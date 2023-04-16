package com.bill.app.validator

import com.bill.app.entity.Actor
import com.bill.app.entity.Movie
import com.bill.app.exception.CustomException
import com.bill.app.service.MovieService
import org.springframework.stereotype.Service

@Service
class MovieValidator(){

    fun isDuplicateMovie(foundMovie: Movie?): Unit{
        if(foundMovie!=null){
            throw CustomException("Movie already present $foundMovie")
        }
    }

    fun isActorPresent(movie: Movie): Unit{
        if(movie.actors.isNullOrEmpty()){
            throw CustomException("Movie must associate with an actor $movie")
        }
    }

    fun isDuplicateActor(actors: List<Actor>): Unit{
        if(actors.size!=actors.toSet().size){
            throw CustomException("Movie can not have same actors $actors")
        }
    }

    fun validateMovie(movie: Movie,foundMovie: Movie?): Unit{
        isDuplicateMovie(foundMovie)
        isActorPresent(movie)
        isDuplicateActor(movie.actors)
    }

    fun validateMovieU(movieOld: Movie,movieNew: Movie,foundMovie: Movie?): Unit{
        if(movieNew != movieOld){
            isDuplicateMovie(foundMovie)
        }
        isActorPresent(movieNew)
        isDuplicateActor(movieNew.actors)
    }

}