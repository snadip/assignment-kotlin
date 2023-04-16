package com.bill.app.exception

import com.bill.app.aop.LogExecution
import com.bill.app.entity.Status
import com.bill.app.model.GenericResponse
import mu.KotlinLogging
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

private val logger = KotlinLogging.logger { }

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
class ExceptionControllerAdvice {

    @ExceptionHandler
    @LogExecution
    fun handleIllegalStateException(ex: IllegalStateException): ResponseEntity<GenericResponse> {
        val errorResponse = GenericResponse(HttpStatus.NOT_FOUND,ex.message,Status.FAIL,ex.message)
        return ResponseEntity.notFound().build()
    }

    @ExceptionHandler
    @LogExecution
    fun handleMethodArgumentNotValidException(ex: MethodArgumentNotValidException): ResponseEntity<GenericResponse> {
        val errorResponse = GenericResponse(HttpStatus.BAD_REQUEST,ex.message,Status.FAIL,ex.message)
        return ResponseEntity.badRequest().body(errorResponse)
    }

    @ExceptionHandler
    @LogExecution
    fun handleCustomException(ex: CustomException): ResponseEntity<GenericResponse> {
        val errorResponse = GenericResponse(HttpStatus.BAD_REQUEST,ex.message,Status.FAIL,ex.message)
        return ResponseEntity.badRequest().body(errorResponse)
    }

    @ExceptionHandler
    @LogExecution
    fun handleException(ex: Exception): ResponseEntity<GenericResponse> {
        val errorResponse = GenericResponse(HttpStatus.BAD_REQUEST,ex.message,Status.FAIL,ex.message)
        return ResponseEntity.internalServerError().body(errorResponse)
    }

}