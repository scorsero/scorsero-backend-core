package io.github.scorsero.corebackend.api.auth

import io.github.scorsero.corebackend.data.User
import io.github.scorsero.corebackend.data.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Scope
import org.springframework.context.annotation.ScopedProxyMode
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
class UserSession {

  @Autowired
  private UserRepository userRepository

  private User user

  public User getCurrentUser() {
    if (user == null) {
      def authentication = SecurityContextHolder.getContext().getAuthentication()
      String userName = authentication.getName()
      user = userRepository.findByUsername(userName);
    }
  }
}
