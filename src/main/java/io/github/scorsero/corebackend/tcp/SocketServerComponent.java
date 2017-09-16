package io.github.scorsero.corebackend.tcp;

import io.github.scorsero.corebackend.CorebackendApplication;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class SocketServerComponent implements ApplicationContextAware {

  private static Logger logger = LoggerFactory.getLogger(CommunicationThread.class);

  private ServerSocket serverSocket;
  private ApplicationContext context;

  public SocketServerComponent() {
  }

  @EventListener({ContextRefreshedEvent.class})
  void contextRefreshedEvent() throws IOException {
    new Thread(() -> {
      try {
        serverSocket = new ServerSocket(27015);
        while (true) {
          Socket socket = serverSocket.accept();
          logger.debug("Socket {} connected! Sending to communication",socket.getInetAddress());
          CommunicationThread communicationThread = (CommunicationThread) context
              .getBean("communicationThread");
          communicationThread.setSocketChannel(socket);
          communicationThread.start();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }

    }).start();
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.context = applicationContext;
  }
}
