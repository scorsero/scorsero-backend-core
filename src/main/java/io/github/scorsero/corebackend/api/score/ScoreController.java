package io.github.scorsero.corebackend.api.score;

import io.github.scorsero.corebackend.api.auth.UserSession;
import io.github.scorsero.corebackend.data.Score;
import io.github.scorsero.corebackend.data.User;
import io.github.scorsero.corebackend.data.repository.ScoreRepository;
import io.github.scorsero.corebackend.data.repository.UserRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by dim3coder on 8/28/17.
 */
@RestController
@RequestMapping("/score")
public class ScoreController {

  private static Logger logger = LoggerFactory.getLogger(ScoreController.class);

  private final ScoreRepository repository;
  private final UserRepository userRepository;

  @Autowired
  UserSession session;

  @Autowired
  public ScoreController(ScoreRepository repository, UserRepository userRepository) {
    this.repository = repository;
    this.userRepository = userRepository;
  }

  @GetMapping
  public ResponseEntity<List<Score>> getScores(@RequestParam(required = false) Long updateTime) {
    User user = session.getUser();
    List<Score> scores;
    if (updateTime != null) {
      scores = repository.getAllByUserIdEqualsAndUpdateTimeAfter(user.getId(), updateTime);
    } else {
      scores = userRepository.findByUsername(user.getUsername()).getScores();
    }
    return ResponseEntity.ok(scores);
  }

  @PostMapping
  public ResponseEntity<Score> saveScore(@Validated @RequestBody Score score) {
    User user = session.getUser();
    score.setUserId(user.getId());
    score = repository.save(score);
    repository.flush();
    logger.debug("Score {} saved by user with id {}", score.toString(), score.getUserId());
    return ResponseEntity.ok(score);
  }

  @PutMapping
  public ResponseEntity<?> updateScore(@Validated @RequestBody Score score) {
    Score oldScore = repository.findByIdAndUserId(score.getId(), session.getUser().getId());
    if (oldScore != null) {
      Score savedScore = repository.save(score);
      return ResponseEntity.ok(savedScore);
    }
    return ResponseEntity.notFound().build();
  }

  @DeleteMapping
  public ResponseEntity<?> deleteScore(@RequestParam Long id) {
    Score score = repository.findByIdAndUserId(id, session.getUser().getId());
    if( score != null) {
      repository.delete(score);
      return ResponseEntity.ok(score);
    }
    return ResponseEntity.notFound().build();
  }

}
