package com.neu.prattle.model;

import com.neu.prattle.service.MemberService;
import com.neu.prattle.service.MemberServiceImpl;

import javax.persistence.Column;
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

  @Column(unique = true)
  protected String name;

  @Transient
  protected MemberService ms;

  public AbstractMember() {
    this.ms = MemberServiceImpl.getInstance();
    this.name = null;
  }

  public AbstractMember(MemberService ms) {
    this.ms = ms;
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
