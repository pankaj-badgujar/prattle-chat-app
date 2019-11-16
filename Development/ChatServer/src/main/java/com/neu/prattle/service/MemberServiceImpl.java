package com.neu.prattle.service;

import com.neu.prattle.exceptions.UserAlreadyPresentException;
import com.neu.prattle.model.Group;
import com.neu.prattle.model.IMember;
import com.neu.prattle.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementation for the interface {@link MemberService} which holds all the members for the
 * application is a singleton class.
 *
 * @author Devansh Gandhi
 * @version 1.0 dated 11/1/2019
 */
public class MemberServiceImpl implements MemberService {

  private final List<IMember> members = new ArrayList<>();
  private static MemberService memberService;

  private MemberServiceImpl(){}

  static {
    memberService = new MemberServiceImpl();
  }

  @Override
  public synchronized void addGroup(Group group) {
    this.members.add(group);
  }

  @Override
  public synchronized void addUser(User user) {
    if (this.members.contains(user))
      throw new UserAlreadyPresentException(String.format("User already present with name: %s",
              user.getName()));
    this.members.add(user);
  }

  /**
   * @param memberName -> The name of the user.
   * @return An optional wrapper supplying the user.
   */
  @Override
  public synchronized Optional<IMember> findMemberByName(String memberName) {
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
}
