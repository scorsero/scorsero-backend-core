package io.github.scorsero.corebackend.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity(name = "score_extension")
public class ScoreExtension {

  @Id
  @GeneratedValue
  private Long id;

  @NotNull
  @Column(name = "score_id")
  private Long scoreId;

  @NotNull
  private Long type;

  @ManyToOne
  @JoinColumn(name = "score_id",nullable = false,insertable = false, updatable = false)
  @JsonIgnore
  private Score score;

  public ScoreExtension() {
  }

  public ScoreExtension(Long scoreId, Long type) {
    this.scoreId = scoreId;
    this.type = type;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getScoreId() {
    return scoreId;
  }

  public void setScoreId(Long scoreId) {
    this.scoreId = scoreId;
  }

  public Long getType() {
    return type;
  }

  public void setType(Long type) {
    this.type = type;
  }
}
