package io.github.scorsero.corebackend.data.repository;

import io.github.scorsero.corebackend.data.Priority;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriorityRepository extends JpaRepository<Priority, Long> {

  List<Priority> findAllByUserId(Long userId);

  Priority findByIdAndUserId(Long id, Long userId);

}
