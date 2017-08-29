package io.github.scorsero.corebackend.api.auth;

import io.github.scorsero.corebackend.data.User;
import io.github.scorsero.corebackend.data.repository.UserRepository;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by dim3coder on 8/29/17.
 */
@RestController
@RequestMapping("/user/register")
public class RegistrationController {

  private UserRepository repository;

  @Autowired
  public RegistrationController(UserRepository repository) {
    this.repository = repository;
  }

  @PostMapping
  public User register(@RequestBody User user) {
    if (user.getUsername() == null ||
        user.getEmail() == null ||
        user.getPassword() == null) {
      throw new BadCredentialsException("Username, email and password are required fields");
    }
    repository.save(user);
    return user;
  }
}
