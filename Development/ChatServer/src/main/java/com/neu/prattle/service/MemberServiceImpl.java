package com.neu.prattle.service;

import com.neu.prattle.exceptions.UserAlreadyPresentException;
import com.neu.prattle.model.Group;
import com.neu.prattle.model.IMember;
import com.neu.prattle.model.User;

import java.util.ArrayList;
import java.util.List;

public class MemberServiceImpl implements MemberService {
  private final List<IMember> members = new ArrayList<>();
  private static MemberService memberService;

  static {
    memberService = new MemberServiceImpl();
  }
  @Override
  public void createGroup(Group group) {
    this.members.add(group);
  }

  @Override
  public synchronized void createUser(User user) {
    if (this.members.contains(user))
      throw new UserAlreadyPresentException(String.format("User already present with name: %s", user.getName()));
    this.members.add(user);
  }

  /**
   * Call this method to return an instance of this service.
   * @return this
   */
  public static MemberService getInstance() {
    return memberService;
  }
}
