package com.bill.app.repository

import com.bill.app.entity.Movie
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.sql.Date
import java.time.LocalDate
import java.util.*

@Repository
interface MovieRepository : CrudRepository<Movie, String>{

    @Query(value = "SELECT * FROM movie WHERE movie_name=? and movie_release_dt=?",nativeQuery = true)
    fun findUniqueMovie(movieName:String,movieReleaseDt: LocalDate): Movie?

    @Query(value = "SELECT * FROM movie WHERE movie_name=?",nativeQuery = true)
    fun findMovie(movieName:String): List<Movie>?
}