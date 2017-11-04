package io.github.scorsero.corebackend.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Created by dim3coder on 8/25/17.
 */
@Data
@Entity(name = "score_user")
@JsonIgnoreProperties(value = {"id", "authorities", "enabled", "locked"}, allowGetters = true)
public class User implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(unique = true)
  @NotNull
  @NotEmpty
  private String username;

  @Email
  @NotNull
  @NotEmpty
  private String email;

  @NotNull
  @NotEmpty
  private String password;

  @Column(name = "create_time")
  private Long creationTime;

  @Column(name = "update_time")
  private Long updateTime;

  @OneToMany(mappedBy = "user")
  @JsonIgnore
  private List<Score> scores;

  @OneToMany(mappedBy = "user")
  @JsonIgnore
  private List<Priority> priorities;

  private Boolean enabled;
  private Boolean locked;


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

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return new ArrayList<>();
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

}
