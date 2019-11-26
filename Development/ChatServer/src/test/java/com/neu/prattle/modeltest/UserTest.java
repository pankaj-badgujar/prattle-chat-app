package com.neu.prattle.modeltest;

import com.neu.prattle.exceptions.NoSuchUserPresentException;
import com.neu.prattle.model.Group;
import com.neu.prattle.model.User;
import com.neu.prattle.service.MemberService;
import com.neu.prattle.service.MemberServiceImpl;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class UserTest {

  private final String name = "devansh";

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
    MemberService userService = MemberServiceImpl.getInstance();
    String harshilName = "harshil";
    User devansh = new User("Devansh10");
    User harshil = new User(harshilName);
    userService.addUser(devansh);
    userService.addUser(harshil);

    harshil.connectTo(devansh);

    devansh.connectTo(harshil);

    assertEquals(devansh.getName(), harshil.getConnectedMembers().get().getName());
    assertEquals(harshilName, devansh.getConnectedMembers().get().getName());

    String pankajName = "Pankaj108";
    User pankaj = new User(pankajName);
    userService.addUser(pankaj);


    harshil.connectTo(pankaj);
    assertEquals(pankajName, harshil.getConnectedMembers().get().getName());
  }

  @Test(expected = NoSuchUserPresentException.class)
  public void testInvalidConnectedGroups() {
    MemberService userService = MemberServiceImpl.getInstance();
    User devansh = new User("devansh1");
    userService.addUser(devansh);
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
    User devansh = new User(name);
    String devanshID = devansh.getId();

    User duplicateDevansh = new User(name + "1");

    String duplicatDevanshID = duplicateDevansh.getId();
    assertNotEquals(devanshID, duplicatDevanshID);

    Group group = new Group("test",new ArrayList<>(),new ArrayList<>());
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