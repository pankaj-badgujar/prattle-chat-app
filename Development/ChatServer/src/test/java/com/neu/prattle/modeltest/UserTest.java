package com.neu.prattle.modeltest;

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
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

@TestMethodOrder(OrderAnnotation.class)
public class UserTest {

  private final String name = "devansh";
  private MemberService memberService;

  @Before
  public void setup(){
    memberService= MemberServiceImpl.getInstance();
  }

  @Test
  @Order(1)
  public void testAFindAllMembers(){
    User bhargavi = new User("bhargavi");
    User mike = new User("mike");
    User pranay = new User("pranay");

    List<String> users = new ArrayList<>();
    users.add("bhargavi");
    users.add("mike");

    memberService.addUser(bhargavi);
    memberService.addUser(mike);
    memberService.addUser(pranay);
    Group group = new Group("fse", users);
    memberService.addGroup(group);

    Set<IMember> allMembers = new HashSet<>();
    allMembers.add(mike);
    allMembers.add(pranay);
    allMembers.add(group);


    Set<IMember> im = memberService.findAllMembers("bhargavi");
    for(IMember im2: im)
      System.out.println(im2.getName());
    System.out.println("===");
    for(IMember im2: allMembers)
      System.out.println(im2.getName());

    assertTrue("true", memberService.findAllMembers("bhargavi").containsAll(allMembers));
  }

  @Test
  @Order(2)
  public void testUserCreation() {
    User user = new User(name);
    assertEquals(name, user.getName());
  }

  @Test
  @Order(3)
  public void testUserNameChange() {
    User user = new User(name);
    assertEquals(name, user.getName());
    String changedName = "Devansh";
    user.setName(changedName);
    assertEquals(changedName, user.getName());
  }

  @Test
  @Order(4)
  public void testUsersConnection() {
    String harshilName = "harshil";
    User devansh = new User("Devansh2");
    User harshil = new User(harshilName);
    memberService.addUser(devansh);
    memberService.addUser(harshil);

    harshil.connectTo(devansh);

    devansh.connectTo(harshil);

    assertEquals(devansh.getName(), harshil.getConnectedMembers().get().getName());
    assertEquals(harshilName, devansh.getConnectedMembers().get().getName());

    String pankajName = "Pankaj1";
    User pankaj = new User(pankajName);
    memberService.addUser(pankaj);


    harshil.connectTo(pankaj);
    assertEquals(pankajName, harshil.getConnectedMembers().get().getName());
  }

  @Test(expected = NoSuchUserPresentException.class)
  @Order(5)
  public void testInvalidConnectedGroups() {
    User devansh = new User("devansh1");
    memberService.addUser(devansh);
    User harshil1 = new User("harshil1");

    // We are adding an invalid user which is yet not registered which should throw an exception
    devansh.connectTo(harshil1);
  }

  @Test
  @Order(6)
  public void testEqualUsers() {
    User devansh = new User(name);

    User duplicateDevansh = new User(name);

    assertEquals(devansh, duplicateDevansh);
  }

  @Test
  @Order(7)
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
  @Order(8)
  public void testUserHashCode() {
    User devansh = new User(name);
    User anotherDevansh = new User(name);

    assertEquals(devansh.hashCode(), anotherDevansh.hashCode());
  }

  @Test
  @Order(9)
  public void testGetAllMembers() {
    User harshil = new User("harshil");
    Set<String> connectedMembers = new HashSet<>();
    connectedMembers.add("harshil");
    assertEquals(connectedMembers, harshil.getAllConnectedMembers());
  }
}