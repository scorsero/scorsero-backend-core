package io.github.scorsero.corebackend.security;

import io.github.scorsero.corebackend.data.User;
import io.github.scorsero.corebackend.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * Created by dim3coder on 8/25/17.
 */
@Component("authUserDetailsService")
public class AuthUserDetailsService implements UserDetailsService {

  private UserRepository repository;

  @Autowired
  public AuthUserDetailsService(UserRepository repository) {
    this.repository = repository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = repository.findByUsername(username);
    if (user == null) {
      throw new UsernameNotFoundException("No username present with username: " + username);
    }
    return user;
  }
}
