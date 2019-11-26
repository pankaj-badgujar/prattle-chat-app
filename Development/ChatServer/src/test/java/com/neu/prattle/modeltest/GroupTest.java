package com.neu.prattle.modeltest;

import com.neu.prattle.exceptions.InvalidAdminException;
import com.neu.prattle.exceptions.NoSuchUserPresentException;
import com.neu.prattle.model.Group;
import com.neu.prattle.model.IMember;
import com.neu.prattle.model.User;
import com.neu.prattle.service.MemberService;
import com.neu.prattle.service.MemberServiceImpl;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

  @Test
  public void testGroupInstantiation() {
    User harshil = new User("Harshil");
    User pankaj = new User("Pankaj");
    User devansh = new User("Devansh");
    User mike = new User("Mike");
    users = new ArrayList<>();
    users.add("Harshil");
    users.add("Devansh");
    users.add("Pankaj");
    users.add("Mike");

    admins = new ArrayList<>();
    admins.add("Mike");

    MemberService memberService = MemberServiceImpl.getInstance();
    memberService.addUser(harshil);
    memberService.addUser(pankaj);
    memberService.addUser(devansh);
    memberService.addUser(mike);

    group = new Group("FSE", users, admins);
    assertEquals("FSE", group.getName());

    group.setName("MSD");
    assertEquals("MSD", group.getName());
    assertEquals(((Group) (group)).getUsers().toString(), users.toString());
    assertEquals(((Group) (group)).getAdmins().toString(), admins.toString());
  }

  @Test (expected = NoSuchUserPresentException.class)
  public void testInValidAddAdminRequest() {
    User harshil = new User("Harshil1");
    User pankaj = new User("Pankaj1");
    User devansh = new User("Devansh1");
    User mike = new User("Mike1");

    MemberService memberService = MemberServiceImpl.getInstance();
    memberService.addUser(harshil);
    memberService.addUser(pankaj);
    memberService.addUser(devansh);
    memberService.addUser(mike);

    users = new ArrayList<>();
    users.add("Harshil1");
    users.add("Devansh1");
    users.add("Pankaj1");
    users.add("Mike1");

    admins = new ArrayList<>();
    admins.add("Mike1");
    group = new Group("FSE", users, admins);
    ((Group) (group)).makeAdmin("Mike1", "Vaibhav");
    admins.add("Vaibhav");
    assertEquals(((Group) (group)).getAdmins().toString(), admins.toString());
  }

  @Test
  public void testValidAddUserRequest() {
    User harshil = new User("Harshil2");
    User pankaj = new User("Pankaj2");
    User devansh = new User("Devansh2");
    User mike = new User("Mike2");
    User bhargavi = new User("Bhargavi");

    MemberService memberService = MemberServiceImpl.getInstance();
    memberService.addUser(harshil);
    memberService.addUser(pankaj);
    memberService.addUser(devansh);
    memberService.addUser(mike);
    memberService.addUser(bhargavi);

    users = new ArrayList<>();
    users.add("Harshil2");
    users.add("Devansh2");
    users.add("Pankaj2");
    users.add("Mike2");

    admins = new ArrayList<>();
    admins.add("Mike2");

    group = new Group("FSE", users, admins);
    ((Group) (group)).addUser("Mike2", "Bhargavi");
    users.add("Bhargavi");
    assertEquals(((Group) (group)).getUsers().toString(), users.toString());
  }

  @Test
  public void testInvalidAddAdminRequest() {

    User harshil = new User("Harshil3");
    User pankaj = new User("Pankaj3");
    User devansh = new User("Devansh3");
    User mike = new User("Mike3");
    User bhargavi = new User("Bhargavi1");

    MemberService memberService = MemberServiceImpl.getInstance();
    memberService.addUser(harshil);
    memberService.addUser(pankaj);
    memberService.addUser(devansh);
    memberService.addUser(mike);
    memberService.addUser(bhargavi);


    users = new ArrayList<>();
    users.add("Harshil3");
    users.add("Devansh3");
    users.add("Pankaj3");
    users.add("Mike3");

    admins = new ArrayList<>();
    admins.add("Mike2");

    group = new Group("FSE", users, admins);

    try {
      ((Group) (group)).makeAdmin("Bhargavi1", "Vaibhav");
    } catch (InvalidAdminException iae) {
      assertEquals("Bhargavi1 is not an admin of FSE group", iae.getMessage());
    }
  }

  @Test
  public void testValidRemoveUserRequest() {
    User harshil = new User("Harshil4");
    User pankaj = new User("Pankaj4");
    User devansh = new User("Devansh4");
    User mike = new User("Mike4");
    User bhargavi = new User("Bhargavi4");

    MemberService memberService = MemberServiceImpl.getInstance();
    memberService.addUser(harshil);
    memberService.addUser(pankaj);
    memberService.addUser(devansh);
    memberService.addUser(mike);
    memberService.addUser(bhargavi);


    users = new ArrayList<>();
    users.add("Harshil4");
    users.add("Devansh4");
    users.add("Pankaj4");
    users.add("Mike4");
    users.add("Bhargavi4");

    admins = new ArrayList<>();
    admins.add("Mike4");
    group = new Group("FSE", users, admins);

    ((Group) (group)).removeUser("Mike4", "Bhargavi4");
    users.remove("Bhargavi4");
    assertEquals(((Group) (group)).getUsers().toString(), users.toString());
  }

  @Test
  public void testInvalidRemoveUserRequest() {
    User harshil = new User("Harshil9");
    User pankaj = new User("Pankaj9");
    User devansh = new User("Devansh9");
    User mike = new User("Mike9");
    User bhargavi = new User("Bhargavi9");
    User vaibhav = new User("Vaibhav9");

    MemberService memberService = MemberServiceImpl.getInstance();
    memberService.addUser(harshil);
    memberService.addUser(pankaj);
    memberService.addUser(devansh);
    memberService.addUser(mike);
    memberService.addUser(bhargavi);
    memberService.addUser(vaibhav);

    users = new ArrayList<>();
    users.add("Harshil9");
    users.add("Devansh9");
    users.add("Pankaj9");
    users.add("Mike9");
    users.add("Bhargavi9");
    users.add("Vaibhav9");

    admins = new ArrayList<>();
    admins.add("Mike9");

    group = new Group("FSE", users, admins);
    try {
      ((Group) (group)).removeUser("Harshil7", "Bhargavi7");
    } catch (InvalidAdminException iae) {
      assertEquals("Harshil7 is not an admin of FSE group", iae.getMessage());
    }
  }

  @Test
  public void testValidRemoveAdminRequest() {
    User harshil = new User("Harshil7");
    User pankaj = new User("Pankaj7");
    User devansh = new User("Devansh7");
    User mike = new User("Mike7");
    User bhargavi = new User("Bhargavi7");
    User vaibhav = new User("Vaibhav7");

    MemberService memberService = MemberServiceImpl.getInstance();
    memberService.addUser(harshil);
    memberService.addUser(pankaj);
    memberService.addUser(devansh);
    memberService.addUser(mike);
    memberService.addUser(bhargavi);
    memberService.addUser(vaibhav);

    users = new ArrayList<>();
    users.add("Harshil7");
    users.add("Devansh7");
    users.add("Pankaj7");
    users.add("Mike7");
    users.add("Bhargavi7");
    users.add("Vaibhav7");

    admins = new ArrayList<>();
    admins.add("Mike7");

    group = new Group("FSE", users, admins);
    ((Group) (group)).makeAdmin("Mike7", "Vaibhav7");
    admins.add("Vaibhav7");
    assertEquals(((Group) (group)).getAdmins().toString(), admins.toString());

    ((Group) (group)).removeAdmin("Mike7", "Vaibhav7");
    admins.remove("Vaibhav7");
    assertEquals(((Group) (group)).getAdmins().toString(), admins.toString());
  }

  @Test
  public void testInvalidRemoveAdminRequest() {
    users = new ArrayList<>();
    users.add("Harshil");
    users.add("Devansh");
    users.add("Pankaj");
    users.add("Mike");
    users.add("Bhargavi");

    admins = new ArrayList<>();
    admins.add("Mike");

    group = new Group("FSE", users, admins);

    try {
      ((Group) (group)).removeUser("Bhargavi", "Mike");
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

    MemberService accountService = MemberServiceImpl.getInstance();
    accountService.addUser(harshil);
    accountService.addUser(devansh);
    accountService.addUser(pankaj);
    accountService.addUser(mike);

    group = new Group("FSE1", users, admins);

    assertEquals(new HashSet<String>(users), group.getAllConnectedMembers());
  }

  @Test
  public void testIMemberConstructor() {
    User harshil = new User("Harshil6");
    User devansh = new User("Pankaj6");
    User pankaj = new User("Devansh6");
    User mike = new User("Mike6");

    MemberService accountService = MemberServiceImpl.getInstance();
    accountService.addUser(harshil);
    accountService.addUser(devansh);
    accountService.addUser(pankaj);
    accountService.addUser(mike);

    Set<IMember> user = new HashSet<>();
    user.add(harshil);
    user.add(devansh);
    user.add(pankaj);
    user.add(mike);

    Set<IMember> admin = new HashSet<>();
    admin.add(mike);
    group = new Group("FSE2", user, admin);

    Set<String> userName = new HashSet<>();
    user.forEach(member->userName.add(member.getName()));
    assertEquals(userName, group.getAllConnectedMembers());
  }

  @Test(expected = NoSuchUserPresentException.class)
  public void testInvalidMemberGroup(){
    User harshil = new User("Harshil60");
    User devansh = new User("Pankaj60");
    User pankaj = new User("Devansh60");
    User mike = new User("Mike60");

    MemberService accountService = MemberServiceImpl.getInstance();
    accountService.addUser(harshil);
    accountService.addUser(devansh);
    accountService.addUser(pankaj);

    Set<IMember> user = new HashSet<>();
    user.add(harshil);
    user.add(devansh);
    user.add(pankaj);
    user.add(mike);

    Set<IMember> admin = new HashSet<>();
    admin.add(mike);
    group = new Group("FSE2", user, admin);
  }
}