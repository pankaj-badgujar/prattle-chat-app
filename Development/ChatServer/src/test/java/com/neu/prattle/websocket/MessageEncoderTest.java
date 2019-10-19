package com.neu.prattle.websocket;

import com.neu.prattle.model.Message;

import org.junit.Before;
import org.junit.Test;


import java.util.List;
import java.util.Map;

import javax.websocket.Decoder;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import static org.junit.Assert.*;

public class MessageEncoderTest {
  private MessageEncoder encoder;

  @Before
  public void setup() {
    encoder = new MessageEncoder();
    encoder.init(new EndpointConfig() {
      @Override
      public List<Class<? extends Encoder>> getEncoders() {
        return null;
      }

      @Override
      public List<Class<? extends Decoder>> getDecoders() {
        return null;
      }

      @Override
      public Map<String, Object> getUserProperties() {
        return null;
      }
    });
  }

  @Test
  public void testEncoding() {
    Message message = new Message();
    message.setContent("Team 6 FSE meeting");
    try {
      assertEquals("{\"from\":null,\"to\":null,\"content\":\"Team 6 FSE meeting\"}",
                encoder.encode(message));
    } catch (EncodeException e) {
      e.printStackTrace();
    }
    encoder.destroy();
  }
}