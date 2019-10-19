
package com.neu.prattle.controller;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

public class GroupControllerTest extends JerseyTest {

  @Test
  public void testCreateNewGroup() {
    Entity<String> entity = Entity.entity("{\"users\": \"[]\", " +
            "\"admins\": \"[]\", \"groupId\": \"1234\", \"groupName\": \"FSE\"}",
            MediaType.APPLICATION_JSON);
    Response response = target("/group/create").request().post(entity);
    assertEquals(200, response.getStatus());
  }

  @Override
  protected Application configure() {
    return new ResourceConfig(GroupController.class);
  }
}