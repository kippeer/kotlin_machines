package com.industrial.monitoring.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "machines")
data class Machine(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    
    @Column(nullable = false)
    var name: String,
    
    @Column(nullable = false)
    var model: String,
    
    @Column(nullable = false)
    var serialNumber: String,
    
    @Column(nullable = true)
    var description: String? = null,
    
    @Column(nullable = false)
    var location: String,
    
    @Column(nullable = false)
    var installationDate: LocalDateTime,
    
    @Column(nullable = false)
    var lastMaintenanceDate: LocalDateTime? = null,
    
    @Column(nullable = false)
    var status: MachineStatus = MachineStatus.OPERATIONAL,
    
    @Column(nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),
    
    @Column(nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now(),
    
    @OneToMany(mappedBy = "machine", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val readings: MutableList<SensorReading> = mutableListOf(),
    
    @OneToMany(mappedBy = "machine", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val alerts: MutableList<Alert> = mutableListOf()
)

enum class MachineStatus {
    OPERATIONAL,
    MAINTENANCE,
    STOPPED,
    FAILURE
}