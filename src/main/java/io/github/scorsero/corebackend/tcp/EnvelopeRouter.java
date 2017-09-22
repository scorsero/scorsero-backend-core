package io.github.scorsero.corebackend.tcp;

import com.google.protobuf.InvalidProtocolBufferException;
import io.github.scorsero.transport.Transport.LoginRequest;
import io.github.scorsero.transport.Transport.TransportEnvelope;
import org.springframework.stereotype.Component;

@Component
public class EnvelopeRouter {

  public void route(TransportEnvelope envelope){
    switch (envelope.getType()) {
      case PING:
        break;
      case LOGIN_REQUEST:
        try {
          LoginRequest request = LoginRequest.parseFrom(envelope.getData());
          // TODO: 9/22/17 Add auth logic
        } catch (InvalidProtocolBufferException e) {
          e.printStackTrace();
        }

      case LOGIN_RESPONSE:
        break;
      case MESSAGE:
        break;
      case UNRECOGNIZED:
        break;
    }

  }
}
