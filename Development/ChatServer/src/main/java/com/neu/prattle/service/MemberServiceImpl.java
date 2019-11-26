package com.neu.prattle.service;

import com.neu.prattle.exceptions.UserAlreadyPresentException;
import com.neu.prattle.model.Group;
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

  private MemberServiceImpl() {
  }

  static {
    memberService = new MemberServiceImpl();
  }

  @Override
  public synchronized void addGroup(Group group) {
    this.groups.add(group);
  }

  @Override
  public synchronized void addUser(User user) {
    if (this.users.contains(user)) {
      throw new UserAlreadyPresentException(String.format("User already present with name: %s",
          user.getName()));
    }
    this.users.add(user);
  }

  /**
   * @param memberName -> The name of the user.
   * @return An optional wrapper supplying the user.
   */
  @Override
  public synchronized Optional<IMember> findMemberByName(String memberName) {
    List<IMember> members = new ArrayList<>();
    members.addAll(users);
    members.addAll(groups);
    return members.stream().filter(member -> member.getName().equals(memberName)).findAny();
  }

  /**
   * Call this method to return an instance of this service.
   *
   * @return this
   */
  public static MemberService getInstance() {
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
    Set<IMember> allMembers = new HashSet<>();
    Optional<IMember> member = findMemberByName(username);
    allMembers.addAll(users);
    member.ifPresent(allMembers::remove);
    member.ifPresent(iMember -> allMembers.addAll(((IUser) iMember).getGroupsForUser()));
    return allMembers;
  }
}

