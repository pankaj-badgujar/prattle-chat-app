package com.neu.prattle.daotest;

import com.neu.prattle.dao.SqlGroupDao;
import com.neu.prattle.dao.SqlUserDao;
import com.neu.prattle.model.Group;
import com.neu.prattle.model.User;
import com.neu.prattle.service.MemberServiceImpl;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.LinkedList;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class SqlDaoTest {

  private Session session;
  private MemberServiceImpl memberService;

  @Before
  public void setup() {
    session = Mockito.mock(Session.class);
    memberService = Mockito.mock(MemberServiceImpl.class);
  }

  @Test
  public void testGroupDaoInstantiation() {
    when(session.save(any(Object.class))).thenReturn(null);
    SqlGroupDao dao = new SqlGroupDao(session);
    Group test = new Group("FSE", new LinkedList<>(), new LinkedList<>(), memberService);
    dao.createGroup(test);
    assertNotNull(dao.createGroup(test));
  }

  @Test
  public void testUserDaoInstantiation() {
    when(session.save(any(Object.class))).thenReturn(null);
    SqlUserDao dao = new SqlUserDao(session);
    User test = new User("FSE", memberService);
    assertNotNull(dao.createUser(test));
  }

  @Test
  public void testUserDaoFindInstantiation() {
    when(session.get(User.class, "devansh")).thenReturn(new User("devansh", memberService));
    SqlUserDao dao = new SqlUserDao(session);
    assertNotNull(dao.getUser("devansh"));
  }

  @Test
  public void testGroupDaoFindInstantiation() {
    when(session.get(Group.class, "devansh")).thenReturn(new Group("devansh",
            new ArrayList<>(), new ArrayList<>(), memberService));
    SqlGroupDao dao = new SqlGroupDao(session);
    assertNotNull(dao.findGroup("devansh"));
  }

  @Test
  public void testUserDaoDeleteInstantiation() {
    when(session.get(User.class, "devansh")).thenReturn(new User("devansh", memberService));
    SqlUserDao dao = new SqlUserDao(session);
    assertFalse(dao.removeUser("devansh"));
  }

  @Test
  public void testGroupDaoDeleteInstantiation() {
    when(session.get(Group.class, "devansh")).thenReturn(new Group("devansh",
            new ArrayList<>(), new ArrayList<>(), memberService));
    SqlGroupDao dao = new SqlGroupDao(session);
    assertFalse(dao.removeGroup("devansh"));
  }

}