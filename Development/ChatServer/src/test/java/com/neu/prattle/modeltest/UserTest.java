package com.neu.prattle.modeltest;

import com.neu.prattle.dao.GroupDao;
import com.neu.prattle.dao.UserDao;
import com.neu.prattle.exceptions.NoSuchUserPresentException;
import com.neu.prattle.model.Group;
import com.neu.prattle.model.IMember;
import com.neu.prattle.model.User;
import com.neu.prattle.service.MemberServiceImpl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * @author Bhargavi Padhya
 * @version version 1.1 dated 11/15/2019
 */
public class UserTest {
  @Mock
  private UserDao userDao;

  @InjectMocks
  private MemberServiceImpl memberService;

  private final String name = "devansh";

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    when(userDao.createUser(any(User.class))).thenReturn(new User());
  }

  @Test
  public void testAFindAllMembers() {

    User bhargavi = new User("bhargavi");
    User mike = new User("mike");
    User pranay = new User("pranay");

    List<String> users = new ArrayList<>();
    users.add("bhargavi");
    users.add("mike");

    memberService.addUser(bhargavi);
    memberService.addUser(mike);
    memberService.addUser(pranay);
    Group group = new Group("fse", users, new ArrayList<>(), memberService);
    memberService.addGroup(group);

    Set<IMember> allMembers = new HashSet<>();
    allMembers.add(mike);
    allMembers.add(pranay);
    allMembers.add(group);

    assertTrue(memberService.findAllMembers("bhargavi").containsAll(allMembers));
  }

  @Test
  public void testUserCreation() {
    User user = new User(name);
    assertEquals(name, user.getName());
  }

  @Test
  public void testUserNameChange() {
    User user = new User(name);
    assertEquals(name, user.getName());
    String changedName = "Devansh";
    user.setName(changedName);
    assertEquals(changedName, user.getName());
  }

  @Test
  public void testUsersConnection() {
    String harshilName = "harshil";
    User devansh = new User("Devansh2", memberService);
    User harshil = new User(harshilName, memberService);
    memberService.addUser(devansh);
    memberService.addUser(harshil);

    harshil.connectTo(devansh);

    devansh.connectTo(harshil);

    assertEquals(devansh.getName(), harshil.getConnectedMembers().get().getName());
    assertEquals(harshilName, devansh.getConnectedMembers().get().getName());

    String pankajName = "Pankaj108";
    User pankaj = new User(pankajName);
    memberService.addUser(pankaj);

    harshil.connectTo(pankaj);
    assertEquals(pankajName, harshil.getConnectedMembers().get().getName());
  }

  @Test(expected = NoSuchUserPresentException.class)
  public void testInvalidConnectedGroups() {
    User devansh = new User("devansh1");
    memberService.addUser(devansh);
    User harshil1 = new User("harshil1");

    // We are adding an invalid user which is yet not registered which should throw an exception
    devansh.connectTo(harshil1);
  }

  @Test
  public void testEqualUsers() {
    User devansh = new User(name);

    User duplicateDevansh = new User(name);

    assertEquals(devansh, duplicateDevansh);
  }

  @Test
  public void testNotEqualUsers() {
    User devansh = new User(name, memberService);
    int devanshID = devansh.getId();

    User duplicateDevansh = new User(name + "1", memberService);
    int duplicatDevanshID = duplicateDevansh.getId();

    assertEquals(devanshID, duplicatDevanshID);

    Group group = new Group("test", new ArrayList<>(), new ArrayList<>());
    assertNotEquals(devansh, duplicateDevansh);
    assertNotEquals(devansh, group);
  }

  @Test
  public void testUserHashCode() {
    User devansh = new User(name);
    User anotherDevansh = new User(name);

    assertEquals(devansh.hashCode(), anotherDevansh.hashCode());
  }

  @Test
  public void testGetAllMembers() {
    User harshil = new User("harshil");
    Set<String> connectedMembers = new HashSet<>();
    connectedMembers.add("harshil");
    assertEquals(connectedMembers, harshil.getAllConnectedMembers());
  }
}
