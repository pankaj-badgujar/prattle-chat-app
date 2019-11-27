package com.neu.prattle.daotest;

import com.neu.prattle.dao.SqlGroupDao;
import com.neu.prattle.dao.SqlUserDao;
import org.junit.Test;
public class SqlGroupDaoTest {

  @Test
  public void testInstantiation() {
    SqlGroupDao.getInstance();
    SqlUserDao.getInstance();
  }
}