package io.github.scorsero.corebackend.tcp;


import io.github.scorsero.corebackend.data.repository.UserRepository;
import io.github.scorsero.transport.Transport.TransportEnvelope;
import io.github.scorsero.transport.Transport.TransportEnvelope.EnvelopeType;
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
  @Autowired
  private EnvelopeRouter router;

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
    while (!shutdown && (System.currentTimeMillis() < updateTime+15000)) {
      try {
        OutputStream outputStream = socket.getOutputStream();
        TransportEnvelope transportEnvelope;
        while ((transportEnvelope = TransportEnvelope.parseDelimitedFrom(socket.getInputStream()))
            != null) {
          if(transportEnvelope.getType() == EnvelopeType.PING){
            updateTime = transportEnvelope.getTime();
            continue;
          }

          // TODO: 9/19/17 Move deserialization to structures
          router.route(transportEnvelope);
          logger.debug("message is: {}", transportEnvelope.getTime());
          TransportEnvelope responseTransport = transportEnvelope.toBuilder()
              .setType(EnvelopeType.LOGIN_RESPONSE)
              .setTime(System.currentTimeMillis()).build();
          responseTransport.writeDelimitedTo(outputStream);
          updateTime = System.currentTimeMillis();
        }
      } catch (SocketException e){
        if(e.getMessage().equals("Connection reset")){
          shutdown = true;
        }
      } catch (IOException e) {
        if (System.currentTimeMillis() > updateTime + 10000) {
          logger.debug("Pinging socket"+socket.toString());
          try {
            TransportEnvelope.newBuilder()
                .setType(EnvelopeType.PING).setTime(System.currentTimeMillis()).build().writeDelimitedTo(socket.getOutputStream());
          } catch (IOException e1) {
            e1.printStackTrace();
          }
        }
      }
    }
    logger.debug("Connection destroyed");
  }
}
