package com.neu.prattle;

import com.neu.prattle.exceptions.NoSuchUserPresentException;
import com.neu.prattle.model.User;
import com.neu.prattle.service.UserService;
import com.neu.prattle.service.UserServiceImpl;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

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

}