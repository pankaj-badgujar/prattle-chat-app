package com.neu.prattle.modeltest;

import com.neu.prattle.exceptions.InvalidAdminException;
import com.neu.prattle.exceptions.NoSuchUserPresentException;
import com.neu.prattle.model.Group;
import com.neu.prattle.model.IMember;
import com.neu.prattle.model.User;
import com.neu.prattle.service.MemberServiceImpl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
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

  private User harshil;
  private User pankaj;
  private User devansh;
  private User mike;
  private User vaibhav;

  private String harshilName = "Harshil";
  private String pankajName = "Pankaj";
  private String devanshName = "Devansh";
  private String mikeName = "Mike";
  private String bhargaviName = "Bhargavi";
  private String vaibhavName = "Vaibhav";

  private MemberServiceImpl memberService;

  @Before
  public void setup() {
    memberService = Mockito.mock(MemberServiceImpl.class);
    harshil = new User(harshilName, memberService);
    pankaj = new User(pankajName, memberService);
    vaibhav = new User(vaibhavName, memberService);
    devansh = new User(devanshName, memberService);
    mike = new User(mikeName, memberService);

    when(memberService.findMemberByName(harshilName)).thenReturn(Optional.of(harshil));
    when(memberService.findMemberByName(devanshName)).thenReturn(Optional.of(devansh));
    when(memberService.findMemberByName(pankajName)).thenReturn(Optional.of(pankaj));
    when(memberService.findMemberByName(mikeName)).thenReturn(Optional.of(mike));
    when(memberService.findMemberByName(bhargaviName)).thenReturn(Optional.of(new
            User(bhargaviName, memberService)));
    when(memberService.findMemberByName(vaibhavName)).thenReturn(Optional.empty());
  }

  @Test
  public void testGroupInstantiation() {
    users = new ArrayList<>();
    users.add(harshilName);
    users.add(devanshName);
    users.add(pankajName);
    users.add(mikeName);

    admins = new ArrayList<>();
    admins.add(mikeName);

    group = new Group("FSE", users, admins, memberService);
    assertEquals("FSE", group.getName());

    group.setName("MSD");
    assertEquals("MSD", group.getName());
    assertEquals(((Group) (group)).getUsers().toString(), users.toString());
    assertEquals(((Group) (group)).getAdmins().toString(), admins.toString());
  }

  @Test(expected = NoSuchUserPresentException.class)
  public void testInValidAddAdminRequest() {
    users = new ArrayList<>();
    users.add(harshilName);
    users.add(devanshName);
    users.add(pankajName);
    users.add(mikeName);
    users.add(bhargaviName);

    admins = new ArrayList<>();
    admins.add(bhargaviName);

    group = new Group("FSE", users, admins, memberService);

    ((Group) (group)).makeAdmin(bhargaviName, vaibhavName);
    admins.add(vaibhavName);
    assertEquals(((Group) (group)).getAdmins().toString(), admins.toString());
  }

  @Test
  public void testValidAddUserRequest() {
    users = new ArrayList<>();
    users.add(harshilName);
    users.add(devanshName);
    users.add(pankajName);
    users.add(mikeName);

    admins = new ArrayList<>();
    admins.add(mikeName);

    group = new Group("FSE", users, admins, memberService);
    ((Group) (group)).addUser(mikeName, bhargaviName);
    users.add(bhargaviName);
    assertEquals(((Group) (group)).getUsers().toString(), users.toString());
  }

  @Test(expected = InvalidAdminException.class)
  public void testInvalidAddAdminRequest() {

    users = new ArrayList<>();
    users.add(harshilName);
    users.add(devanshName);
    users.add(pankajName);
    users.add(mikeName);

    admins = new ArrayList<>();
    admins.add(mikeName);

    group = new Group("FSE", users, admins, memberService);
    ((Group) (group)).makeAdmin(bhargaviName, harshilName);

  }

  @Test
  public void testValidRemoveUserRequest() {
    users = new ArrayList<>();
    users.add(harshilName);
    users.add(devanshName);
    users.add(pankajName);
    users.add(mikeName);
    users.add(bhargaviName);

    admins = new ArrayList<>();
    admins.add(mikeName);
    group = new Group("FSE", users, admins, memberService);

    ((Group) (group)).removeUser(mikeName, bhargaviName);
    users.remove(bhargaviName);
    assertEquals(((Group) (group)).getUsers().toString(), users.toString());
  }

  @Test(expected = InvalidAdminException.class)
  public void testInvalidRemoveUserRequest() {
    users = new ArrayList<>();
    users.add(harshilName);
    users.add(devanshName);
    users.add(pankajName);
    users.add(mikeName);
    users.add(bhargaviName);

    admins = new ArrayList<>();
    admins.add(mikeName);

    group = new Group("FSE", users, admins, memberService);
    ((Group) (group)).removeUser(harshilName, bhargaviName);

  }

  @Test
  public void testValidRemoveAdminRequest() {
    users = new ArrayList<>();
    users.add(harshilName);
    users.add(devanshName);
    users.add(pankajName);
    users.add(mikeName);

    admins = new ArrayList<>();
    admins.add(mikeName);

    group = new Group("FSE", users, admins, memberService);
    ((Group) (group)).makeAdmin(mikeName, harshilName);
    admins.add(harshilName);
    assertEquals(((Group) (group)).getAdmins().toString(), admins.toString());

    ((Group) (group)).removeAdmin(mikeName, harshilName);
    admins.remove(harshilName);
    assertEquals(((Group) (group)).getAdmins().toString(), admins.toString());
  }

  @Test(expected = InvalidAdminException.class)
  public void testInvalidRemoveAdminRequest() {
    users = new ArrayList<>();
    users.add(harshilName);
    users.add(devanshName);
    users.add(pankajName);
    users.add(mikeName);

    admins = new ArrayList<>();
    admins.add(mikeName);

    group = new Group("FSE", users, admins, memberService);
    ((Group) (group)).removeUser(bhargaviName, mikeName);
  }

  @Test
  public void testGetAllMembers() {

    users = new ArrayList<>();
    users.add(harshilName);
    users.add(devanshName);
    users.add(pankajName);
    users.add(mikeName);
    users.add(bhargaviName);

    admins = new ArrayList<>();
    admins.add(mikeName);

    group = new Group("FSE1", users, admins, memberService);

    assertEquals(new HashSet<>(users), group.getAllConnectedMembers());
  }

  @Test
  public void testIMemberConstructor() {
    Set<IMember> user = new HashSet<>();
    user.add(harshil);
    user.add(devansh);
    user.add(pankaj);
    user.add(mike);

    Set<IMember> admin = new HashSet<>();
    admin.add(mike);
    group = new Group("FSE2", user, admin, memberService);

    Set<String> userName = new HashSet<>();
    user.forEach(member -> userName.add(member.getName()));
    assertEquals(userName, group.getAllConnectedMembers());
  }

  @Test
  public void testSetMembers() {

    users = new ArrayList<>();
    users.add(harshilName);

    admins = new ArrayList<>();
    admins.add(harshilName);

    group = new Group("FSE1", users, admins, memberService);

    users = new ArrayList<>();
    users.add(devanshName);

    admins = new ArrayList<>();
    admins.add(devanshName);
    Group group2 = new Group("FSE2", users, admins, memberService);

    group.setGroupsForUser(group2);
    List<IMember> value = new ArrayList<>();
    value.add(group);
    assertEquals(value, harshil.getGroupsForUser());
  }

  @Test(expected = NoSuchUserPresentException.class)
  public void testInvalidMemberGroup() {

    Set<IMember> user = new HashSet<>();
    user.add(harshil);
    user.add(devansh);
    user.add(pankaj);
    user.add(mike);
    user.add(vaibhav);

    Set<IMember> admin = new HashSet<>();
    admin.add(mike);
    group = new Group("FSE2", user, admin, memberService);
  }
}
