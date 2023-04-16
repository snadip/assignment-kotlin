package com.bill.app.validator

import com.bill.app.entity.Actor
import com.bill.app.exception.CustomException
import com.bill.app.service.ActorService
import org.springframework.stereotype.Service

@Service
class ActorValidator() {

    fun validateActor(actor: Actor,foundActor: Actor?): Unit{
        isDuplicateActor(foundActor)
    }

    fun validateActorU(oldActor: Actor,newActor: Actor,foundActor: Actor?): Unit{
        if(oldActor!=newActor){
            isDuplicateActor(foundActor)
        }
    }

    fun validateActorD(actor: Actor): Unit{
        if(!actor.movies.isNullOrEmpty()){
            throw CustomException("Actor associated with a movie can not be deleted. Movies ${actor.movies}")
        }
    }

    fun isDuplicateActor(foundActor: Actor?): Unit{
        if(foundActor!=null && foundActor.actorName!=null){
            throw CustomException("Actor with same name already exists $foundActor")
        }
    }
}