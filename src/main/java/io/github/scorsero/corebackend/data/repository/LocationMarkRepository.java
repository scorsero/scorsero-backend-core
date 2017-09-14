package io.github.scorsero.corebackend.data.repository;

import io.github.scorsero.corebackend.data.LocationMark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationMarkRepository extends JpaRepository<LocationMark,Long> {
}
