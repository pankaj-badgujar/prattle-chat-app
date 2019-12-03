package com.neu.prattle.daotest;

import com.neu.prattle.dao.GroupDao;
import com.neu.prattle.dao.SqlGroupDao;
import com.neu.prattle.dao.SqlUserDao;
import com.neu.prattle.dao.UserDao;
import com.neu.prattle.model.Group;
import com.neu.prattle.model.User;
import com.neu.prattle.service.MemberServiceImpl;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.LinkedList;

import static junit.framework.TestCase.assertNotNull;

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
}