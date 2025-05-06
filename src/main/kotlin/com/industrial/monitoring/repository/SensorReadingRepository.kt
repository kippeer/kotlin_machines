package com.industrial.monitoring.repository

import com.industrial.monitoring.model.SensorReading
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface SensorReadingRepository : JpaRepository<SensorReading, Long> {
    fun findByMachineId(machineId: Long): List<SensorReading>
    
    fun findByMachineIdAndTimestampBetween(machineId: Long, start: LocalDateTime, end: LocalDateTime): List<SensorReading>
    
    @Query("SELECT r FROM SensorReading r WHERE r.machine.id = :machineId ORDER BY r.timestamp DESC LIMIT 1")
    fun findLatestByMachineId(machineId: Long): SensorReading?
    
    @Query("SELECT AVG(r.temperature) FROM SensorReading r WHERE r.machine.id = :machineId AND r.timestamp BETWEEN :start AND :end")
    fun findAverageTemperatureByMachineIdAndTimeRange(machineId: Long, start: LocalDateTime, end: LocalDateTime): Double
    
    @Query("SELECT AVG(r.vibration) FROM SensorReading r WHERE r.machine.id = :machineId AND r.timestamp BETWEEN :start AND :end")
    fun findAverageVibrationByMachineIdAndTimeRange(machineId: Long, start: LocalDateTime, end: LocalDateTime): Double
    
    @Query("SELECT AVG(r.energyConsumption) FROM SensorReading r WHERE r.machine.id = :machineId AND r.timestamp BETWEEN :start AND :end AND r.energyConsumption IS NOT NULL")
    fun findAverageEnergyConsumptionByMachineIdAndTimeRange(machineId: Long, start: LocalDateTime, end: LocalDateTime): Double?
    
    @Query("SELECT MAX(r.energyConsumption) FROM SensorReading r WHERE r.machine.id = :machineId AND r.timestamp BETWEEN :start AND :end AND r.energyConsumption IS NOT NULL")
    fun findMaxEnergyConsumptionByMachineIdAndTimeRange(machineId: Long, start: LocalDateTime, end: LocalDateTime): Double?
}