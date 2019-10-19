package com.neu.prattle.modeltest;

import com.neu.prattle.exceptions.InvalidAdminException;
import com.neu.prattle.model.Group;
import com.neu.prattle.model.IGroup;

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
  private IGroup group;
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
    assertEquals( "auto-generated-id-here",group.getGroupId());
    assertEquals("FSE", group.getGroupName());

    group.setGroupName("MSD");
    assertEquals(group.getGroupName(), "MSD");
    assertEquals(group.getUsers().toString(), users.toString());
    assertEquals(group.getAdmins().toString(), admins.toString());
  }

  @Test
  public void testValidAddAdminRequest() {
    group.makeAdmin("Mike", "Vaibhav");
    admins.add("Vaibhav");
    assertEquals(group.getAdmins().toString(), admins.toString());
  }

  @Test
  public void testValidAddUserRequest() {
    group.addUser("Mike", "Bhargavi");
    users.add("Bhargavi");
    assertEquals(group.getUsers().toString(), users.toString());
  }

  @Test
  public void testInvalidAddAdminRequest() {
    try {
      group.makeAdmin("Bhargavi", "Vaibhav");
    } catch(InvalidAdminException iae) {
      assertEquals("Bhargavi is not an admin of FSE group", iae.getMessage());
    }
  }

  @Test
  public void testInvalidAddUserRequest() {
    try {
      group.addUser("Bhargavi", "Vaibhav");
    } catch(InvalidAdminException iae) {
      assertEquals("Bhargavi is not an admin of FSE group", iae.getMessage());
    }
  }

  @Test
  public void testValidRemoveUserRequest() {
    group.removeUser("Mike", "Bhargavi");
    users.remove("Bhargavi");
    assertEquals(group.getUsers().toString(), users.toString());
  }

  @Test
  public void testInvalidRemoveUserRequest() {
    try{
      group.removeUser("Harshil", "Bhargavi");
    } catch(InvalidAdminException iae) {
      assertEquals("Harshil is not an admin of FSE group", iae.getMessage());
    }
  }

  @Test
  public void testValidRemoveAdminRequest() {
    group.makeAdmin("Mike", "Vaibhav");
    admins.add("Vaibhav");
    assertEquals(group.getAdmins().toString(), admins.toString());

    group.removeAdmin("Mike", "Vaibhav");
    admins.remove("Vaibhav");
    assertEquals(group.getAdmins().toString(), admins.toString());
  }

  @Test
  public void testInvalidRemoveAdminRequest() {
    try{
      group.removeUser("Bhargavi", "Mike");
    } catch(InvalidAdminException iae) {
      assertEquals("Bhargavi is not an admin of FSE group", iae.getMessage());
    }
  }

  @Test
  public void testInvalidAdminBeingRemovedRequest() {
    try{
      group.removeUser("Mike", "Bhargavi");
    } catch(InvalidAdminException iae) {
      assertEquals("Bhargavi is not an admin of FSE group", iae.getMessage());
    }
  }
}