package com.neu.prattle.controller;

import com.neu.prattle.exceptions.UserAlreadyPresentException;
import com.neu.prattle.model.Group;

import com.neu.prattle.service.GroupService;
import com.neu.prattle.service.GroupServiceImpl;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/***
 * A Resource class responsible for handling CRUD operations
 * on Group objects.
 *
 * @author Harshil Mavani
 * @version 1.0 18-10-2019
 */
@Path(value = "/group")
public class GroupController {

  // Usually Dependency injection will be used to inject the service at run-time
  private GroupService groupService = GroupServiceImpl.getInstance();

  /***
   * Handles a HTTP POST request for group creation
   *
   * @param group -> The Group object decoded from the payload of POST request.
   * @return -> A Response indicating the outcome of the requested operation.
   */
  @POST
  @Path("/create")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response createGroup(Group group) {
    try {
      groupService.createGroup(group);
    } catch (UserAlreadyPresentException e) {
      return Response.status(409).build();
    }
    return Response.ok().build();
  }
}