package com.industrial.monitoring.dto

import java.time.LocalDateTime

data class UptimeReportDTO(
    val machineId: Long,
    val machineName: String,
    val totalUptime: Long, // in minutes
    val totalDowntime: Long, // in minutes
    val availabilityPercentage: Double,
    val lastStatus: String,
    val lastStatusChangedAt: LocalDateTime
)

data class FailureReportDTO(
    val machineId: Long,
    val machineName: String,
    val totalFailures: Int,
    val meanTimeBetweenFailures: Double, // in hours
    val lastFailureAt: LocalDateTime?,
    val criticalFailureCount: Int
)

data class EnergyConsumptionReportDTO(
    val machineId: Long,
    val machineName: String,
    val totalConsumption: Double, // in kWh
    val averageConsumption: Double, // in kWh per hour
    val peakConsumption: Double, // in kWh
    val peakConsumptionAt: LocalDateTime?
)