package io.github.scorsero.corebackend.data.repository;

import io.github.scorsero.corebackend.data.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by dim3coder on 8/25/17.
 */
public interface UserRepository extends CrudRepository<User, Long> {
  public User findByUsername(String username);
}
