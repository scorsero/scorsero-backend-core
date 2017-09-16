package io.github.scorsero.corebackend.api.auth;

import io.github.scorsero.corebackend.api.auth.error.UserAlreadyExistException;
import io.github.scorsero.corebackend.data.User;
import io.github.scorsero.corebackend.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by dim3coder on 8/29/17.
 */
@SuppressWarnings("ConstantConditions")
@RestController
@RequestMapping("/user/register")
public class RegistrationController {

  private UserRepository repository;

  @Autowired
  public RegistrationController(UserRepository repository) {
    this.repository = repository;
  }

  @PostMapping
  public ResponseEntity<?> register(@RequestBody @Validated User user)
      throws UserAlreadyExistException {
    user.setEnabled(true);
    user.setLocked(false);
    try {
      return ResponseEntity.ok(repository.save(user));
    } catch (DataIntegrityViolationException exception) {
      throw new UserAlreadyExistException("User already exist");
    }
  }
}
