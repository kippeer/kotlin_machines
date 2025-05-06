package com.industrial.monitoring.dto

import com.industrial.monitoring.model.SensorReading
import java.time.LocalDateTime

data class SensorReadingDTO(
    val id: Long = 0,
    val machineId: Long,
    val timestamp: LocalDateTime = LocalDateTime.now(),
    val temperature: Double,
    val vibration: Double,
    val pressure: Double,
    val noise: Double,
    val energyConsumption: Double? = null,
    val humidity: Double? = null,
    val rotationSpeed: Double? = null
)

data class SensorReadingCreateDTO(
    val temperature: Double,
    val vibration: Double,
    val pressure: Double,
    val noise: Double,
    val energyConsumption: Double? = null,
    val humidity: Double? = null,
    val rotationSpeed: Double? = null
)

fun SensorReading.toDTO(): SensorReadingDTO = SensorReadingDTO(
    id = this.id,
    machineId = this.machine.id,
    timestamp = this.timestamp,
    temperature = this.temperature,
    vibration = this.vibration,
    pressure = this.pressure,
    noise = this.noise,
    energyConsumption = this.energyConsumption,
    humidity = this.humidity,
    rotationSpeed = this.rotationSpeed
)