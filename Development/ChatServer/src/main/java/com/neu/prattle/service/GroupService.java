package com.neu.prattle.service;

import com.neu.prattle.model.Group;

/**
 * GroupService interface that fulfills requests issued by the GroupController.
 *
 * @author Harshil Mavani
 * @version 1.0 dated 2019-10-18
 */
public interface GroupService {

  /**
   * A method to create groups as posted by the user in the payload. The group consists all
   * necessary details to initialize the object and store it.
   * @param group The group that is requested to be made.
   */
  void createGroup(Group group);
}
