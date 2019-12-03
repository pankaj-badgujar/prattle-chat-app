package com.neu.prattle.websocket;

/**
 * A simple chat client based on websockets.
 *
 * @author Devansh Gandhi
 * @version dated 2019-10-18
 */

import com.neu.prattle.model.IMember;
import com.neu.prattle.model.IUser;
import com.neu.prattle.model.Message;
import com.neu.prattle.service.MemberService;
import com.neu.prattle.service.MemberServiceImpl;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

/**
 * The Class ChatEndpoint.
 *
 * This class handles Messages that arrive on the server.
 */
@ServerEndpoint(value = "/chat/{username}", decoders = MessageDecoder.class, encoders = MessageEncoder.class)
public class ChatEndpoint {

  /**
   * The account service.
   */
  private MemberService accountService;

  /**
   * The session.
   */
  private Session session;

  /**
   * The Constant chatEndpoints.
   */
  private static final Set<ChatEndpoint> chatEndpoints = new CopyOnWriteArraySet<>();

  /**
   * The users.
   */
  private static HashMap<String, String> users = new HashMap<>();

  public ChatEndpoint(){
    accountService = MemberServiceImpl.getInstance();
  }

  public ChatEndpoint(MemberService memberService){
    accountService = memberService;
  }
  /**
   * On open.
   *
   * Handles opening a new session (websocket connection). If the user is a known user (user
   * management), the session added to the pool of sessions and an announcement to that pool is made
   * informing them of the new user.
   *
   * If the user is not known, the pool is not augmented and an error is sent to the originator.
   *
   * @param session  the web-socket (the connection)
   * @param username the name of the user (String) used to find the associated UserService object
   * @throws IOException     Signals that an I/O exception has occurred.
   * @throws EncodeException the encode exception
   */
  @OnOpen
  public void onOpen(Session session, @PathParam("username") String username) throws IOException, EncodeException {

    Optional<IMember> member = accountService.findMemberByName(username);
    if (!member.isPresent()) {
      Message error = Message.messageBuilder()
              .setMessageContent(String.format("User %s could not be found", username))
              .build();

      session.getBasicRemote().sendObject(error);
      return;
    }

    addEndpoint(session, username);
  }

  /**
   * Adds a newly opened session to the pool of sessions.
   *
   * @param session  the newly opened session
   * @param username the user who connected
   */
  private void addEndpoint(Session session, String username) {
    this.session = session;
    chatEndpoints.add(this);
    /* users is a hashmap between session ids and users */
    users.put(session.getId(), username);
  }

  /**
   * On message.
   *
   * When a message arrives, broadcast it to all connected users.
   *
   * @param session the session originating the message
   * @param message the text of the inbound message
   */
  @OnMessage
  public void onMessage(Session session, Message message) {

    broadcastToTheConnectUser(message.getFrom(), message);
  }

  /**
   * On close.
   *
   * Closes the session by removing it from the pool of sessions and broadcasting the news to
   * everyone else.
   *
   * @param session the session
   */
  @OnClose
  public void onClose(Session session) {
    chatEndpoints.remove(this);
  }

  /**
   * On error.
   *
   * Handles situations when an error occurs.  Not implemented.
   *
   * @param session   the session with the problem
   * @param throwable the action to be taken.
   */
  @OnError
  public void onError(Session session, Throwable throwable) {
    // Do error handling here
  }

  private void broadcastToTheConnectUser(String userFrom, Message message) {
    Optional<IMember> user = accountService.findMemberByName(userFrom);

    Optional<IMember> toMember = user.isPresent() ? ((IUser) user.get()).getConnectedMembers()
            : Optional.empty();

    Set<String> allConnectedMembers = toMember.isPresent() ?
            toMember.get().getAllConnectedMembers() : new HashSet<>();

    for (String connectedMember : allConnectedMembers) {
      String toUserSessionId = getSessionID(connectedMember);
      sendTo(toUserSessionId, message);
    }
  }

  private void sendTo(String seesionID, Message message) {
    chatEndpoints.forEach(endpoint -> {
      synchronized (endpoint) {
        try {
          if (endpoint.session.getId().equals(seesionID)) {
            endpoint.session.getBasicRemote()
                    .sendObject(message);
          }
        } catch (EncodeException | NullPointerException | IOException e) {
          // Add a logger here to handle exception
        }
      }
    });
  }

  private String getSessionID(String toUser) {
    final String[] sessionIdOfToUser = new String[1];
    users.forEach((sessionID, user) -> {
      if (user.equals(toUser)) {
        sessionIdOfToUser[0] = sessionID;
      }
    });
    return sessionIdOfToUser[0];
  }
}
