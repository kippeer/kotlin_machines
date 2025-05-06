package com.industrial.monitoring.controller

import com.industrial.monitoring.dto.auth.LoginRequest
import com.industrial.monitoring.dto.auth.LoginResponse
import com.industrial.monitoring.service.auth.AuthService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "APIs for user authentication")
class AuthController(private val authService: AuthService) {

    @PostMapping("/login")
    @Operation(summary = "User login", description = "Authenticates a user and returns a JWT token")
    fun login(@Valid @RequestBody loginRequest: LoginRequest): ResponseEntity<LoginResponse> {
        val token = authService.authenticateUser(loginRequest.username, loginRequest.password)
        return ResponseEntity.ok(LoginResponse(token))
    }
}