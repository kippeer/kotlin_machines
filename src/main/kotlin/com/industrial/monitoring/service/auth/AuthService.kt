package com.industrial.monitoring.service.auth

import com.industrial.monitoring.security.JwtTokenProvider
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val authenticationManager: AuthenticationManager,
    private val userDetailsService: UserDetailsService,
    private val jwtTokenProvider: JwtTokenProvider
) {

    fun authenticateUser(username: String, password: String): String {
        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(username, password)
        )
        
        val userDetails = userDetailsService.loadUserByUsername(username)
        return jwtTokenProvider.generateToken(userDetails)
    }
}