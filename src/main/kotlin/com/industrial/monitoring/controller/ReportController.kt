package com.industrial.monitoring.controller

import com.industrial.monitoring.dto.EnergyConsumptionReportDTO
import com.industrial.monitoring.dto.FailureReportDTO
import com.industrial.monitoring.dto.UptimeReportDTO
import com.industrial.monitoring.service.ReportService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping("/reports")
@Tag(name = "Reports", description = "APIs for operational reports on machine performance")
class ReportController(private val reportService: ReportService) {

    @GetMapping("/uptime")
    @Operation(summary = "Get uptime report", description = "Retrieves uptime statistics for all machines")
    fun getUptimeReport(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) start: LocalDateTime,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) end: LocalDateTime
    ): ResponseEntity<List<UptimeReportDTO>> {
        return ResponseEntity.ok(reportService.getUptimeReport(start, end))
    }
    
    @GetMapping("/uptime/{machineId}")
    @Operation(summary = "Get machine uptime report", description = "Retrieves uptime statistics for a specific machine")
    fun getMachineUptimeReport(
        @PathVariable machineId: Long,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) start: LocalDateTime,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) end: LocalDateTime
    ): ResponseEntity<UptimeReportDTO> {
        return ResponseEntity.ok(reportService.getMachineUptimeReport(machineId, start, end))
    }
    
    @GetMapping("/failures")
    @Operation(summary = "Get failure report", description = "Retrieves failure statistics for all machines")
    fun getFailureReport(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) start: LocalDateTime,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) end: LocalDateTime
    ): ResponseEntity<List<FailureReportDTO>> {
        return ResponseEntity.ok(reportService.getFailureReport(start, end))
    }
    
    @GetMapping("/energy")
    @Operation(summary = "Get energy consumption report", description = "Retrieves energy usage statistics for machines")
    fun getEnergyConsumptionReport(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) start: LocalDateTime,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) end: LocalDateTime
    ): ResponseEntity<List<EnergyConsumptionReportDTO>> {
        return ResponseEntity.ok(reportService.getEnergyConsumptionReport(start, end))
    }
}