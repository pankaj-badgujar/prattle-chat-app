package com.neu.prattle.model;

import java.util.UUID;

/**
 * An abstract class to encapsulate common functionality of users and groups.
 */
public abstract class AbstractMember implements IMember {

  protected String name;
  protected String connectedTo;
  protected String id;

  AbstractMember() {
    this.name = null;
    this.id = UUID.randomUUID().toString();
    this.connectedTo = null;
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String getId()  {
    return id;
  }
}
