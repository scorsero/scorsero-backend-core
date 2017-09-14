package io.github.scorsero.corebackend.api.score;

import io.github.scorsero.corebackend.data.ScoreExtension;
import io.github.scorsero.corebackend.data.repository.ScoreExtensionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/score/{id}/")
public class ScoreExtensionController {

  private final ScoreExtensionRepository repository;

  @Autowired
  public ScoreExtensionController(ScoreExtensionRepository repository) {
    this.repository = repository;
  }

  @PutMapping
  public ResponseEntity<ScoreExtension> putExtension(@Validated @RequestBody ScoreExtension extension) {
    return ResponseEntity.ok(repository.save(extension));
  }
}
