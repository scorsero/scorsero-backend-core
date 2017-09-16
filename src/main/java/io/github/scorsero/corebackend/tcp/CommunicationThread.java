package io.github.scorsero.corebackend.tcp;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class CommunicationThread extends Thread {

  private Logger logger = LoggerFactory.getLogger(CommunicationThread.class);

  private Socket socket;

  public void setSocketChannel(Socket channel) {
    this.socket = channel;
  }

  @Override
  public void run() {
    logger.debug("Socket connected: {}", socket.isConnected());
    byte[] buf;
    try {
      socket.setSoTimeout(2000);
    } catch (SocketException e) {
      e.printStackTrace();
    }
    while (socket.isConnected()) {
      try {
        buf = new byte[1024];
        while (socket.getInputStream().read() != -1){

        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    logger.debug("Connection destroyed");
  }
}
