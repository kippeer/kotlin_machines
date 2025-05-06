package com.industrial.monitoring.controller

import com.industrial.monitoring.dto.SensorReadingCreateDTO
import com.industrial.monitoring.dto.SensorReadingDTO
import com.industrial.monitoring.service.SensorReadingService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping("/machines/{machineId}/readings")
@Tag(name = "Sensor Readings", description = "APIs for managing machine sensor readings")
class ReadingController(private val sensorReadingService: SensorReadingService) {

    @GetMapping
    @Operation(summary = "Get all readings for a machine", description = "Retrieves all sensor readings for a specific machine")
    fun getAllReadings(@PathVariable machineId: Long): ResponseEntity<List<SensorReadingDTO>> {
        return ResponseEntity.ok(sensorReadingService.getReadingsByMachineId(machineId))
    }
    
    @GetMapping("/latest")
    @Operation(summary = "Get latest reading", description = "Retrieves the most recent sensor reading for a machine")
    fun getLatestReading(@PathVariable machineId: Long): ResponseEntity<SensorReadingDTO> {
        return ResponseEntity.ok(sensorReadingService.getLatestReadingByMachineId(machineId))
    }
    
    @GetMapping("/timerange")
    @Operation(summary = "Get readings within time range", description = "Retrieves sensor readings for a specific time period")
    fun getReadingsByTimeRange(
        @PathVariable machineId: Long,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) start: LocalDateTime,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) end: LocalDateTime
    ): ResponseEntity<List<SensorReadingDTO>> {
        return ResponseEntity.ok(sensorReadingService.getReadingsByMachineIdAndTimeRange(machineId, start, end))
    }
    
    @PostMapping
    @Operation(summary = "Record new reading", description = "Records a new sensor reading for a machine")
    fun createReading(
        @PathVariable machineId: Long,
        @Valid @RequestBody readingDTO: SensorReadingCreateDTO
    ): ResponseEntity<SensorReadingDTO> {
        val createdReading = sensorReadingService.createReading(machineId, readingDTO)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReading)
    }
}