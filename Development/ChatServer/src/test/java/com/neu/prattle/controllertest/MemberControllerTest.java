package com.neu.prattle.controllertest;
import com.neu.prattle.controller.MemberController;
import com.neu.prattle.dao.GroupDao;
import com.neu.prattle.dao.UserDao;
import com.neu.prattle.exceptions.UserAlreadyPresentException;
import com.neu.prattle.model.Group;
import com.neu.prattle.model.User;
import com.neu.prattle.service.MemberServiceImpl;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.LinkedList;
import java.util.Optional;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;


public class MemberControllerTest {

  private MemberController controller;

  @Mock
  UserDao userDao;

  @Mock
  GroupDao groupDao;

  @InjectMocks
  MemberServiceImpl memberService;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    controller = new MemberController(memberService);
    when(userDao.createUser(any(User.class))).thenReturn(new User());
    when(groupDao.createGroup(any(Group.class))).thenReturn(new Group());
  }

  @Test
  public void testCreateNewUser() {
    Response response = controller.createUserAccount(new User("mock"));
    assertEquals(200, response.getStatus());
  }

  @Test
  public void testCreateDuplicateUser() {
    when(memberService.addUser(any(User.class))).thenThrow(new UserAlreadyPresentException("mock"));
    Response response = controller.createUserAccount(new User("", memberService));
    assertEquals(409, response.getStatus());
  }

  @Test
  public void testConnectTwoUsers() {
  }

  @Test
  public void testConnectUserWithInvalidUser() throws JSONException {

  }

  @Test
  public void testCreateNewGroup() {
    when(memberService.addGroup(any(Group.class))).thenReturn(new Group("", new LinkedList<>(), new LinkedList<>(), memberService));
    Response response = controller.createGroup(new Group());
    assertEquals(200, response.getStatus());
  }
}