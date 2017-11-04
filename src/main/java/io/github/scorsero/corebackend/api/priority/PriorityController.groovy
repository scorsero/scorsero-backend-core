package io.github.scorsero.corebackend.api.priority

import io.github.scorsero.corebackend.api.auth.UserSession
import io.github.scorsero.corebackend.data.Priority
import io.github.scorsero.corebackend.data.repository.PriorityRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/priority")
class PriorityController {

  @Autowired
  private PriorityRepository repository;
  @Autowired
  private UserSession session;

  @GetMapping
  def fetchAll() {
    return ResponseEntity.ok(repository.findAllByUserId(session.user.id))
  }

  @PostMapping
  def create(@Validated @RequestBody Priority priority) {
    priority.userId = session.user.id
    def savedPriority = repository.save(priority)
    return ResponseEntity.ok(savedPriority)
  }

  @PutMapping
  def update(@Validated @RequestBody Priority priority) {
    def item = repository.findByIdAndUserId(priority.id, session.user.id)
    if (item != null) {
      return ResponseEntity.ok(repository.save(priority))
    }
    return ResponseEntity.notFound().build()
  }

  @DeleteMapping
  def removeById(@RequestParam Long id) {
    def item = repository.findByIdAndUserId(id, session.user.id);
    if (item != null) {
      repository.delete(item);
      return ResponseEntity.ok(item)
    }
    return ResponseEntity.notFound().build()
  }
}
