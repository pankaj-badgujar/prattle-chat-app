package com.neu.prattle.controller;

import com.google.gson.Gson;

import com.neu.prattle.model.IMember;
import com.neu.prattle.service.MemberService;
import com.neu.prattle.service.MemberServiceImpl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;

@Path("/member1")
public class MemberController1 {

  private MemberService accountService = MemberServiceImpl.getInstance();

  @GET
  @Path("/classmember")
  @Consumes(MediaType.APPLICATION_JSON)
  public String findAllMembers(String username) {
    Set<IMember> memberSet = accountService.findAllMembers(username);
    Gson gson = new Gson();
    return gson.toJson(memberSet);
  }

}
