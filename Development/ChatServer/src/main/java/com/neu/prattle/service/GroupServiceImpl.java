package com.neu.prattle.service;

import com.neu.prattle.model.Group;

import com.neu.prattle.model.IGroup;

import java.util.HashSet;
import java.util.Set;

public class GroupServiceImpl implements GroupService {

  private final Set<IGroup> groups = new HashSet<>();
  private static GroupService groupService;


  static {
    groupService = new GroupServiceImpl();
  }

  /***
   * UserServiceImpl is a Singleton class.
   */
  private GroupServiceImpl() {

  }

  @Override
  public void createGroup(Group group) {
    this.groups.add(group);
  }

  /**
   * Call this method to return an instance of this service.
   * @return this
   */
  public static GroupService getInstance() {
    return groupService;
  }

}
