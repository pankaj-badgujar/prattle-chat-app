package com.neu.prattle.controller;

import com.google.gson.Gson;

import com.neu.prattle.exceptions.UserAlreadyPresentException;
import com.neu.prattle.model.Group;
import com.neu.prattle.model.IMember;
import com.neu.prattle.model.IMemberDTO;
import com.neu.prattle.model.IUser;
import com.neu.prattle.model.User;
import com.neu.prattle.model.UserConnector;
import com.neu.prattle.service.MemberService;
import com.neu.prattle.service.MemberServiceImpl;

import org.json.JSONObject;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
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

  private MemberService accountService = MemberServiceImpl.getInstance();

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
   * Get all the members for the username passed as parameter to this endpoint.
   *
   * @param username -> Username of the User for which we need to fetch all the members of the
   *                 group
   * @return -> Json object with all the IMember in th group.
   */
  @GET
  @Path("members")
  @Consumes(MediaType.APPLICATION_JSON)
  public String findAllMembers(String username) {
    Gson gson = new Gson();
    JSONObject jsonObject = new JSONObject(username);
    Set<IMember> memberSet = accountService.findAllMembers((String) jsonObject.get("username"));
    Set<IMemberDTO> dto = memberSet.stream().map(IMember::getDTO).collect(Collectors.toSet());
    return gson.toJson(dto);
  }
}
