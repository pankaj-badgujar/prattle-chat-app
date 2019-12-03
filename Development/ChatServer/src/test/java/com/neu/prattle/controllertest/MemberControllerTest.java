package com.neu.prattle.controllertest;

import com.neu.prattle.controller.MemberController;
import com.neu.prattle.exceptions.InvalidAdminException;
import com.neu.prattle.exceptions.NoSuchUserPresentException;
import com.neu.prattle.exceptions.UserAlreadyPresentException;
import com.neu.prattle.model.Group;
import com.neu.prattle.model.GroupData;
import com.neu.prattle.model.User;
import com.neu.prattle.model.UserConnector;
import com.neu.prattle.service.MemberService;
import com.neu.prattle.service.MemberServiceImpl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
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
    Response response = controller.createUserAccount(new User("mock", Mockito.mock(MemberServiceImpl.class)));
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
    Response response = controller.createGroup(new Group("FSE", new ArrayList<>(), new ArrayList<>()
            , Mockito.mock(MemberServiceImpl.class)));
    assertEquals(200, response.getStatus());
    doThrow(UserAlreadyPresentException.class).when(impl).addGroup(any(Group.class));
    response = controller.createGroup(new Group("FSE", new ArrayList<>(), new ArrayList<>()
            , Mockito.mock(MemberServiceImpl.class)));
    assertEquals(409, response.getStatus());
  }

  @Test
  public void testAddGroup() {
    doNothing().when(impl).addGroup(any(Group.class));
    Response response = controller.addGroupMember("devansh",
            new GroupData("devansh", new ArrayList<>()));
    assertEquals(400, response.getStatus());
    when(impl.findMemberByName(any(String.class))).thenReturn(Optional.of(Mockito.mock(Group.class)));
    response = controller.addGroupMember("devansh",
            new GroupData("devansh", new ArrayList<>()));
    assertEquals(200, response.getStatus());
    Group mockedGroup = Mockito.mock(Group.class);
    doThrow(InvalidAdminException.class).when(mockedGroup).addUser(anyString(), anyString());
    List<String> mockedList = new ArrayList<>();
    mockedList.add("devansh");
    when(impl.findMemberByName(any(String.class))).thenReturn(Optional.of(mockedGroup));
    response = controller.addGroupMember("devansh",
            new GroupData("devansh", mockedList));
    assertEquals(401, response.getStatus());
    doThrow(NoSuchUserPresentException.class).when(mockedGroup).addUser(anyString(), anyString());
    when(impl.findMemberByName(any(String.class))).thenReturn(Optional.of(mockedGroup));
    response = controller.addGroupMember("devansh",
            new GroupData("devansh", mockedList));
    assertEquals(400, response.getStatus());
  }

  @Test
  public void testMakeAdminGroup() {
    doNothing().when(impl).addGroup(any(Group.class));
    Response response = controller.addGroupAdmin("devansh",
            "devansh", new ArrayList<>());
    assertEquals(400, response.getStatus());
    when(impl.findMemberByName(any(String.class))).thenReturn(Optional.of(Mockito.mock(Group.class)));
    response = controller.addGroupAdmin("devansh",
            "devansh", new ArrayList<>());
    assertEquals(200, response.getStatus());
    Group mockedGroup = Mockito.mock(Group.class);
    doThrow(InvalidAdminException.class).when(mockedGroup).makeAdmin(anyString(), anyString());
    List<String> mockedList = new ArrayList<>();
    mockedList.add("devansh");
    when(impl.findMemberByName(any(String.class))).thenReturn(Optional.of(mockedGroup));
    response = controller.addGroupAdmin("devansh",
            "devansh", mockedList);
    assertEquals(401, response.getStatus());
    doThrow(NoSuchUserPresentException.class).when(mockedGroup).makeAdmin(anyString(), anyString());
    when(impl.findMemberByName(any(String.class))).thenReturn(Optional.of(mockedGroup));
    response = controller.addGroupAdmin("devansh",
            "devansh", mockedList);
    assertEquals(400, response.getStatus());
  }

  @Test
  public void testValidateUserAccountWhenUserAbsent() {
    Map<String, String> map = new HashMap<>();
    map.put("username", "harshil");
    map.put("password", "test");
    when(impl.findMemberByName(anyString())).thenReturn(Optional.empty());
    Response res = controller.validateUserAccount(map);
    assertEquals(401, res.getStatus());
  }

  @Test
  public void testValidateUserAccountWhenUserPresent() {

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

  @Test
  public void testDeleteGroup() {
    when(impl.deleteGroup(anyString(),anyString())).thenReturn(true);
    Response res = controller.deleteGroup("devansh","FSE");
    assertEquals(200, res.getStatus());
    when(impl.deleteGroup(anyString(),anyString())).thenReturn(false);
    res = controller.deleteGroup("devansh","FSE");
    assertEquals(401, res.getStatus());
  }

  @Test
  public void testRemoveGroup() {
    doNothing().when(impl).addGroup(any(Group.class));
    Response response = controller.removeAdminGroup("devansh",
            "devansh", new ArrayList<>());
    assertEquals(400, response.getStatus());
    when(impl.findMemberByName(any(String.class))).thenReturn(Optional.of(Mockito.mock(Group.class)));
    response = controller.removeAdminGroup("devansh",
            "devansh", new ArrayList<>());
    assertEquals(200, response.getStatus());
    Group mockedGroup = Mockito.mock(Group.class);
    doThrow(InvalidAdminException.class).when(mockedGroup).removeAdmin(anyString(), anyString());
    List<String> mockedList = new ArrayList<>();
    mockedList.add("devansh");
    when(impl.findMemberByName(any(String.class))).thenReturn(Optional.of(mockedGroup));
    response = controller.removeAdminGroup("devansh",
            "devansh", mockedList);
    assertEquals(401, response.getStatus());
    doThrow(NoSuchUserPresentException.class).when(mockedGroup).removeAdmin(anyString(), anyString());
    when(impl.findMemberByName(any(String.class))).thenReturn(Optional.of(mockedGroup));
    response = controller.removeAdminGroup("devansh",
            "devansh", mockedList);
    assertEquals(400, response.getStatus());
  }
}