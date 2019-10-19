package com.neu.prattle.service;

import com.neu.prattle.exceptions.NoSuchUserPresentException;
import com.neu.prattle.model.Group;
import com.neu.prattle.model.IGroup;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class GroupServiceImpl implements GroupService {

  private final Set<IGroup> groups = new HashSet<>();
  private final UserService userService = UserServiceImpl.getInstance();
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
  public void createGroup(String userName, String groupName) {
    if(!userService.findUserByName(userName).isPresent()) {
      throw new NoSuchUserPresentException("User " + userName + " is not a valid user.");
    }
    IGroup group = new Group(groupName, new ArrayList<>(), new ArrayList<>());
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
