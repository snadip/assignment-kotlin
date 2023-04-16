package com.bill.app.repository

import com.bill.app.entity.Actor
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ActorRepository : CrudRepository<Actor, String> {

    @Query(value = "SELECT * FROM actor WHERE actor_name=?",nativeQuery = true)
    fun findActor(actorName:String): Optional<Actor>
}