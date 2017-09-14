package io.github.scorsero.corebackend.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class LocationMark {

  @Id
  @GeneratedValue
  private Long id;

  @Column(name = "score_id")
  private Long scoreId;

  private Long latitude;
  private Long longitude;

  private Integer radius;

  private String name;

  private String description;

  @Column(name = "interaction_type")
  private String interactionType;

  public LocationMark() {
  }

  public LocationMark(Long scoreId, Long latitude, Long longitude, Integer radius,
      String name, String description, String interactionType) {
    this.scoreId = scoreId;
    this.latitude = latitude;
    this.longitude = longitude;
    this.radius = radius;
    this.name = name;
    this.description = description;
    this.interactionType = interactionType;
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

  public Long getLatitude() {
    return latitude;
  }

  public void setLatitude(Long latitude) {
    this.latitude = latitude;
  }

  public Long getLongitude() {
    return longitude;
  }

  public void setLongitude(Long longitude) {
    this.longitude = longitude;
  }

  public Integer getRadius() {
    return radius;
  }

  public void setRadius(Integer radius) {
    this.radius = radius;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getInteractionType() {
    return interactionType;
  }

  public void setInteractionType(String interactionType) {
    this.interactionType = interactionType;
  }
}
