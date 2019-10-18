package com.neu.prattle.model;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

public class MessageTest {

  @Test
  public void testNullMessage(){
    Message m = new Message();
    Assert.assertEquals("From: null\nTo: null\nContent: null", m.toString());
  }

  @Test
  public void testMessages(){
    Message m = new Message();
    m.setFrom("Devansh");
    m.setTo("Pankaj");
    m.setContent("Team meeting at 4pm in Snell Library.");
    Assert.assertEquals("Devansh",m.getFrom());
    Assert.assertEquals("Pankaj",m.getTo());
    Assert.assertEquals("Team meeting at 4pm in Snell Library.",m.getContent());
  }

  @Test
  public void testToString(){
    Message m = new Message();
    m.setFrom("Bhargavi");
    m.setTo("Harshil");
    m.setContent("New commit ot branch implement-group-model.");
    Assert.assertEquals("From: Bhargavi\nTo: Harshil\nContent: New commit ot branch implement-group-model.", m.toString());
  }

  @Test
  public void testMessageBuilder() {
    Message.MessageBuilder builder = Message.messageBuilder();
    builder.setMessageContent("New commit ot branch implement-group-model.");
    builder.setFrom("Bhargavi");
    builder.setTo("Harshil");
    Message message = builder.build();
    Assert.assertEquals("From: Bhargavi\nTo: Harshil\nContent: New commit ot branch implement-group-model.", message.toString());
  }
}