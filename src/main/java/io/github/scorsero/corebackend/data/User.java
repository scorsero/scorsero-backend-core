package io.github.scorsero.corebackend.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.Email;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Created by dim3coder on 8/25/17.
 */
@Entity(name = "score_user")
@JsonIgnoreProperties(value = {"id","authorities","enabled","locked"},allowGetters = true)
public class User implements UserDetails {

  @Id
  @GeneratedValue
  private Long id;

  @Column(unique = true)
  @NotNull
  private String username;

  @Email
  @NotNull
  private String email;

  @NotNull
  private String password;

  @Column(name = "create_time")
  private Long creationTime;

  @Column(name = "update_time")
  private Long updateTime;
  @OneToMany(mappedBy = "user")
  @JsonIgnore
  private List<Score> scores;

  private Boolean enabled;
  private Boolean locked;

  public User() {
  }

  public User(String username, String email, String password, Long creationTime,
      Boolean enabled, Boolean locked) {
    this.username = username;
    this.email = email;
    this.password = password;
    this.creationTime = creationTime;
    this.enabled = enabled;
    this.locked = locked;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }


  @PreUpdate
  protected void onUpdate() {
    updateTime = new Date().getTime();
  }

  @PrePersist
  protected void onCreate() {
    creationTime = updateTime = new Date().getTime();
  }


  @Override
  public boolean isEnabled() {
    return enabled;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return !locked;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return new ArrayList<>();
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Long getCreationTime() {
    return creationTime;
  }

  public void setCreationTime(Long creationTime) {
    this.creationTime = creationTime;
  }

  public Long getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Long updateTime) {
    this.updateTime = updateTime;
  }

  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }

  public void setLocked(Boolean locked) {
    this.locked = locked;
  }

  public List<Score> getScores() {
    return scores;
  }

  public void setScores(List<Score> scores) {
    this.scores = scores;
  }
}
