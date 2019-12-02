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

  @GeneratedValue(strategy= GenerationType.AUTO)
  private int id;

  @Id
  protected String name;

  @Transient
  protected MemberService ms;

  public AbstractMember() {
    this.ms = MemberServiceImpl.getInstance();
    this.name = null;
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
