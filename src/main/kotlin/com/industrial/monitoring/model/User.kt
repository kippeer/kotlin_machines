package com.industrial.monitoring.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    
    @Column(nullable = false, unique = true)
    val username: String,
    
    @Column(nullable = false)
    val password: String,
    
    @Column(nullable = false)
    val email: String,
    
    @Column(nullable = false)
    val fullName: String,
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val role: UserRole,
    
    @Column(nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),
    
    @Column(nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now(),
    
    @Column(nullable = false)
    var lastLogin: LocalDateTime? = null
)

enum class UserRole {
    ADMIN,
    ENGINEER,
    OPERATOR
}