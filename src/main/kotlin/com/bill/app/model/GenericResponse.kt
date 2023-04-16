package com.bill.app.model

import com.bill.app.entity.Status
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import java.time.LocalDateTime

class GenericResponse (
    var statusCode: HttpStatus?= null,
    var errorMessage: String? = null,
    var status: Status? = null,
    var messege: String? = null,
)