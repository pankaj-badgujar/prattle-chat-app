package com.neu.prattle.controllertest;
import com.neu.prattle.controller.MemberController;

import com.neu.prattle.exceptions.UserAlreadyPresentException;
import com.neu.prattle.model.Group;
import com.neu.prattle.model.User;
import com.neu.prattle.model.UserConnector;
import com.neu.prattle.service.MemberService;
import com.neu.prattle.service.MemberServiceImpl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;


public class MemberControllerTest {

  private MemberController controller;
  private MemberService impl;

  @Before
  public void setup() {
    impl = Mockito.mock(MemberServiceImpl.class);
    when(impl.findMemberByName(any(String.class))).thenReturn(Optional.empty());
    controller = new MemberController(impl);
  }

  @Test
  public void testCreateNewUser() {
    Response response = controller.createUserAccount(new User("mock"));
    assertEquals(200, response.getStatus());
  }

  @Test
  public void testCreateDuplicateUser() {
    when(impl.addUser(any(User.class))).thenThrow(new UserAlreadyPresentException("mock"));
    Response response = controller.createUserAccount(new User("", impl));
    assertEquals(409, response.getStatus());
  }

  @Test
  public void testConnectTwoUsers() {
    when(impl.findMemberByName(anyString())).thenReturn(Optional.of(new User("harshil", impl)));
    Response res = controller.connectToUserAccounts(new UserConnector("harshil", "devamsh"));
    assertEquals(200, res.getStatus());
  }

  @Test
  public void testConnectUserWithInvalidUser() {
    when(impl.findMemberByName(anyString())).thenReturn(Optional.empty());
    Response res = controller.connectToUserAccounts(new UserConnector("harshil", "devamsh"));
    assertEquals(409, res.getStatus());
  }

  @Test
  public void testCreateNewGroup() {
    doNothing().when(impl).addGroup(any(Group.class));
    Response response = controller.createGroup(new Group());
    assertEquals(200, response.getStatus());
  }

  @Test
  public void testValidateUserAccountWhenUserAbsent(){
    Map<String, String> map = new HashMap<>();
    map.put("username", "harshil");
    map.put("password", "test");

    when(impl.findMemberByName(anyString())).thenReturn(Optional.empty());

    Response res = controller.validateUserAccount(map);
    assertEquals(401, res.getStatus());
  }

  @Test
  public void testValidateUserAccountWhenUserPresent(){

    when(impl.findMemberByName(anyString())).thenReturn(Optional.of(new User("harshil", "password", impl)));

    Map<String, String> map = new HashMap<>();
    map.put("username", "harshil");
    map.put("password", "password");
    Response res = controller.validateUserAccount(map);
    assertEquals(200, res.getStatus());
  }

  @Test
  public void testFindAllMembers() {

    when(impl.findMemberByName(any(String.class))).thenReturn(Optional.of(new User("harshil", impl)));
    Response res = controller.findAllMembers("harshil");
    assertEquals(200, res.getStatus());
  }

  @Test
  public void testFindAllMembersUserNotFound() {

    Response res = controller.findAllMembers("devansh");
    assertEquals(404, res.getStatus());
  }
}