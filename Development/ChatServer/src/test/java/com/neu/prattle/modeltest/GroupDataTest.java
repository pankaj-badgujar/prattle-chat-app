package com.neu.prattle.modeltest;

import com.neu.prattle.model.GroupData;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class GroupDataTest {

  @Test
  public void groupDataCreation(){
    GroupData groupData = new GroupData("devansh",new ArrayList<>());
    assertEquals("devansh",groupData.getGroupName());
    assertEquals(new ArrayList<String>(),groupData.getMembers());
  }

}