package io.github.scorsero.corebackend.security;

import io.github.scorsero.corebackend.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Created by dim3coder on 8/25/17.
 */
@EnableWebSecurity
public class ApiSecurityConfig extends WebSecurityConfigurerAdapter {

  private final UserDetailsService userDetailsService;
  private UserRepository repository;

  @Autowired()
  public ApiSecurityConfig(UserDetailsService userDetailsService, UserRepository repository) {
    this.userDetailsService = userDetailsService;
    this.repository = repository;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable().authorizeRequests()
        .antMatchers("/").permitAll()
        .antMatchers(HttpMethod.POST, "/user/login").permitAll()
        .antMatchers(HttpMethod.POST, "/user/register").permitAll()
        .anyRequest().authenticated()
        .and()
        .addFilterBefore(new JWTLoginFilter("/user/login", authenticationManager()),
            UsernamePasswordAuthenticationFilter.class)
        .addFilterBefore(new JWTAuthenticationFilter(repository),
            UsernamePasswordAuthenticationFilter.class);
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService);
  }
}
