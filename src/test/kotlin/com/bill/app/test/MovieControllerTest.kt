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
class MovieControllerTest {

    @Autowired
    lateinit var restTemplate: TestRestTemplate
    var movie = Movie("",LocalDate.now())

    @Test
    fun testFindMovieByID() {
        movie = restTemplate.getForEntity("/movie/101",Movie::class.java).body!!
        assertNotNull(movie)
        assertEquals("Movie 101",movie.movieName);
    }

    @Test
    fun testFindAllMovies() {
        val result = restTemplate.getForEntity("/movie/",List::class.java)
        assertNotNull(result)
        assert(!result.body?.isEmpty()!!)
    }

    @Test
    fun testFindUniqueMovie() {
        movie = restTemplate.getForEntity("/movie/Movie 101/2021-09-13",Movie::class.java).body!!
        assertNotNull(movie)
        assertEquals("Movie 101",movie.movieName);
    }

    @Test
    @Order(1)
    fun testSaveMovie() {
        val movieNew = Movie("New Movie 99",LocalDate.now())
        val actor1=Actor("Actor 101")
        val actor2=Actor("Actor 102")
        var actors = mutableListOf<Actor>(actor1,actor2)
        movieNew.actors=actors
        val result=restTemplate.postForEntity("/movie/add",movieNew,Movie::class.java)
        assertNotNull(result)
        assertEquals("New Movie 99", result.body?.movieName)

    }

    @Test
    @Order(2)
    fun testSaveDuplicateMovie() {
        movie = restTemplate.getForEntity("/movie/101",Movie::class.java).body!!
        val result=restTemplate.postForEntity("/movie/add",movie,GenericResponse::class.java)
        println(result)
        assertNotNull(result)
        assertEquals("Movie already present $movie", result.body?.errorMessage)

    }

    @Test
    @Order(2)
    fun testSaveDuplicateActors() {
        val movieNew = Movie("New Movie 99",LocalDate.now())
        val actor1=Actor("Actor 101")
        val actor2=Actor("Actor 101")
        var actors = mutableListOf<Actor>(actor1,actor2)
        movieNew.actors=actors
        val result=restTemplate.postForEntity("/movie/add",movieNew,GenericResponse::class.java)
        assertNotNull(result)
        assertEquals("Movie can not have same actors $actors", result.body?.errorMessage)

    }

    @Test
    @Order(2)
    fun testSaveNoActors() {
        val movieNew = Movie("New Movie 99",LocalDate.now())
        var actors = mutableListOf<Actor>()
        movieNew.actors=actors
        val result=restTemplate.postForEntity("/movie/add",movieNew,GenericResponse::class.java)
        assertNotNull(result)
        assertEquals("Movie must associate with an actor $movieNew", result.body?.errorMessage)

    }

    @Test
    @Order(2)
    fun testSaveInvalidActor() {
        val movieNew = Movie("New Movie 99",LocalDate.now())
        val actor1=Actor("Actor not in table")
        var actors = mutableListOf<Actor>(actor1)
        movieNew.actors=actors
        val result=restTemplate.postForEntity("/movie/add",movieNew,GenericResponse::class.java)
        assertNotNull(result)
        assertEquals("No actor found with name $actor1.actorName. Please add actor", result.body?.errorMessage)

    }

    @Test
    @Order(1)
    fun testUpdateMovie() {

        val result = restTemplate.getForEntity("/movie/101",Movie::class.java)
        val actor=Actor("Actor 104")
        result.body?.movieName="101 Updated"
        result.body?.movieReleaseDate=LocalDate.now()
        result.body?.actors?.add(actor)
        val movie:Movie= result.body!!
        restTemplate.put("/movie/update",movie,Movie::class.java)
        val result2 = restTemplate.getForEntity("/movie/101",Movie::class.java)
        println("testUpdateMovie $result2")
        assertNotNull(result2)
        assertEquals("101 Updated", result2.body?.movieName)
    }

    @Test
    @Order(3)
    fun testDeleteMovie() {
        val result=restTemplate.exchange("/movie/delete/101", HttpMethod.DELETE,null,GenericResponse::class.java)
        assertNotNull(result)
        assertEquals("Movie deleted successfully", result.body?.messege)
    }
}