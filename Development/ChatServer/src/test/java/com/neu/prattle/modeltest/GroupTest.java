package com.neu.prattle.modeltest;

import com.neu.prattle.dao.GroupDao;
import com.neu.prattle.dao.UserDao;
import com.neu.prattle.exceptions.InvalidAdminException;
import com.neu.prattle.exceptions.NoSuchUserPresentException;
import com.neu.prattle.exceptions.UserAlreadyPresentException;
import com.neu.prattle.model.Group;
import com.neu.prattle.model.IMember;
import com.neu.prattle.model.User;
import com.neu.prattle.service.MemberService;
import com.neu.prattle.service.MemberServiceImpl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

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

  @Mock
  private GroupDao groupDao;

  @Mock
  private UserDao userDao;

  @InjectMocks
  private MemberServiceImpl memberService;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    when(groupDao.createGroup(any(Group.class))).thenReturn(new Group());
  }

  @Test
  public void testGroupInstantiation() {
    User harshil = new User("Harshil100", memberService);
    User pankaj = new User("Pankaj", memberService);
    User devansh = new User("Devansh", memberService);
    User mike = new User("Mike", memberService);
    users = new ArrayList<>();
    users.add("Harshil100");
    users.add("Devansh");
    users.add("Pankaj");
    users.add("Mike");

    admins = new ArrayList<>();
    admins.add("Mike");

    memberService.addUser(harshil);
    memberService.addUser(pankaj);
    memberService.addUser(devansh);
    memberService.addUser(mike);

    group = new Group("FSE", users, admins, memberService);
    assertEquals("FSE", group.getName());

    group.setName("MSD");
    assertEquals("MSD", group.getName());
    assertEquals(((Group) (group)).getUsers().toString(), users.toString());
    assertEquals(((Group) (group)).getAdmins().toString(), admins.toString());
  }

  @Test(expected = UserAlreadyPresentException.class)
  public void groupAlreadyPresentException() {
    Group group = new Group("FSE", new LinkedList<>(), new LinkedList<>(), memberService);
    memberService.addGroup(group);
    memberService.addGroup(group);
  }

  @Test (expected = NoSuchUserPresentException.class)
  public void testInValidAddAdminRequest() {
    User harshil = new User("Harshil1",memberService);
    User pankaj = new User("Pankaj1",memberService);
    User devansh = new User("Devansh1",memberService);
    User mike = new User("Mike1",memberService);

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
    group = new Group("FSE", users, admins,memberService);
    ((Group) (group)).makeAdmin("Mike1", "Vaibhav");
    admins.add("Vaibhav");
    assertEquals(((Group) (group)).getAdmins().toString(), admins.toString());
  }

  @Test
  public void testValidAddUserRequest() {
    User harshil = new User("Harshil2", memberService);
    User pankaj = new User("Pankaj2", memberService);
    User devansh = new User("Devansh2", memberService);
    User mike = new User("Mike2", memberService);
    User bhargavi = new User("Bhargavi", memberService);

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

    group = new Group("FSE", users, admins, memberService);
    ((Group) (group)).addUser("Mike2", "Bhargavi");
    users.add("Bhargavi");
    assertEquals(((Group) (group)).getUsers().toString(), users.toString());
  }

  @Test
  public void testInvalidAddAdminRequest() {

    User harshil = new User("Harshil3", memberService);
    User pankaj = new User("Pankaj3", memberService);
    User devansh = new User("Devansh3", memberService);
    User mike = new User("Mike3", memberService);
    User bhargavi = new User("Bhargavi1", memberService);

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
    admins.add("Mike3");

    group = new Group("FSE", users, admins, memberService);

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
    group = new Group("FSE", users, admins, memberService);

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

    group = new Group("FSE", users, admins, memberService);
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

    group = new Group("FSE", users, admins, memberService);
    ((Group) (group)).makeAdmin("Mike7", "Vaibhav7");
    admins.add("Vaibhav7");
    assertEquals(((Group) (group)).getAdmins().toString(), admins.toString());

    ((Group) (group)).removeAdmin("Mike7", "Vaibhav7");
    admins.remove("Vaibhav7");
    assertEquals(((Group) (group)).getAdmins().toString(), admins.toString());
  }

  @Test
  public void testInvalidRemoveAdminRequest() {

    User harshil = new User("Harshil200");
    User pankaj = new User("Pankaj200");
    User devansh = new User("Devansh200");
    User mike = new User("Mike200");
    User bhargavi = new User("Bhargavi200");
    User vaibhav = new User("Vaibhav200");

    memberService.addUser(harshil);
    memberService.addUser(pankaj);
    memberService.addUser(devansh);
    memberService.addUser(mike);
    memberService.addUser(bhargavi);
    memberService.addUser(vaibhav);

    users = new ArrayList<>();
    users.add("Harshil200");
    users.add("Devansh200");
    users.add("Pankaj200");
    users.add("Mike200");
    users.add("Bhargavi200");

    admins = new ArrayList<>();
    admins.add("Mike200");

    group = new Group("FSE", users, admins, memberService);

    try {
      ((Group) (group)).removeUser("Bhargavi200", "Mike200");
    } catch (InvalidAdminException iae) {
      assertEquals("Bhargavi200 is not an admin of FSE group", iae.getMessage());
    }
  }

  @Test
  public void testGetAllMembers() {
    User harshil = new User("Harshil5", memberService);
    User devansh = new User("Pankaj5", memberService);
    User pankaj = new User("Devansh5", memberService);
    User mike = new User("Mike5", memberService);

    users = new ArrayList<>();
    users.add("Harshil5");
    users.add("Devansh5");
    users.add("Pankaj5");
    users.add("Mike5");

    admins = new ArrayList<>();
    admins.add("Mike5");

    memberService.addUser(harshil);
    memberService.addUser(devansh);
    memberService.addUser(pankaj);
    memberService.addUser(mike);

    group = new Group("FSE1", users, admins, memberService);

    assertEquals(new HashSet<>(users), group.getAllConnectedMembers());
  }

  @Test
  public void testIMemberConstructor() {
    User harshil = new User("Harshil6");
    User devansh = new User("Pankaj6");
    User pankaj = new User("Devansh6");
    User mike = new User("Mike6");

    memberService.addUser(harshil);
    memberService.addUser(devansh);
    memberService.addUser(pankaj);
    memberService.addUser(mike);

    Set<IMember> user = new HashSet<>();
    user.add(harshil);
    user.add(devansh);
    user.add(pankaj);
    user.add(mike);

    Set<IMember> admin = new HashSet<>();
    admin.add(mike);
    group = new Group("FSE2", user, admin, memberService);

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

    memberService.addUser(harshil);
    memberService.addUser(devansh);
    memberService.addUser(pankaj);

    Set<IMember> user = new HashSet<>();
    user.add(harshil);
    user.add(devansh);
    user.add(pankaj);
    user.add(mike);

    Set<IMember> admin = new HashSet<>();
    admin.add(mike);
    group = new Group("FSE2", user, admin, memberService);
  }
}
