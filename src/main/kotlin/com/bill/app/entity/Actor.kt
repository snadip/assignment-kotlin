package com.bill.app.entity

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*


@Entity
@Table(name = "ACTOR")
data class Actor(
    @Column(name = "actor_name")
    var actorName:String="") {
    @Id
    @GeneratedValue
    @Column(name = "actor_id")
    var actorID:Int=0

    @ManyToMany(fetch = FetchType.LAZY,cascade = [CascadeType.PERSIST,CascadeType.MERGE],mappedBy = "actors")
    @JsonIgnore
    var movies:MutableList<Movie> = mutableListOf()
}