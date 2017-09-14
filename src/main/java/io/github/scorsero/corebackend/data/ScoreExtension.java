package io.github.scorsero.corebackend.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "score_extension")
public class ScoreExtension {

  @Id
  @GeneratedValue
  private Long id;

  @Column(name = "score_id")
  private Long scoreId;

  private Long type;

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
