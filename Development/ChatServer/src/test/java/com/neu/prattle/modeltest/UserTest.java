package com.neu.prattle.modeltest;

import com.neu.prattle.dao.GroupDao;
import com.neu.prattle.dao.UserDao;
import com.neu.prattle.exceptions.NoSuchUserPresentException;
import com.neu.prattle.exceptions.UserAlreadyPresentException;
import com.neu.prattle.model.Group;
import com.neu.prattle.model.IMember;
import com.neu.prattle.model.User;
import com.neu.prattle.service.MemberServiceImpl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * @author Bhargavi Padhya
 * @version version 1.1 dated 11/15/2019
 */
public class UserTest {

  private MemberServiceImpl memberService;

  private final String name = "devansh";

  @Before
  public void setup() {
    memberService = Mockito.mock(MemberServiceImpl.class);
  }

  @Test
  public void testUserCreation() {
    User user = new User(name,memberService);
    assertEquals(name, user.getName());
  }

  @Test
  public void testUserNameChange() {
    User user = new User(name,memberService);
    assertEquals(name, user.getName());
    String changedName = "Devansh";
    user.setName(changedName);
    assertEquals(changedName, user.getName());
  }

  @Test
  public void testUsersConnection() {
    String harshilName = "harshil";
    User devansh = new User(name, memberService);
    User harshil = new User(harshilName, memberService);

    when(memberService.findMemberByName("devansh")).thenReturn(Optional.of(devansh));
    when(memberService.findMemberByName("harshil")).thenReturn(Optional.of(harshil));

    harshil.connectTo(devansh);
    devansh.connectTo(harshil);

    assertEquals(devansh.getName(), harshil.getConnectedMembers().get().getName());
    assertEquals(harshilName, devansh.getConnectedMembers().get().getName());

    String pankajName = "pankaj";
    User pankaj = new User(pankajName,memberService);

    when(memberService.findMemberByName("pankaj")).thenReturn(Optional.of(pankaj));
    harshil.connectTo(pankaj);
    assertEquals(pankajName, harshil.getConnectedMembers().get().getName());
  }

  @Test(expected = NoSuchUserPresentException.class)
  public void testInvalidConnectedGroups() {
    User devansh = new User("devansh",memberService);
    User harshil = new User("harshil",memberService);

    // We are adding an invalid user which is yet not registered which should throw an exception
    when(memberService.findMemberByName("harshil")).thenReturn(Optional.empty());
    devansh.connectTo(harshil);
  }

  @Test
  public void testEqualUsers() {
    User devansh = new User(name,memberService);

    User duplicateDevansh = new User(name,memberService);

    assertEquals(devansh, duplicateDevansh);
  }

  @Test
  public void testNotEqualUsers() {
    User devansh = new User(name, memberService);
    int devanshID = devansh.getId();

    User duplicateDevansh = new User(name + "1", memberService);
    int duplicateDevanshID = duplicateDevansh.getId();

    assertEquals(devanshID, duplicateDevanshID);

    Group group = new Group("test", new ArrayList<>(), new ArrayList<>(),memberService);
    assertNotEquals(devansh, duplicateDevansh);
    assertNotEquals(devansh, group);
  }

  @Test
  public void testUserHashCode() {
    User devansh = new User(name,memberService);
    User anotherDevansh = new User(name,memberService);

    assertEquals(devansh.hashCode(), anotherDevansh.hashCode());
  }

  @Test
  public void testGetAllMembers() {
    User harshil = new User("harshil",memberService);
    Set<String> connectedMembers = new HashSet<>();
    connectedMembers.add("harshil");
    assertEquals(connectedMembers, harshil.getAllConnectedMembers());
  }

  @Test
  public void isPasswordCorrect(){
    User harshil = new User("harshil",memberService);
    Set<String> connectedMembers = new HashSet<>();
    connectedMembers.add("harshil");
    assertEquals(connectedMembers, harshil.getAllConnectedMembers());
  }
}
