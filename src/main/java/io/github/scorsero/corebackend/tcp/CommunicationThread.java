package io.github.scorsero.corebackend.tcp;


import io.github.scorsero.transport.Transport.TransportEnvelope;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class CommunicationThread extends Thread {

  @Value("${socket.timeout:2000}")
  private Integer timeout;

  private Logger logger = LoggerFactory.getLogger(CommunicationThread.class);

  private Socket socket;

  @SuppressWarnings("WeakerAccess")
  public void setSocketChannel(Socket channel) {
    this.socket = channel;
  }

  @Override
  public void run() {
    logger.debug("Socket connected: {}", socket.isConnected());
    byte[] buf;
    try {
      socket.setKeepAlive(true);
      socket.setSoTimeout(timeout);
    } catch (SocketException e) {
      e.printStackTrace();
    }
    boolean shutdown = false;
    while (socket.isConnected() && !shutdown) {
      try {
        buf = new byte[1024];
        while (socket.getInputStream().read(buf) != -1) {
          TransportEnvelope transportEnvelope = TransportEnvelope.parseDelimitedFrom(new ByteArrayInputStream(buf));
          logger.debug("message is: {}", transportEnvelope.toString());
          buf = new byte[1024];
        }
      } catch (IOException e) {
        shutdown = true;
        e.printStackTrace();
      }
    }
    logger.debug("Connection destroyed");
  }
}
