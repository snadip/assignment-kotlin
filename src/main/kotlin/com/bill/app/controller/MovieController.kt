package com.bill.app.controller

import com.bill.app.aop.LogExecution
import com.bill.app.entity.Movie
import com.bill.app.entity.Status
import com.bill.app.model.GenericResponse
import com.bill.app.service.MovieService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.sql.Date
import java.time.LocalDate

@RestController
@RequestMapping("movie")
class MovieController(val service: MovieService) {

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Int): ResponseEntity<Movie>? {
        return ResponseEntity.ok().body(service.findMovie(id))
    }

    @GetMapping("/")
    @LogExecution
    fun findAll(): ResponseEntity<List<Movie>>? {
        return ResponseEntity.ok().body(service.findMovies())
    }

    @GetMapping("/{name}/{date}")
    fun findUniqueMovie(@PathVariable name: String,@PathVariable date: String): ResponseEntity<Movie>? {
       return ResponseEntity.ok().body(service.findUniqueMovie(name,LocalDate.parse(date)))
    }

    @GetMapping("name/{name}")
    fun findMovie(@PathVariable name: String): ResponseEntity<List<Movie>>? {
        return ResponseEntity.ok().body(service.findMovie(name))
    }

    @PostMapping("/add")
    fun saveMovie(@Valid @RequestBody movie: Movie): ResponseEntity<Movie>? {
        return ResponseEntity.ok().body(service.saveMovie(movie))
    }

    @PutMapping("/update")
    fun updateMovie(@Valid @RequestBody movie: Movie): ResponseEntity<Movie>? {
        return ResponseEntity.ok().body(service.updateMovie(movie))
    }

    @DeleteMapping("/delete/{id}")
    fun deleteMovie(@Valid @PathVariable id: Int): ResponseEntity<GenericResponse>? {
        service.deleteMovie(id)
        return ResponseEntity.ok().body(GenericResponse(HttpStatus.ACCEPTED,"",Status.SUCCESS,"Movie deleted successfully"))
    }

}