package io.github.scorsero.corebackend.api.priority

import io.github.scorsero.corebackend.data.Priority
import io.github.scorsero.corebackend.data.repository.PriorityRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/priority")
class PriorityController {

  @Autowired
  PriorityRepository repository;

  @GetMapping
  ResponseEntity<List<Priority>> fetchAll() {

  }
}
