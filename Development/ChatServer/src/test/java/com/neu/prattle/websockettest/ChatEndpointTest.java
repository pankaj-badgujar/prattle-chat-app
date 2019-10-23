package com.neu.prattle.websockettest;

import com.neu.prattle.model.Message;
import com.neu.prattle.model.User;
import com.neu.prattle.service.UserService;
import com.neu.prattle.service.UserServiceImpl;
import com.neu.prattle.websocket.ChatEndpoint;

import org.codehaus.jackson.map.ObjectMapper;
import org.glassfish.tyrus.client.ClientManager;
import org.glassfish.tyrus.server.Server;
import org.glassfish.tyrus.test.tools.TestContainer;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.websocket.ClientEndpointConfig;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for testing the websocket server {@link ChatEndpoint}
 *
 * @author Devansh Gandhi
 * @version dated 2019-10-23
 */
public class ChatEndpointTest extends TestContainer {
  private WebSocketContainer container;
  private ChatEndpointClient chatEndpoint;
  private Appendable appendable;

  @Before
  public void setUp() {
    appendable = new StringBuffer();
    this.container = ContainerProvider.getWebSocketContainer();
    chatEndpoint = new ChatEndpointClient(appendable);
  }

  @Test
  public void testOnOpenInvalid() throws DeploymentException, InterruptedException {
    final Server server = startServer(ChatEndpoint.class);
    final CountDownLatch messageLatch = new CountDownLatch(1);
    try {
      ClientManager clientManager = ClientManager.createClient();
      clientManager.connectToServer(new ChatEndpointClient(appendable),
              ClientEndpointConfig.Builder.create().build(), new URI("ws://localhost:8025/e2e-test/chat/bhargavi"));
    } catch (IOException | URISyntaxException e) {
      e.printStackTrace();
    }
    messageLatch.await(1, TimeUnit.SECONDS);
    stopServer(server);
    assertEquals("", appendable.toString());
  }

  @Test
  public void testOnOpenValid() throws DeploymentException, InterruptedException {
    UserService userService = UserServiceImpl.getInstance();

    userService.addUser(new User("Vaibhav"));
    User user = (new User("bhargavi"));
    user.connectTo("Vaibhav");
    userService.addUser(user);
    final Server server = startServer(ChatEndpoint.class);
    final CountDownLatch messageLatch = new CountDownLatch(1);
    try {
      ClientManager clientManager = ClientManager.createClient();
      ChatEndpointClient chatEndpointClient = new ChatEndpointClient(appendable);
      clientManager.connectToServer(chatEndpointClient,
              ClientEndpointConfig.Builder.create().build(), new URI("ws://localhost:8025/e2e-test/chat/bhargavi"));
      //chatEndpointClient.sendMessage("Hey");
      //assertTrue(messageLatch.await(1, TimeUnit.SECONDS));
    } catch (IOException | URISyntaxException e) {
      e.printStackTrace();
    }
    stopServer(server);
    assertEquals("", appendable.toString());
  }

  private class ChatEndpointClient extends Endpoint {

    ChatEndpointClient(Appendable appendable) {
      logger = appendable;
    }

    private Appendable logger;
    private Session session1;

    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
      this.session1 = session;
      this.session1.addMessageHandler((MessageHandler.Whole<Message>) message -> {
        try {
          logger.append(message.toString());
        } catch (IOException e) {
          e.printStackTrace();
        }
      });
      try {
        ObjectMapper objectMapper = new ObjectMapper();
        session1.getBasicRemote().sendText(objectMapper.writeValueAsString("Hey"));
        Thread.sleep(1000);
      } catch (IOException | InterruptedException e) {
        e.printStackTrace();
      }
    }


  }
}
