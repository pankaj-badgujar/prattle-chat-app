package com.neu.prattle.controllertest;
import com.neu.prattle.controller.MemberController;
import com.neu.prattle.dao.GroupDao;
import com.neu.prattle.dao.UserDao;
import com.neu.prattle.exceptions.UserAlreadyPresentException;
import com.neu.prattle.model.Group;
import com.neu.prattle.model.User;
import com.neu.prattle.model.UserConnector;
import com.neu.prattle.service.MemberServiceImpl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
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
    MemberServiceImpl impl = Mockito.mock(MemberServiceImpl.class);
    when(impl.findMemberByName(anyString())).thenReturn(Optional.of(new User("harshil", impl)));
    controller = new MemberController(impl);
    Response res = controller.connectToUserAccounts(new UserConnector("harshil", "devamsh"));
    assertEquals(200, res.getStatus());
  }

  @Test
  public void testConnectUserWithInvalidUser() {
    MemberServiceImpl impl = Mockito.mock(MemberServiceImpl.class);
    when(impl.findMemberByName(anyString())).thenReturn(Optional.empty());
    controller = new MemberController(impl);
    Response res = controller.connectToUserAccounts(new UserConnector("harshil", "devamsh"));
    assertEquals(409, res.getStatus());
  }

  @Test
  public void testCreateNewGroup() {
    MemberServiceImpl impl = Mockito.mock(MemberServiceImpl.class);
    doNothing().when(impl).addGroup(any(Group.class));
    controller = new MemberController(impl);
    Response response = controller.createGroup(new Group());
    assertEquals(200, response.getStatus());
  }

  @Test
  public void testValidateUserAccountWhenUserAbsent(){
    Map<String, String> map = new HashMap<>();
    map.put("username", "harshil");
    map.put("password", "test");
    Response res = controller.validateUserAccount(map);
    assertEquals(401, res.getStatus());
  }

  @Test
  public void testValidateUserAccountWhenUserPresent(){

    User user = new User("harshil", "password", memberService);
    memberService.addUser(user);

    Map<String, String> map = new HashMap<>();
    map.put("username", "harshil");
    map.put("password", "password");
    Response res = controller.validateUserAccount(map);
    assertEquals(200, res.getStatus());
  }

  @Test
  public void testFindAllMembers() {
    MemberServiceImpl impl = Mockito.mock(MemberServiceImpl.class);
    when(impl.findAllMembers(any(String.class))).thenReturn(new HashSet<>());
    controller = new MemberController(impl);
    controller.findAllMembers("test");

  }
}