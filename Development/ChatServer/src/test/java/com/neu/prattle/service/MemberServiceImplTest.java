package com.neu.prattle.service;

import com.neu.prattle.dao.GroupDao;
import com.neu.prattle.dao.SqlGroupDao;
import com.neu.prattle.dao.SqlUserDao;
import com.neu.prattle.dao.UserDao;
import com.neu.prattle.exceptions.UserAlreadyPresentException;
import com.neu.prattle.model.Group;
import com.neu.prattle.model.IMember;
import com.neu.prattle.model.User;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

public class MemberServiceImplTest {

  private UserDao userDao;
  private GroupDao groupDao;
  private MemberService memberService;

  @Before
  public void setUp() {
    userDao = Mockito.mock(SqlUserDao.class);
    groupDao = Mockito.mock(SqlGroupDao.class);
    memberService = new MemberServiceImpl(userDao, groupDao);
    when(userDao.createUser(any(User.class))).thenReturn(Mockito.mock(User.class));
    when(groupDao.createGroup(any(Group.class))).thenReturn(Mockito.mock(Group.class));
  }

  @Test
  public void testGroupService() {
    Group groupName = Mockito.mock(Group.class);
    memberService.addGroup(groupName);
    try {
      memberService.addGroup(groupName);
    } catch (UserAlreadyPresentException e) {
      assertEquals("Group already present with name: null", e.getMessage());
    }
  }

  @Test
  public void testUserService() {
    User groupName = Mockito.mock(User.class);
    memberService.addUser(groupName);
    try {
      memberService.addUser(groupName);
    } catch (UserAlreadyPresentException e) {
      assertEquals("User already present with name: null", e.getMessage());
    }
  }

  @Test
  public void testGetGroup() {
    when(userDao.getUser(anyString())).thenReturn(Optional.empty());
    when(groupDao.findGroup(anyString())).thenReturn(Optional.empty());
    Optional<IMember> member = memberService.findMemberByName("devansh");
    assertFalse(member.isPresent());
  }

  @Test
  public void testGetUser() {
    when(userDao.getUser(anyString())).thenReturn(Optional.of(Mockito.mock(User.class)));
    Optional<IMember> member = memberService.findMemberByName("devansh");
    assertTrue(member.isPresent());
  }

  @Test
  public void testFindAllMember() {
    when(userDao.getUser(anyString())).thenReturn(Optional.of(Mockito.mock(User.class)));
    Set<IMember> member = memberService.findAllMembers("devansh");
    assertTrue(member.isEmpty());
  }

  @Test
  public void testDeleteGroup() {
    User mockUser = Mockito.mock(User.class);
    mockUser.setName("mockUser");
    Group mockGroup = Mockito.mock(Group.class);
    when(groupDao.findGroup(anyString())).thenReturn(Optional.of(mockGroup));
    when(groupDao.removeGroup(anyString())).thenReturn(true);
    memberService.addUser(mockUser);
    List<String> mockList = new ArrayList<>();
    mockList.add("devansh");
    when(mockGroup.getAdmins()).thenReturn(mockList);
    boolean member = memberService.deleteGroup("FSE", "devansh");
    assertTrue(member);
  }

  @Test
  public void testDeleteInvalidGroup() {
    User mockUser = Mockito.mock(User.class);
    mockUser.setName("mockUser");
    Group mockGroup = Mockito.mock(Group.class);
    when(groupDao.findGroup(anyString())).thenReturn(Optional.of(mockGroup));
    when(groupDao.removeGroup(anyString())).thenReturn(true);
    memberService.addUser(mockUser);
    List<String> mockList = new ArrayList<>();
    when(mockGroup.getAdmins()).thenReturn(mockList);
    boolean member = memberService.deleteGroup("FSE", "devansh");
    assertFalse(member);
  }

  @Test
  public void testDeleteGroupInvalid() {
    User mockUser = Mockito.mock(User.class);
    mockUser.setName("mockUser");
    when(groupDao.findGroup(anyString())).thenReturn(Optional.empty());
    boolean member = memberService.deleteGroup("FSE", "devansh");
    assertFalse(member);
  }

}