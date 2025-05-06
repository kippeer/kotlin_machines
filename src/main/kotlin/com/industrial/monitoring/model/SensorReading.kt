package com.industrial.monitoring.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "sensor_readings")
data class SensorReading(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "machine_id", nullable = false)
    val machine: Machine,
    
    @Column(nullable = false)
    val timestamp: LocalDateTime = LocalDateTime.now(),
    
    @Column(nullable = false)
    val temperature: Double,
    
    @Column(nullable = false)
    val vibration: Double,
    
    @Column(nullable = false)
    val pressure: Double,
    
    @Column(nullable = false)
    val noise: Double,
    
    @Column(nullable = true)
    val energyConsumption: Double? = null,
    
    @Column(nullable = true)
    val humidity: Double? = null,
    
    @Column(nullable = true)
    val rotationSpeed: Double? = null
)