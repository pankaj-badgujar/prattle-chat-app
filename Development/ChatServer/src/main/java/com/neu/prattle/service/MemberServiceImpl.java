package com.neu.prattle.service;

import com.neu.prattle.dao.GroupDao;
import com.neu.prattle.dao.SqlGroupDao;
import com.neu.prattle.dao.SqlUserDao;
import com.neu.prattle.dao.UserDao;
import com.neu.prattle.exceptions.UserAlreadyPresentException;
import com.neu.prattle.model.Group;
import com.neu.prattle.model.IGroup;
import com.neu.prattle.model.IMember;
import com.neu.prattle.model.IUser;
import com.neu.prattle.model.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Implementation for the interface {@link MemberService} which holds all the members for the
 * application is a singleton class.
 *
 * @author Bhargavi Padhya
 * @version 1.1 dated 11/15/2019
 */
public class MemberServiceImpl implements MemberService {

  private final List<IMember> users = new ArrayList<>();
  private final List<IMember> groups = new ArrayList<>();
  private static MemberService memberService;

  private UserDao userDao;
  private GroupDao groupDao;


  private MemberServiceImpl() {
    userDao = SqlUserDao.getInstance();groupDao = SqlGroupDao.getInstance();
  }

  MemberServiceImpl(UserDao userDao, GroupDao groupDao) {
    this.userDao = userDao;
    this.groupDao = groupDao;
  }

  @Override
  public synchronized void addGroup(Group group) {
    if (this.groups.contains(group)) {
      throw new UserAlreadyPresentException(String.format("Group already present with name: %s",
              group.getName()));
    }
    this.groups.add(group);
    groupDao.createGroup(group);
  }

  @Override
  public synchronized User addUser(User user) {
    if (this.users.contains(user)) {
      throw new UserAlreadyPresentException(String.format("User already present with name: %s",
              user.getName()));
    }
    this.users.add(user);
    return userDao.createUser(user);
  }

  /**
   * @param memberName -> The name of the user.
   * @return An optional wrapper supplying the user.
   */
  @Override
  public synchronized Optional<IMember> findMemberByName(String memberName) {
    Optional<IMember> member = userDao.getUser(memberName);
    if (member.isPresent()) {
      return member;
    }
    return groupDao.findGroup(memberName);
  }

  /**
   * Call this method to return an instance of this service.
   *
   * @return this
   */
  public static MemberService getInstance() {
    memberService = memberService == null ? new MemberServiceImpl() : memberService;
    return memberService;
  }

  /**
   * A method to find all the members for given username.
   *
   * @param username the username for whom all their members need to be returned.
   * @return all the members for given username.
   */

  @Override
  public Set<IMember> findAllMembers(String username) {
    Optional<IMember> member = findMemberByName(username);
    Set<IMember> allMembers = new HashSet<>(users);
    member.ifPresent(allMembers::remove);
    member.ifPresent(iMember -> allMembers.addAll(((IUser) iMember).getGroupsForUser()));
    return allMembers;
  }

  @Override
  public boolean deleteGroup(String groupName, String adminName) {
    Optional<IMember> groupObject = groupDao.findGroup(groupName);
    if (groupObject.isPresent()) {
      IGroup group = (IGroup) groupObject.get();
      if (group.getAdmins().contains(adminName)) {
        return groupDao.removeGroup(groupName);
      }
    }
    return false;
  }
}
