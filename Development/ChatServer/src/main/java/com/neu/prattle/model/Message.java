package com.neu.prattle.model;

import java.util.UUID;

/***
 * A Basic POJO for Message.
 *
 * @author CS5500 Fall 2019 Teaching staff
 * @version dated 2019-10-06
 */
public class Message {
  /***
   * The name of the user who sent this message.
   */
  private String from;
  /***
   * The name of the user to whom the message is sent.
   */
  private String to;
  /***
   * It represents the contents of the message.
   */
  private String content;

  private String id;

  public Message(String string) {
    id = UUID.randomUUID().toString();
    content = string;
  }

  public Message() {

  }

  @Override
  public String toString() {
    return new StringBuilder()
            //.append("id").append(id)
            .append("From: ").append(from)
            .append("\nTo: ").append(to)
            .append("\nContent: ").append(content)
            .toString();
  }

  public String getFrom() {
    return from;
  }

  public void setFrom(String from) {
    this.from = from;
  }

  public String getTo() {
    return to;
  }

  public void setTo(String to) {
    this.to = to;
  }

  public String getContent() {
    return content;
  }

//  public String getMessageID() {
//    return this.id;
//  }

  public void setContent(String content) {
    this.content = content;
  }

  public static MessageBuilder messageBuilder() {
    return new MessageBuilder();
  }

  /***
   * A Builder helper class to create instances of {@link Message}
   */
  public static class MessageBuilder {
    /***
     * Invoking the build method will return this message object.
     */
    Message message;

    public MessageBuilder() {
      message = new Message();
      message.setFrom("Not set");
    }

    public MessageBuilder setFrom(String from) {
      message.setFrom(from);
      return this;
    }

    public MessageBuilder setTo(String to) {
      message.setTo(to);
      return this;
    }

    public MessageBuilder setMessageContent(String content) {
      message.setContent(content);
      return this;
    }

    public Message build() {
      return message;
    }
  }
}
