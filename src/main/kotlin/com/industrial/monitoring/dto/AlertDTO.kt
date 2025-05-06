package com.industrial.monitoring.dto

import com.industrial.monitoring.model.Alert
import com.industrial.monitoring.model.AlertType
import java.time.LocalDateTime

data class AlertDTO(
    val id: Long = 0,
    val machineId: Long,
    val timestamp: LocalDateTime,
    val type: AlertType,
    val parameter: String,
    val value: Double,
    val threshold: Double,
    val message: String,
    val resolved: Boolean = false,
    val resolvedAt: LocalDateTime? = null
)

fun Alert.toDTO(): AlertDTO = AlertDTO(
    id = this.id,
    machineId = this.machine.id,
    timestamp = this.timestamp,
    type = this.type,
    parameter = this.parameter,
    value = this.value,
    threshold = this.threshold,
    message = this.message,
    resolved = this.resolved,
    resolvedAt = this.resolvedAt
)