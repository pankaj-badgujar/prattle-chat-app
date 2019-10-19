
package com.neu.prattle.controllertest;
import com.neu.prattle.controller.UserController;
import com.neu.prattle.main.PrattleApplication;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

public class UserControllerTest extends JerseyTest {

  @Test
  public void testCreateNewUser() {
    Entity<String> entity = Entity.entity("{\"name\": \"Pankaj\"}", MediaType.APPLICATION_JSON);
    Response response = target("/user/create").request().post(entity);
    assertEquals(200, response.getStatus());
  }

  @Test
  public void testCreateDuplicateUser() {
    Entity<String> entity = Entity.entity("{\"name\": \"Harshil\"}",
            MediaType.APPLICATION_JSON);
    target("/user/create").request().post(entity);
    entity = Entity.entity("{\"name\": \"Harshil\"}", MediaType.APPLICATION_JSON);
    Response response = target("/user/create").request().post(entity);
    assertEquals(409, response.getStatus());
  }

  @Override
  protected Application configure() {
    return new ResourceConfig(UserController.class);
  }
}