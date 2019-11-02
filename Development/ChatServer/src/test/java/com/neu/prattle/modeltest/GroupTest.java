package com.neu.prattle.modeltest;

import com.neu.prattle.exceptions.InvalidAdminException;
import com.neu.prattle.model.Group;
import com.neu.prattle.model.IGroup;
import com.neu.prattle.model.IMember;
import com.neu.prattle.model.IUser;
import com.neu.prattle.model.User;
import com.neu.prattle.service.MemberService;
import com.neu.prattle.service.MemberServiceImpl;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Test suite for the creation of group instances. A group is a collection of users in the system
 *
 * @author Harshil Mavani
 * @version 1.0 dated 2019-10-16
 */
public class GroupTest {
  private IMember group;
  private List<String> users;
  private List<String> admins;

  @Before
  public void setup() {
    users = new ArrayList<>();
    users.add("Harshil");
    users.add("Devansh");
    users.add("Pankaj");
    users.add("Mike");

    admins = new ArrayList<>();
    admins.add("Mike");
    group = new Group("FSE", users, admins);
  }

  @Test
  public void testGroupInstantiation() {
    assertEquals("FSE", group.getName());

    group.setName("MSD");
    assertEquals("MSD", group.getName());
    assertEquals(((Group) (group)).getUsers().toString(), users.toString());
    assertEquals(((Group) (group)).getAdmins().toString(), admins.toString());
  }

  @Test
  public void testValidAddAdminRequest() {
    ((Group) (group)).makeAdmin("Mike", "Vaibhav");
    admins.add("Vaibhav");
    assertEquals(((Group) (group)).getAdmins().toString(), admins.toString());
  }

  @Test
  public void testValidAddUserRequest() {
    ((Group) (group)).addUser("Mike", "Bhargavi");
    users.add("Bhargavi");
    assertEquals(((Group) (group)).getUsers().toString(), users.toString());
  }

  @Test
  public void testInvalidAddAdminRequest() {
    try {
      ((Group) (group)).makeAdmin("Bhargavi", "Vaibhav");
    } catch (InvalidAdminException iae) {
      assertEquals("Bhargavi is not an admin of FSE group", iae.getMessage());
    }
  }

  @Test
  public void testInvalidAddUserRequest() {
    try {
      ((Group) (group)).addUser("Bhargavi", "Vaibhav");
    } catch (InvalidAdminException iae) {
      assertEquals("Bhargavi is not an admin of FSE group", iae.getMessage());
    }
  }

  @Test
  public void testValidRemoveUserRequest() {
    ((Group) (group)).removeUser("Mike", "Bhargavi");
    users.remove("Bhargavi");
    assertEquals(((Group) (group)).getUsers().toString(), users.toString());
  }

  @Test
  public void testInvalidRemoveUserRequest() {
    try {
      ((Group) (group)).removeUser("Harshil", "Bhargavi");
    } catch (InvalidAdminException iae) {
      assertEquals("Harshil is not an admin of FSE group", iae.getMessage());
    }
  }

  @Test
  public void testValidRemoveAdminRequest() {
    ((Group) (group)).makeAdmin("Mike", "Vaibhav");
    admins.add("Vaibhav");
    assertEquals(((Group) (group)).getAdmins().toString(), admins.toString());

    ((Group) (group)).removeAdmin("Mike", "Vaibhav");
    admins.remove("Vaibhav");
    assertEquals(((Group) (group)).getAdmins().toString(), admins.toString());
  }

  @Test
  public void testInvalidRemoveAdminRequest() {
    try {
      ((Group) (group)).removeUser("Bhargavi", "Mike");
    } catch (InvalidAdminException iae) {
      assertEquals("Bhargavi is not an admin of FSE group", iae.getMessage());
    }
  }

  @Test
  public void testInvalidAdminBeingRemovedRequest() {
    try {
      ((Group) (group)).removeUser("Mike", "Bhargavi");
    } catch (InvalidAdminException iae) {
      assertEquals("Bhargavi is not an admin of FSE group", iae.getMessage());
    }
  }

  @Test
  public void testGetAllMembers() {
    User harshil = new User("Harshil5");
    User devansh = new User("Pankaj5");
    User pankaj = new User("Devansh5");
    User mike = new User("Mike5");

    users = new ArrayList<>();
    users.add("Harshil5");
    users.add("Devansh5");
    users.add("Pankaj5");
    users.add("Mike5");

    admins = new ArrayList<>();
    admins.add("Mike5");
    group = new Group("FSE1", users, admins);

    MemberService accountService = MemberServiceImpl.getInstance();
    accountService.addUser(harshil);
    accountService.addUser(devansh);
    accountService.addUser(pankaj);
    accountService.addUser(mike);

    assertEquals(users, group.getAllConnectedMembers());
  }
}