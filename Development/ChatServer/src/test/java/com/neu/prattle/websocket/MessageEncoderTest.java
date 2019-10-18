package com.neu.prattle.websocket;

import com.neu.prattle.model.Message;

import org.junit.Before;
import org.junit.Test;

import javax.websocket.EncodeException;

import static org.junit.Assert.*;

public class MessageEncoderTest {
  private MessageEncoder encoder;

  @Before
  public void setup() {
    encoder = new MessageEncoder();
  }

  @Test
  public void testEncoding() {
    Message message = new Message();
    message.setContent("Team 6 FSE meeting");
    try{
      assertEquals("{\"from\":null,\"to\":null,\"content\":\"Team 6 FSE meeting\"}",
              encoder.encode(message));
    } catch (EncodeException ee) {
      fail();
    }
  }
}