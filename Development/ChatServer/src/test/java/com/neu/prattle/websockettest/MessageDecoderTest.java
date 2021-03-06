package com.neu.prattle.websockettest;

import com.neu.prattle.websocket.MessageDecoder;

import org.junit.Before;
import org.junit.Test;
import org.json.*;

import java.io.IOException;

import static org.junit.Assert.*;

public class MessageDecoderTest {
  private MessageDecoder decoder;

  @Before
  public void setup() {
    decoder = new MessageDecoder();
  }

  @Test
  public void testMessageDecoding() {
    String encodedMessage = "From: null\n" + "To: null\n" + "Content: Team 6 FSE meeting";
    assertTrue(decoder.willDecode(encodedMessage));
    assertEquals(encodedMessage,
            decoder.decode("{\"from\":null," +
                    "\"to\":null,\"content\":\"Team 6 FSE meeting\"}").toString());
  }

  @Test
  public void testException() {
    String encodedMessage = "{\nmessage: \"test\",\nanother: \"another\"\n}";
    assertNull(decoder.decode(encodedMessage));
  }
}
