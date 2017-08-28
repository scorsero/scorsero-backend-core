package io.github.scorsero.corebackend.data.repository;

import io.github.scorsero.corebackend.data.Score;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by dim3coder on 8/28/17.
 */
public interface ScoreRepository extends JpaRepository<Score, Long> {

  public List<Score> getAllByUserId(Long userId);


}
