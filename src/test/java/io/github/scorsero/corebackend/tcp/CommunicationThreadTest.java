package io.github.scorsero.corebackend.tcp;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import io.github.scorsero.transport.Transport.LoginRequest;
import io.github.scorsero.transport.Transport.TransportEnvelope;
import io.github.scorsero.transport.Transport.TransportEnvelope.EnvelopeType;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Scanner;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommunicationThreadTest {

  @Autowired SocketServerComponent socketServerComponent;

  @Test
  public void startSpring_SocketServerComponentExist() throws Exception {
    assertNotNull(socketServerComponent);
  }

  /**
   * Raw test used for debugging
   */
  @Test
  public void sendingAuth_authHandled() throws Exception {
    SocketChannel sChannel = null;
    try {
      sChannel = SocketChannel.open();
      sChannel.configureBlocking(true);
      sChannel.connect(new InetSocketAddress("127.0.0.1", 27015));
      OutputStream outputStream = sChannel.socket().getOutputStream();
      TransportEnvelope build = TransportEnvelope.newBuilder().setTime(new Date().getTime())
          .setType(EnvelopeType.LOGIN_REQUEST)
          .setData(LoginRequest.newBuilder().setToken("mylovelytoken").build().toByteString())
          .build();
      build.writeDelimitedTo(outputStream);
      InputStream inputStream = sChannel.socket().getInputStream();
      TransportEnvelope transportEnvelope = TransportEnvelope.parseDelimitedFrom(inputStream);
      assertThat(transportEnvelope.getType(), is(EnvelopeType.LOGIN_RESPONSE));
      build.writeDelimitedTo(outputStream);
      outputStream.flush();
      outputStream.close();
      sChannel.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}