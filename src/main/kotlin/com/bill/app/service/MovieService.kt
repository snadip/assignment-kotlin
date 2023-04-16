package com.bill.app.service

import com.bill.app.entity.Actor
import com.bill.app.entity.Movie
import com.bill.app.exception.CustomException
import com.bill.app.repository.ActorRepository
import com.bill.app.repository.MovieRepository
import com.bill.app.validator.MovieValidator
import org.springframework.stereotype.Service
import java.sql.Date
import java.time.LocalDate

@Service
class MovieService(val movieRepository: MovieRepository,val actorService: ActorService ,val validator: MovieValidator) {

    fun findMovies(): List<Movie> = movieRepository.findAll().toList()
    fun findMovie(id:Int): Movie = movieRepository.findById(id.toString()).orElseThrow { CustomException("No movie found with id ".plus(id)) }
    fun findUniqueMovie(movieName:String,movieReleaseDt: LocalDate): Movie?{
        return movieRepository.findUniqueMovie(movieName,movieReleaseDt)
    }

    fun findMovie(movieName:String): List<Movie>?{
        return movieRepository.findMovie(movieName)
    }

    fun saveMovie(movie: Movie):Movie {
        validator.validateMovie(movie,findUniqueMovie(movie.movieName,movie.movieReleaseDate))
        return movieRepository.save(validatedActors(movie))
    }

    fun updateMovie(movie: Movie):Movie {
        validator.validateMovieU(findMovie(movie.movieID),movie,findUniqueMovie(movie.movieName,movie.movieReleaseDate))
        return movieRepository.save(validatedActors(movie))
    }

    fun deleteMovie(movie: Movie): Unit {
        findMovie(movie.movieID)
        movieRepository.delete(movie)
    }

    fun deleteMovie(id: Int): Unit {
        movieRepository.delete(findMovie(id))
    }

    fun validatedActors(movie: Movie): Movie{
        var actors:MutableList<Actor> = mutableListOf()
        movie.actors.forEach { e -> actors.add(actorService.findActor(e)) }
        movie.actors=actors
        return movie
    }
}