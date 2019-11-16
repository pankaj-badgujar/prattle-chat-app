package com.neu.prattle.controller;

import com.neu.prattle.exceptions.UserAlreadyPresentException;
import com.neu.prattle.model.Group;
import com.neu.prattle.model.IMember;
import com.neu.prattle.model.IUser;
import com.neu.prattle.model.User;
import com.neu.prattle.model.UserConnector;
import com.neu.prattle.service.MemberService;
import com.neu.prattle.service.MemberServiceImpl;

import java.util.Optional;

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

    return Response.ok().build();
  }

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
    accountService.addGroup(group);
    return Response.ok().build();
  }

  @GET
  @Path("/members")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response findAllMembers(String username){
    accountService.findAllMembers(username);
    return Response.ok().build();
  }


}
