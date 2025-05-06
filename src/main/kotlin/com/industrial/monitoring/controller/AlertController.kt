package com.industrial.monitoring.controller

import com.industrial.monitoring.dto.AlertDTO
import com.industrial.monitoring.service.alert.AlertService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/machines/{machineId}/alerts")
@Tag(name = "Alerts", description = "APIs for managing machine alerts")
class AlertController(private val alertService: AlertService) {

    @GetMapping
    @Operation(summary = "Get all alerts for a machine", description = "Retrieves all alerts generated for a specific machine")
    fun getAllAlerts(@PathVariable machineId: Long): ResponseEntity<List<AlertDTO>> {
        return ResponseEntity.ok(alertService.getAlertsByMachineId(machineId))
    }
    
    @GetMapping("/unresolved")
    @Operation(summary = "Get unresolved alerts", description = "Retrieves active alerts that have not been resolved")
    fun getUnresolvedAlerts(@PathVariable machineId: Long): ResponseEntity<List<AlertDTO>> {
        return ResponseEntity.ok(alertService.getUnresolvedAlertsByMachineId(machineId))
    }
    
    @PutMapping("/{alertId}/resolve")
    @Operation(summary = "Resolve alert", description = "Marks an alert as resolved")
    fun resolveAlert(
        @PathVariable machineId: Long,
        @PathVariable alertId: Long
    ): ResponseEntity<AlertDTO> {
        return ResponseEntity.ok(alertService.resolveAlert(alertId))
    }
}