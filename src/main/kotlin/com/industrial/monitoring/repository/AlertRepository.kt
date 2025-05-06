package com.industrial.monitoring.repository

import com.industrial.monitoring.model.Alert
import com.industrial.monitoring.model.AlertType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface AlertRepository : JpaRepository<Alert, Long> {
    fun findByMachineId(machineId: Long): List<Alert>
    
    fun findByMachineIdAndResolvedAndTimestampBetween(
        machineId: Long,
        resolved: Boolean,
        start: LocalDateTime,
        end: LocalDateTime
    ): List<Alert>
    
    fun findByTypeAndResolvedFalse(type: AlertType): List<Alert>
    
    @Query("SELECT COUNT(a) FROM Alert a WHERE a.machine.id = :machineId AND a.timestamp BETWEEN :start AND :end")
    fun countAlertsByMachineIdAndTimeRange(machineId: Long, start: LocalDateTime, end: LocalDateTime): Int
}