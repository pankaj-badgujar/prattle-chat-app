package com.neu.prattle.modeltest;

import com.neu.prattle.model.UserDTO;

import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class UserDTOTest {

  @Test
  public void testInstantiation(){
    UserDTO dto = new UserDTO(1,"harshil", null, new LinkedList<>());
    assertNull(dto.getConnectedTo());
    assertEquals("harshil",dto.getUsername());
    assertEquals(new LinkedList<>(), dto.getGroups());
    assertEquals(1, dto.getId());
  }
}