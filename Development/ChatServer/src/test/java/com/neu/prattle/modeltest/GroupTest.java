package com.neu.prattle.modeltest;

import com.neu.prattle.exceptions.InvalidAdminException;
import com.neu.prattle.model.Group;
import com.neu.prattle.model.IGroup;
import com.neu.prattle.model.IMember;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Test suite for the creation of group instances. A group is a collection of users in the system
 *
 * @version 1.0 dated 2019-10-16
 * @author Harshil Mavani
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

    admins = new ArrayList<>();
    admins.add("Mike");
    group = new Group("FSE", users, admins);
  }

  @Test
  public void testGroupInstantiation(){
    assertEquals("FSE", group.getName().get(0));

    group.setName("MSD");
    assertEquals("MSD", group.getName().get(0));
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
    } catch(InvalidAdminException iae) {
      assertEquals("Bhargavi is not an admin of FSE group", iae.getMessage());
    }
  }

  @Test
  public void testInvalidAddUserRequest() {
    try {
      ((Group) (group)).addUser("Bhargavi", "Vaibhav");
    } catch(InvalidAdminException iae) {
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
    try{
      ((Group) (group)).removeUser("Harshil", "Bhargavi");
    } catch(InvalidAdminException iae) {
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
    try{
      ((Group) (group)).removeUser("Bhargavi", "Mike");
    } catch(InvalidAdminException iae) {
      assertEquals("Bhargavi is not an admin of FSE group", iae.getMessage());
    }
  }

  @Test
  public void testInvalidAdminBeingRemovedRequest() {
    try{
      ((Group) (group)).removeUser("Mike", "Bhargavi");
    } catch(InvalidAdminException iae) {
      assertEquals("Bhargavi is not an admin of FSE group", iae.getMessage());
    }
  }
}