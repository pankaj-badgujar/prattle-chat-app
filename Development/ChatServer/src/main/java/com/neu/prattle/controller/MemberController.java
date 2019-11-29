package com.neu.prattle.controller;

import com.google.gson.Gson;

import com.neu.prattle.exceptions.UserAlreadyPresentException;
import com.neu.prattle.model.Group;
import com.neu.prattle.model.IMember;
import com.neu.prattle.model.IUser;
import com.neu.prattle.model.User;
import com.neu.prattle.model.UserConnector;
import com.neu.prattle.service.MemberService;
import com.neu.prattle.service.MemberServiceImpl;

import org.json.JSONObject;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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
 * @version 1.0 dated 11/1/2019
 */
@Path("/member")
public class MemberController {

  private MemberService accountService = MemberServiceImpl.getInstance();

  /**
   * Handles a HTTP POST request for user creation
   *
   * @param user -> The User object decoded from the payload of POST request.
   * @return -> A Response indicating the outcome of the requested operation.
   */
  @POST
  @Path("/create/user")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response createUserAccount(User user) {
    try {
      accountService.addUser(user);
    } catch (UserAlreadyPresentException e) {
      return Response.status(409).build();
    }

    return Response.ok().entity((new Gson()).toJson(user)).build();
  }

  /**
   * Handles a HTTP POST request for user creation
   *
   * @param details -> The User object decoded from the payload of POST request.
   * @return -> A Response indicating the outcome of the requested operation.
   */
  @POST
  @Path("/validate/user")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response validateUserAccount(List<String> details) {

    Optional<IMember> user = accountService.findMemberByName(details.get(0));
    IMember member = user.isPresent() && ((IUser) user.get()).isCorrectPassword(details.get(1)) ?
            user.get() : null;
    return Response.ok().entity((new Gson()).toJson(member)).build();
  }

  /**
   * Connect two users passed in as parameters.
   *
   * @param users -> Wrapper class for two users that needs to be connected. The first parameter is
   *              the user from and the second parameter is the userTo
   * @return -> A Response indicating the outcome of the requested operation.Response
   */
  @POST
  @Path("/connect/member")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response connectToUserAccounts(UserConnector users) {
    Optional<IMember> fromMember = accountService.findMemberByName(users.getUserFrom());
    Optional<IMember> toMember = accountService.findMemberByName(users.getUserTo());
    if (fromMember.isPresent() && toMember.isPresent()) {
      ((IUser) fromMember.get()).connectTo(toMember.get());
    } else {
      return Response.status(409).build();
    }
    return Response.ok().build();
  }

  /**
   * Handles a HTTP POST request for group creation
   *
   * @param group -> The Group object decoded from the payload of POST request.
   * @return -> A Response indicating the outcome of the requested operation.
   */
  @POST
  @Path("/create/group")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response createGroup(Group group) {
    try {
      accountService.addGroup(group);
    } catch (UserAlreadyPresentException e) {
      return Response.status(409).build();
    }
    return Response.ok().build();
  }

  /**
   * Get all the members for the username passed as parameter to this endpoint.
   *
   * @param username -> Username of the User for which we need to fetch all the members of the
   *                 group
   * @return -> Json object with all the IMember in th group.
   */
  @GET
  @Path("/classmember")
  @Consumes(MediaType.APPLICATION_JSON)
  public String findAllMembers(String username) {
    Gson gson = new Gson();
    JSONObject jsonObject = new JSONObject(username);
    Set<IMember> memberSet = accountService.findAllMembers((String) jsonObject.get("username"));
    return gson.toJson(memberSet);
  }
}
