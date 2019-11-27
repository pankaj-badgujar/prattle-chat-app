package com.neu.prattle.dao;

import com.neu.prattle.model.Group;

/**
 * An interface to represent operations offered by a group data access object.
 *
 * @author Harshil Mavani
 * @version 1.0 dated 11/26/2019
 */
public interface GroupDao {

  Group createGroup(Group group);
}
