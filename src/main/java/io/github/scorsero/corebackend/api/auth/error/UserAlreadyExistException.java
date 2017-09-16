package io.github.scorsero.corebackend.api.auth.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class UserAlreadyExistException extends Exception {

  public UserAlreadyExistException(String s) {
    super(s);
  }
}
