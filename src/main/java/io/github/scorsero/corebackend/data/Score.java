package io.github.scorsero.corebackend.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;
import javax.persistence.*;

/**
 * Created by dim3coder on 8/25/17.
 */
@Entity(name = "score")
@JsonIgnoreProperties(value = {"userId"},allowGetters = true)
public class Score {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  @Column(name = "user_id")
  private Long userId;
  private Integer priority;
  @Column(name = "create_time")
  private Long creationTime;
  private Boolean completed;
  @Column(name = "completion_time")
  private Long completionTime;
  @Column(name = "update_time")
  private Long updateTime;
  @Column(name = "target_time")
  private Long targetTime;
  @ManyToOne
  @JoinColumn(name = "user_id",nullable = false,insertable = false, updatable = false)
  @JsonIgnore
  private User user;

  public Score() {
  }

  public Score(Long userId, Integer priority, Long creationTime, Boolean completed,
               Long completionTime, Long targetTime) {
    this.userId = userId;
    this.priority = priority;
    this.creationTime = creationTime;
    this.completed = completed;
    this.completionTime = completionTime;
      this.targetTime = targetTime;
  }

  @PreUpdate
  protected void onUpdate() {
    updateTime = new Date().getTime();
  }

  @PrePersist
  protected void onCreate() {
    updateTime = new Date().getTime();
  }


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public Integer getPriority() {
    return priority;
  }

  public void setPriority(Integer priority) {
    this.priority = priority;
  }

  public Long getCreationTime() {
    return creationTime;
  }

  public void setCreationTime(Long creationTime) {
    this.creationTime = creationTime;
  }

  public Boolean getCompleted() {
    return completed;
  }

  public void setCompleted(Boolean completed) {
    this.completed = completed;
  }

  public Long getCompletionTime() {
    return completionTime;
  }

  public void setCompletionTime(Long completionTime) {
    this.completionTime = completionTime;
  }

  public Long getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Long updateTime) {
    this.updateTime = updateTime;
  }

  public Long getTargetTime() {
    return targetTime;
  }

  public void setTargetTime(Long targetTime) {
    this.targetTime = targetTime;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  @Override
  public String toString() {
    return "Score{" +
            "id=" + id +
            ", userId=" + userId +
            ", priority=" + priority +
            ", creationTime=" + creationTime +
            ", completed=" + completed +
            ", completionTime=" + completionTime +
            ", updateTime=" + updateTime +
            ", targetTime=" + targetTime +
            '}';
  }
}
