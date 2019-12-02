package com.neu.prattle.controller;

import com.google.gson.Gson;

import com.neu.prattle.exceptions.InvalidAdminException;
import com.neu.prattle.exceptions.NoSuchUserPresentException;
import com.neu.prattle.exceptions.UserAlreadyPresentException;
import com.neu.prattle.model.Group;
import com.neu.prattle.model.GroupData;
import com.neu.prattle.model.IGroup;
import com.neu.prattle.model.IMember;
import com.neu.prattle.model.IMemberDTO;
import com.neu.prattle.model.IUser;
import com.neu.prattle.model.User;
import com.neu.prattle.model.UserConnector;
import com.neu.prattle.service.MemberService;
import com.neu.prattle.service.MemberServiceImpl;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * This a controller class for the members creation and connection.
 *
 * @author Devansh Gandhi
 * @version 2.0 dated 11/30/2019
 */
@Path("/")
public class MemberController {

  private MemberService accountService;

  public MemberController() {
    accountService = MemberServiceImpl.getInstance();
  }

  public MemberController(MemberService accountService) {
    this.accountService = accountService;
  }

  /**
   * Handles a HTTP POST request for user creation
   *
   * @param user -> The User object decoded from the payload of POST request.
   * @return -> A Response indicating the outcome of the requested operation.
   */
  @POST
  @Path("user")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response createUserAccount(User user) {
    try {
      accountService.addUser(user);
    } catch (UserAlreadyPresentException e) {
      return Response.status(409).build();
    }

    return Response.ok().entity((new Gson()).toJson(user.getDTO())).build();
  }

  /**
   * Handles a HTTP POST request for user creation
   *
   * @param details -> The User object decoded from the payload of POST request.
   * @return -> A Response indicating the outcome of the requested operation.
   */
  @POST
  @Path("auth")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response validateUserAccount(Map<String, String> details) {

    Optional<IMember> user = accountService.findMemberByName(details.get("username"));
    IMember member = user.isPresent() && ((IUser) user.get()).isCorrectPassword(details.get("password")) ?
            user.get() : null;
    return member != null ? Response.ok().entity((new Gson()).toJson(member.getDTO())).build() :
            Response.status(401).build();
  }

  /**
   * Connect two users passed in as parameters.
   *
   * @param users -> Wrapper class for two users that needs to be connected. The first parameter is
   *              the user from and the second parameter is the userTo
   * @return -> A Response indicating the outcome of the requested operation.Response
   */
  @POST
  @Path("connect")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response connectToUserAccounts(UserConnector users) {
    Optional<IMember> fromMember = accountService.findMemberByName(users.getUserFrom());
    Optional<IMember> toMember = accountService.findMemberByName(users.getUserTo());
    if (fromMember.isPresent() && toMember.isPresent()) {
      ((IUser) fromMember.get()).connectTo(toMember.get());
    } else {
      return Response.status(409).entity("false").build();
    }
    return Response.ok().entity("true").build();
  }

  /**
   * Handles a HTTP POST request for group creation
   *
   * @param group -> The Group object decoded from the payload of POST request.
   * @return -> A Response indicating the outcome of the requested operation.
   */
  @POST
  @Path("group")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response createGroup(Group group) {
    try {
      accountService.addGroup(group);
    } catch (UserAlreadyPresentException e) {
      return Response.status(409).build();
    }
    return Response.ok().entity(new Gson().toJson(group.getDTO())).build();
  }

  /**
   * This method can be used to add a member to the group.
   *
   * @param data -> Key value pairs for group name and member name.
   * @return -> Response with a status 200 if the group was deleted successfully.
   */
  @POST
  @Path("members/group/{adminName}")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response addGroupMember(@PathParam("adminName") String adminName, GroupData data) {
    String groupName = data.getGroupName();
    List<String> memberName = data.getMembers();
    Optional<IMember> groupEntity = accountService.findMemberByName(groupName);
    if (groupEntity.isPresent()) {
      IGroup group = (IGroup) groupEntity.get();
      try {
        memberName.forEach(member -> group.addUser(adminName, member));
      } catch (InvalidAdminException e) {
        return Response.status(401).build();
      } catch (NoSuchUserPresentException e) {
        return Response.status(400).build();
      }
    }
    return Response.status(400).build();
  }

  /**
   * This method can be used to make a member admin to the group.
   *
   * @param groupName  -> The name of the group from which the admin needs to be removed.
   * @param memberName -> All the admins that need to be removed.
   * @param adminName  -> Name of the user making the dlete request.
   * @return -> Response with a status 200 if the group was deleted successfully.
   */
  @POST
  @Path("members/{groupName}/{adminName}")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response addGroupAdmin(@PathParam("adminName") String adminName, @PathParam("groupName")
          String groupName, List<String> memberName) {
    Optional<IMember> groupEntity = accountService.findMemberByName(groupName);
    if (groupEntity.isPresent()) {
      IGroup group = (IGroup) groupEntity.get();
      try {
        memberName.forEach(member -> group.makeAdmin(adminName, member));
        return Response.status(200).build();
      } catch (InvalidAdminException e) {
        return Response.status(401).build();
      } catch (NoSuchUserPresentException e) {
        return Response.status(400).build();
      }
    }
    return Response.status(400).build();
  }

  /**
   * Get all the members for the username passed as parameter to this endpoint.
   *
   * @param username -> Username of the User for which we need to fetch all the members of the
   *                 group
   * @return -> Respose with body as all the IMember in th group.
   */
  @GET
  @Path("members/{name}")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response findAllMembers(@PathParam("name") String username) {
    if (!accountService.findMemberByName(username).isPresent()) {
      return Response.status(404).entity("User with name " + username + " does not exist").build();
    }
    Gson gson = new Gson();
    Set<IMember> memberSet = accountService.findAllMembers(username);
    Set<IMemberDTO> dto = memberSet.stream().map(IMember::getDTO).collect(Collectors.toSet());
    return Response.ok().entity(gson.toJson(dto)).build();
  }


  /**
   * This method can be used to delete a member from the group.
   *
   * @param groupName -> The name of the group that needs to be deleted.
   * @param adminName -> Name of the user making the dlete request.
   * @return -> Response with a status 200 if the group was deleted successfully.
   */
  @DELETE
  @Path("members/group/{adminName}")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response deleteGroup(@PathParam("adminName") String adminName, String groupName) {
    return accountService.deleteGroup(groupName, adminName) ? Response.ok().build() :
            Response.status(401).build();
  }

  /**
   * This method can be used to a member from the group.
   *
   * @param groupName  -> The name of the group from which the admin needs to be removed.
   * @param memberName -> All the admins that need to be removed.
   * @param adminName  -> Name of the user making the dlete request.
   * @return -> Response with a status 200 if the group was deleted successfully.
   */
  @DELETE
  @Path("members/{groupName}/{adminName}")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response removeAdminGroup(@PathParam("adminName") String adminName, @PathParam("groupName")
          String groupName, List<String> memberName) {
    Optional<IMember> groupEntity = accountService.findMemberByName(groupName);
    if (groupEntity.isPresent()) {
      IGroup group = (IGroup) groupEntity.get();
      try {
        memberName.forEach(member -> group.removeAdmin(adminName, member));
      } catch (InvalidAdminException e) {
        return Response.status(401).build();
      } catch (NoSuchUserPresentException e) {
        return Response.status(400).build();
      }
    }
    return Response.status(400).build();
  }
}
