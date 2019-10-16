package com.neu.prattle.model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Test suite for the creation of group instances. A group is a collection of users in the system
 */
public class GroupTest {
  private IGroup group;

  @Before
  public void setup() {
    List<String> users = new ArrayList<>();
    users.add("Harshil");
    users.add("Devansh");
    users.add("Pankaj");
    users.add("Bhargavi");

    List<String> admins = new ArrayList<>();
    admins.add("Mike");
    group = new Group("FSE", users, admins);
  }

  @Test
  public void testGroupInstantiation(){
    assertEquals(group.getGroupId(), "auto-generated-id-here");
    assertEquals(group.getGroupName(), "FSE");


  }

}