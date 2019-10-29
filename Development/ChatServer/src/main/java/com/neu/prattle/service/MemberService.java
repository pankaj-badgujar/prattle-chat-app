package com.neu.prattle.service;

import com.neu.prattle.model.Group;
import com.neu.prattle.model.User;

/**
 * A service to represent and carry out operations on members rather than groups or users.
 *
 * @author Harshil Mavani
 * @version 1.0 dated 10/28/2019
 */
public interface MemberService {

  /**
   * A method to create groups as posted by the user in the payload. The group consists all
   * necessary details to initialize the object and store it.
   * @param group The group that is requested to be made.
   */
  void createGroup(Group group);


  /***
   * Tries to add a user in the system
   * @param user User object
   *
   */
  void createUser(User user);
}
