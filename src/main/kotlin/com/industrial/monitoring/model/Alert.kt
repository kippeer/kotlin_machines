package com.industrial.monitoring.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "alerts")
data class Alert(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "machine_id", nullable = false)
    val machine: Machine,
    
    @Column(nullable = false)
    val timestamp: LocalDateTime = LocalDateTime.now(),
    
    @Column(nullable = false)
    val type: AlertType,
    
    @Column(nullable = false)
    val parameter: String,
    
    @Column(nullable = false)
    val value: Double,
    
    @Column(nullable = false)
    val threshold: Double,
    
    @Column(nullable = false)
    val message: String,
    
    @Column(nullable = false)
    var resolved: Boolean = false,
    
    @Column(nullable = true)
    var resolvedAt: LocalDateTime? = null
)

enum class AlertType {
    TEMPERATURE_HIGH,
    TEMPERATURE_LOW,
    VIBRATION_HIGH,
    PRESSURE_HIGH,
    PRESSURE_LOW,
    NOISE_HIGH,
    ENERGY_CONSUMPTION_HIGH,
    OTHER
}