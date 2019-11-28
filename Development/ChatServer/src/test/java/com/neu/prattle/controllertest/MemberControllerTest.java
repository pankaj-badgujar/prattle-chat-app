package com.neu.prattle.controllertest;

import com.neu.prattle.controller.MemberController;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

public class MemberControllerTest extends JerseyTest {

  @Test
  public void testCreateNewUser() {
    Entity<String> entity = Entity.entity("{\"name\": \"Pankaj20\"}", MediaType.APPLICATION_JSON);
    Response response = target("/member/create/user").request().post(entity);
    assertEquals(200, response.getStatus());
  }

  @Test
  public void testCreateDuplicateUser() {
    Entity<String> entity = Entity.entity("{\"name\": \"Harshil\"}",
            MediaType.APPLICATION_JSON);
    target("/member/create/user").request().post(entity);
    entity = Entity.entity("{\"name\": \"Harshil\"}", MediaType.APPLICATION_JSON);
    Response response = target("/member/create/user").request().post(entity);
    assertEquals(409, response.getStatus());
  }

  @Test
  public void testConnectTwoUsers() throws JSONException {

    Entity<String> entityDevansh = Entity.entity("{\"name\": \"devansh\"}",
            MediaType.APPLICATION_JSON);
    target("/member/create/user").request().post(entityDevansh);
    Entity<String> entityPankaj = Entity.entity("{\"name\": \"pankaj\"}",
            MediaType.APPLICATION_JSON);
    target("/member/create/user").request().post(entityPankaj);
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("userFrom", "devansh");
    jsonObject.put("userTo", "pankaj");
    Entity<String> entity = Entity.entity(jsonObject.toString(),
            MediaType.APPLICATION_JSON);
    Response response = target("/member/connect/member").request().post(entity);
    assertEquals(200, response.getStatus());
  }

  @Test
  public void testConnectUserWithInvalidUser() throws JSONException {
    Entity<String> entityDevansh = Entity.entity("{\"name\": \"devansh\"}",
            MediaType.APPLICATION_JSON);
    target("/member/create/user").request().post(entityDevansh);
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("userFrom", "devansh");
    jsonObject.put("userTo", "pankaj");
    Entity<String> entity = Entity.entity(jsonObject.toString(),
            MediaType.APPLICATION_JSON);
    Response response = target("/member/connect/member").request().post(entity);
    assertEquals(409, response.getStatus());
  }

  @Test
  public void testCreateNewGroup() {
    Entity<String> entity = Entity.entity("{\"name\": \"FSE\"," + "\n \"users\": [],\n \"admins\": []\n }",
            MediaType.APPLICATION_JSON);
    Response response = target("/member/create/group").request().post(entity);
    assertEquals(500, response.getStatus());
  }

  @Override
  protected Application configure() {
    return new ResourceConfig(MemberController.class);
  }

  @After
  public void cleanUp(){

  }

}