package com.bill.app.service

import com.bill.app.entity.Actor
import com.bill.app.exception.CustomException
import com.bill.app.repository.ActorRepository
import com.bill.app.validator.ActorValidator
import org.springframework.stereotype.Service

@Service
class ActorService(val db: ActorRepository, val validator: ActorValidator) {

    fun findActors(): List<Actor> = db.findAll().toList()
    fun findActor(id:Int): Actor = db.findById(id.toString()).orElseThrow { CustomException("No actor found with id ".plus(id)) }
    fun findActor(actorName:String): Actor? = db.findActor(actorName).orElse(null)
    fun findActor(actor: Actor): Actor = db.findActor(actor.actorName).orElseThrow { CustomException("No actor found with name $actor.actorName. Please add actor") }

    fun saveActor(actor: Actor): Actor {
        validator.validateActor(actor,findActor(actor.actorName))
        return db.save(actor)
    }

    fun updateActor(actor: Actor): Actor {
        validator.validateActorU(findActor(actor.actorID),actor,findActor(actor.actorName))
        return db.save(actor)
    }

    fun deleteActor(id: Int): Unit {
        val actor=findActor(id)
        validator.validateActorD(actor)
        db.delete(actor)
    }

}