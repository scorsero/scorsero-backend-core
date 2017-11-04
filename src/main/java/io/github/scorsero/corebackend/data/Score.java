package io.github.scorsero.corebackend.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Data;

/**
 * Created by dim3coder on 8/25/17.
 */
@Data
@Entity(name = "score")
@JsonIgnoreProperties(value = {"userId"}, allowGetters = true)
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
  @JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)
  @JsonIgnore
  private User user;

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
