package com.bill.app.test

import com.bill.app.MovieServiceApplication
import com.bill.app.entity.Actor
import com.bill.app.entity.Movie
import com.bill.app.model.GenericResponse
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpMethod
import org.springframework.test.context.jdbc.Sql
import java.time.LocalDate

@SpringBootTest(classes = [MovieServiceApplication::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql("classpath:test_schema.sql","classpath:test_data.sql")
class ActorControllerTest {

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    @Test
    fun testFindActorByID() {
        val result = restTemplate.getForEntity("/actor/101",Actor::class.java)
        assertNotNull(result)
        assertEquals("Actor 101", result.body?.actorName)
    }

    @Test
    fun testFindAllActors() {
        val result = restTemplate.getForEntity("/actor/",List::class.java)
        assertNotNull(result)
        assert(!result.body?.isEmpty()!!)
    }

    @Test
    fun testFindActorByName() {
        val result = restTemplate.getForEntity("/actor/name/Actor 101",Actor::class.java)
        assertNotNull(result)
        assertEquals("Actor 101",result.body?.actorName);
    }

    @Test
    @Order(1)
    fun testSaveActor() {
        val actor=Actor("Actor Added")
        val result=restTemplate.postForEntity("/actor/add",actor,Actor::class.java)
        assertNotNull(result)
        assertEquals("Actor Added", result.body?.actorName)

    }

    @Test
    @Order(2)
    fun testSaveDuplicateActor() {
        val actor = restTemplate.getForEntity("/actor/101",Actor::class.java)
        val result=restTemplate.postForEntity("/actor/add",actor,GenericResponse::class.java)
        println(result)
        assertNotNull(result)
        assertEquals("Actor with same name already exists ${actor.body}", result.body?.errorMessage)

    }

    @Test
    @Order(1)
    fun testUpdateActor() {

        val result = restTemplate.getForEntity("/actor/101",Actor::class.java)
        result.body?.actorName="Actor 101 Updated"
        restTemplate.put("/actor/update",result,Actor::class.java)
        val result2 = restTemplate.getForEntity("/actor/101",Actor::class.java)
        println("testUpdateActor $result2")
        assertNotNull(result2)
        assertEquals("Actor 101 Updated", result2.body?.actorName)
    }

    @Test
    @Order(3)
    fun testDeleteActor() {
        val result=restTemplate.exchange("/actor/delete/106", HttpMethod.DELETE,null,GenericResponse::class.java)
        assertNotNull(result)
        assertEquals("Actor deleted successfully", result.body?.messege)
    }

    @Test
    @Order(3)
    fun testDeleteActorWithMovie() {
        val actor = restTemplate.getForEntity("/actor/101",Actor::class.java)
        val result=restTemplate.exchange("/actor/delete/${actor.body?.actorID}", HttpMethod.DELETE,null,GenericResponse::class.java)
        assertNotNull(result)
        result.body?.messege?.contains("Actor associated with a movie can not be deleted")?.let { assert(it) }
    }
}