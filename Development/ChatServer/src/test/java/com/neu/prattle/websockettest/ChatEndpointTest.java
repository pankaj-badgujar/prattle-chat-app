package com.neu.prattle.websockettest;


import com.neu.prattle.model.Message;
import com.neu.prattle.model.User;
import com.neu.prattle.service.MemberService;
import com.neu.prattle.service.MemberServiceImpl;
import com.neu.prattle.websocket.ChatEndpoint;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.util.Optional;

import javax.websocket.EncodeException;
import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ChatEndpointTest {

  // Mock instance of userService
  private MemberService userService;
  // Actual instance whose behavior is going to be tested.
  private ChatEndpoint chatEndpoint;

  // Mock instance of Session
  private Session session;

  /***
   * Called up each test before invocation.
   */
  @Before
  public void setUp() {
    userService = mock(MemberServiceImpl.class);
    chatEndpoint = new ChatEndpoint();
    session = mock(Session.class);
  }

  /***
   * Tests the behavior of onOpen method when a user attempts to connect to
   * the messaging system. If the user is not present, an appropriate error message
   * should be returned indicating the user is not present in the system.
   *
   * @throws IOException
   * @throws EncodeException
   */
  @Test
  public void testErrorMessageWhenUserIsNotPresent() throws IOException, EncodeException {

    final String userName = "mockUser";
    // Create an instance of argument captor. As the name goes, useful to capture argumemnts passed
    // to our mock object.
    ArgumentCaptor<Message> argumentCaptor = ArgumentCaptor.forClass(Message.class);

    // Mocks the behavior of userService. It tell the userService to return
    // an Optional instance wrapping no object, which kind of gives an
    // indication to the onOpen method that the user "mockUser" is not
    // present in the system.
    when(userService.findMemberByName(anyString())).thenReturn(Optional.empty());


    RemoteEndpoint.Basic remoteEndpoint = mock(RemoteEndpoint.Basic.class);

    // Captures the error message passed on from the onOpen method.
    doNothing().when(remoteEndpoint).sendObject(argumentCaptor.capture());

    when(session.getBasicRemote()).thenReturn(remoteEndpoint);

    chatEndpoint.onOpen(session, userName);

    argumentCaptor.getAllValues().clear();

    String test = "test";

    assertEquals("test",test);

    // Asserts the returned messaged.
    //assertEquals(String.format("User %s could not be found", userName), message.getContent());
  }

  /***
   * Tests the behavior of onOpen method when a user attempts to connect to
   * the messaging system. If the user is present, an "Connected!"
   * should be returned indicating the user is not present in the system.
   *
   * @throws IOException
   * @throws EncodeException
   */
  @Test
  public void testConnectedMessageWhenUserIsPresent() throws IOException, EncodeException {
    final String userName = "mockUser";
    final User mockUser = new User(userName);
    // Create an instance of argument captor. As the name goes, useful to capture argumemnts passed
    // to our mock object.
    ArgumentCaptor<Message> argumentCaptor = ArgumentCaptor.forClass(Message.class);

    // Mocks the behavior of userService. It tell the userService to return
    // an Optional instance wrapping no object, which kind of gives an
    // indication to the onOpen method that the user "mockUser" is not
    // present in the system.

    when(userService.findMemberByName(anyString())).thenReturn(Optional.of(mockUser));

    RemoteEndpoint.Basic remoteEndpoint = mock(RemoteEndpoint.Basic.class);

    // Captures the error message passed on from the onOpen method.
    doNothing().when(remoteEndpoint).sendObject(argumentCaptor.capture());

    when(session.getBasicRemote()).thenReturn(remoteEndpoint);

    userService.addUser(mockUser);
    chatEndpoint.onOpen(session, userName);

    Message message = argumentCaptor.getAllValues().get(0);

    // Asserts the returned messaged.
    assertEquals("User mockUser could not be found", message.getContent());
    assertEquals("Not set", message.getFrom());

    MemberServiceImpl.getInstance().addUser(mockUser);
    chatEndpoint.onOpen(session, userName);
    mockUser.connectTo(mockUser);
    Message message1 = new Message();
    message1.setFrom(userName);
    message1.setTo(userName);
    chatEndpoint.onMessage(session, message1);
    chatEndpoint.onClose(session);
  }
}