package com.neu.prattle.modeltest;

import com.neu.prattle.exceptions.NoSuchUserPresentException;
import com.neu.prattle.model.Group;
import com.neu.prattle.model.User;
import com.neu.prattle.service.UserService;
import com.neu.prattle.service.UserServiceImpl;

import org.junit.After;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
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
    UserService userService = UserServiceImpl.getInstance();
    String harshilName = "harshil";
    User devansh = new User("Devansh");
    User harshil = new User(harshilName);
    userService.addUser(devansh);
    userService.addUser(harshil);

    harshil.connectTo(devansh.getName());

    devansh.connectTo(harshilName);

    assertEquals(devansh.getName(), harshil.getConnectedUsers());
    assertEquals(harshilName, devansh.getConnectedUsers());

    String pankajName = "Pankaj";
    User pankaj = new User(pankajName);
    userService.addUser(pankaj);


    harshil.connectTo(pankajName);
    assertEquals(pankajName, harshil.getConnectedUsers());
  }

  @Test(expected = NoSuchUserPresentException.class)
  public void testInvalidConnectedGroups() {
    UserService userService = UserServiceImpl.getInstance();
    User devansh = new User(name);
    userService.addUser(devansh);

    // We are adding an invalid user which is yet not registered which should throw an exception
    devansh.connectTo("harshil1");
  }

  @Test
  public void testEqualUsers() {
    User devansh = new User(name);

    User duplicateDevansh = new User(name);

    assertTrue(devansh.equals(duplicateDevansh));
  }

  @Test
  public void testNotEqualUsers() {
    User devansh = new User(name);

    User duplicateDevansh = new User(name + "1");

    Group group = new Group("test",new ArrayList<>(),new ArrayList<>());
    assertFalse(devansh.equals(duplicateDevansh));
    assertFalse(devansh.equals(group));
  }


}