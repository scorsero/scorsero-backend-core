package io.github.scorsero.corebackend.security;

import io.github.scorsero.corebackend.data.User;
import io.github.scorsero.corebackend.data.repository.UserRepository;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

/**
 * Created by dim3coder on 8/25/17.
 */
public class JWTAuthenticationFilter extends GenericFilterBean {

  private final UserRepository repository;

  public JWTAuthenticationFilter(UserRepository repository) {
    this.repository = repository;
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    Authentication authentication = TokenAuthenticationService.getAuthentication(
        (HttpServletRequest) request);
    try {
      String username = (String) authentication.getPrincipal();
      if (username != null) {
        User user = repository.findByUsername(username);
        if (user != null && !user.isAccountNonLocked()) {
          throw new CredentialsExpiredException("Bad credentials or expired");
        }
      }
    } catch (NullPointerException e){

    }
    SecurityContextHolder.getContext().setAuthentication(authentication);
    chain.doFilter(request, response);
  }
}
