package com.bill.app.controller

import com.bill.app.entity.Actor
import com.bill.app.entity.Movie
import com.bill.app.entity.Status
import com.bill.app.model.GenericResponse
import com.bill.app.service.ActorService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("actor")
class ActorController(val service: ActorService)  {

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Int): ResponseEntity<Actor>? {
        return ResponseEntity.ok().body(service.findActor(id))
    }

    @GetMapping("/")
    fun findAll(): ResponseEntity<List<Actor>>? {
        return ResponseEntity.ok().body(service.findActors())
    }

    @GetMapping("name/{name}")
    fun findByName(@PathVariable name: String): ResponseEntity<Actor>? {
        return ResponseEntity.ok().body(service.findActor(name))
    }

    @PostMapping("/add")
    fun saveActor(@Valid @RequestBody actor: Actor): ResponseEntity<Actor>? {
        return ResponseEntity.ok().body(service.saveActor(actor))
    }

    @PutMapping("/update")
    fun updateActor(@Valid @RequestBody actor: Actor): ResponseEntity<Actor>? {
        return ResponseEntity.ok().body(service.updateActor(actor))
    }

    @DeleteMapping("/delete/{id}")
    fun deleteActor(@Valid @PathVariable id: Int): ResponseEntity<GenericResponse>? {
        service.deleteActor(id)
        return ResponseEntity.ok().body(GenericResponse(HttpStatus.ACCEPTED,"", Status.SUCCESS,"Actor deleted successfully"))
    }

}