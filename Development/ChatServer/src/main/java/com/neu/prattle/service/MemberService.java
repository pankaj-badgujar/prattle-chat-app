package com.neu.prattle.service;

import com.neu.prattle.model.Group;
import com.neu.prattle.model.IMember;
import com.neu.prattle.model.User;

import java.util.Optional;
import java.util.Set;

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
   */
  void addGroup(Group group);


  /***
   * Tries to add a user in the system
   * @param user User object
   * @return User the newly created user that was requested.
   */
  User addUser(User user);

  /**
   * @param name -> The name of the user.
   * @return An optional wrapper supplying the user.
   */
  Optional<IMember> findMemberByName(String name);

  /**
   * A method to find all users and groups.
   *
   * @return A list of members, either user or group.
   */
  Set<IMember> findAllMembers(String username);

  /**
   * Method that can be used to delete a member from the group.
   *
   * @param groupName Username of the group that needs to be deleted
   * @param adminName Username of the user making the request.
   * @return true if the group was deleted else false.
   */
  boolean deleteGroup(String groupName, String adminName);
}
