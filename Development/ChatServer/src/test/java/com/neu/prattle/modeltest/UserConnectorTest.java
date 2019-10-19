package com.neu.prattle.modeltest;

import com.neu.prattle.model.UserConnector;

import org.junit.Test;

import static org.junit.Assert.*;

public class UserConnectorTest {

  @Test
  public void UserConnectorCreation(){
    UserConnector connection = new UserConnector("Devansh","Harshil");
    assertEquals("Devansh",connection.getUserFrom());
    assertEquals("Harshil",connection.getUserTo());
  }
}