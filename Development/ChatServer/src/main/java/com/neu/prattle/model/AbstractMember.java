package com.neu.prattle.model;

import com.neu.prattle.service.MemberService;
import com.neu.prattle.service.MemberServiceImpl;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * An abstract class to encapsulate common functionality of users and groups.
 */
@MappedSuperclass
public abstract class AbstractMember implements IMember {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  protected int id;

  protected String name = null;

  @Transient
  protected transient MemberService ms;

  AbstractMember() {
    this.ms = MemberServiceImpl.getInstance();
  }

  AbstractMember(MemberService memberService) {
    this.ms = memberService;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public int getId() {
    return id;
  }

}