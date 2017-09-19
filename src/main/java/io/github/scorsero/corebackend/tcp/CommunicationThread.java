package io.github.scorsero.corebackend.tcp;


import io.github.scorsero.corebackend.data.User;
import io.github.scorsero.corebackend.data.repository.UserRepository;
import io.github.scorsero.transport.Transport.LoginRequest;
import io.github.scorsero.transport.Transport.TransportEnvelope;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class CommunicationThread extends Thread {

  @Autowired
  private UserRepository repository;

  @Value("${socket.timeout:2000}")
  private Integer timeout;
  private Long updateTime;

  private Logger logger = LoggerFactory.getLogger(CommunicationThread.class);

  private Socket socket;

  @SuppressWarnings("WeakerAccess")
  public void setSocketChannel(Socket channel) {
    this.socket = channel;
  }

  @Override
  public void run() {
    logger.debug("Socket connected: {}", socket.isConnected());
    updateTime = System.currentTimeMillis();
    try {
      socket.setKeepAlive(true);
      socket.setSoTimeout(timeout);
    } catch (SocketException e) {
      e.printStackTrace();
    }
    boolean shutdown = false;
    while (!shutdown && (System.currentTimeMillis()< updateTime+4000)) {
      try {
        TransportEnvelope transportEnvelope;
        while ((transportEnvelope = TransportEnvelope.parseDelimitedFrom(socket.getInputStream())) != null){
          // TODO: 9/19/17 Move deserialization to structures
          switch (transportEnvelope.getType()) {
            case LOGIN_REQUEST:
              LoginRequest request = LoginRequest.parseFrom(transportEnvelope.getData());
              logger.debug("Login request by {} w/ password {}",request.getUsername(),request.getPassword());
              User user = repository.findByUsername(request.getUsername());
              if(user != null) {
                logger.debug("User {} exists!",user.getUsername());
                if(user.getPassword().equals(request.getPassword())){
                  logger.debug("User {} authenticated",user.getUsername());
                }
              }
          }
          logger.debug("message is: {}", transportEnvelope.getTime());
          OutputStream outputStream = socket.getOutputStream();
          TransportEnvelope responseTransport = transportEnvelope.toBuilder()
              .setTime(System.currentTimeMillis()).build();
          responseTransport.writeDelimitedTo(outputStream);
          updateTime = System.currentTimeMillis();
        }
      } catch (IOException e) {
        shutdown = true;
        e.printStackTrace();
      }
    }
    logger.debug("Connection destroyed");
  }
}
