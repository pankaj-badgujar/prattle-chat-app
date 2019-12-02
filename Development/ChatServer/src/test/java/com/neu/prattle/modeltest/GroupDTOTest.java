package com.neu.prattle.modeltest;

import com.neu.prattle.model.GroupDTO;

import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.*;

public class GroupDTOTest {
  @Test
  public void testInstantiation() {
    GroupDTO dto = new GroupDTO("FSE", new LinkedList<>(), new LinkedList<>());
    assertEquals("FSE", dto.getUsername());
    assertEquals(new LinkedList<>(), dto.getAdmins());
    assertEquals(new LinkedList<>(), dto.getUsers());
  }

}