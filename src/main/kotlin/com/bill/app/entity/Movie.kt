package com.bill.app.entity

import jakarta.persistence.*
import java.sql.Date
import java.time.LocalDate

@Entity
@Table(name = "MOVIE")
data class Movie(
    @Column(name = "movie_name")
    var movieName:String="",
    @Column(name = "movie_release_dt")
    var movieReleaseDate:LocalDate=LocalDate.now()) {

    @Id
    @GeneratedValue
    @Column(name = "movie_id")
    var movieID:Int=0

    @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST,CascadeType.MERGE])
    @JoinTable(name = "movie_cast", joinColumns = [ JoinColumn(name = "movie_id") ],inverseJoinColumns = [JoinColumn(name = "actor_id")])
    var actors:MutableList<Actor> = mutableListOf()
}