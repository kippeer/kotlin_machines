package com.industrial.monitoring.service

import com.industrial.monitoring.dto.SensorReadingCreateDTO
import com.industrial.monitoring.dto.SensorReadingDTO
import com.industrial.monitoring.dto.toDTO
import com.industrial.monitoring.exception.ResourceNotFoundException
import com.industrial.monitoring.model.SensorReading
import com.industrial.monitoring.repository.MachineRepository
import com.industrial.monitoring.repository.SensorReadingRepository
import com.industrial.monitoring.service.alert.AlertService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class SensorReadingService(
    private val sensorReadingRepository: SensorReadingRepository,
    private val machineRepository: MachineRepository,
    private val alertService: AlertService
) {

    fun getReadingsByMachineId(machineId: Long): List<SensorReadingDTO> {
        if (!machineRepository.existsById(machineId)) {
            throw ResourceNotFoundException("Machine not found with id: $machineId")
        }
        return sensorReadingRepository.findByMachineId(machineId).map { it.toDTO() }
    }
    
    fun getReadingsByMachineIdAndTimeRange(
        machineId: Long,
        start: LocalDateTime,
        end: LocalDateTime
    ): List<SensorReadingDTO> {
        if (!machineRepository.existsById(machineId)) {
            throw ResourceNotFoundException("Machine not found with id: $machineId")
        }
        return sensorReadingRepository.findByMachineIdAndTimestampBetween(machineId, start, end)
            .map { it.toDTO() }
    }
    
    fun getLatestReadingByMachineId(machineId: Long): SensorReadingDTO {
        if (!machineRepository.existsById(machineId)) {
            throw ResourceNotFoundException("Machine not found with id: $machineId")
        }
        
        return sensorReadingRepository.findLatestByMachineId(machineId)
            ?.toDTO()
            ?: throw ResourceNotFoundException("No readings found for machine with id: $machineId")
    }

    @Transactional
    fun createReading(machineId: Long, readingDTO: SensorReadingCreateDTO): SensorReadingDTO {
        val machine = machineRepository.findById(machineId)
            .orElseThrow { ResourceNotFoundException("Machine not found with id: $machineId") }
        
        val reading = SensorReading(
            machine = machine,
            temperature = readingDTO.temperature,
            vibration = readingDTO.vibration,
            pressure = readingDTO.pressure,
            noise = readingDTO.noise,
            energyConsumption = readingDTO.energyConsumption,
            humidity = readingDTO.humidity,
            rotationSpeed = readingDTO.rotationSpeed
        )
        
        val savedReading = sensorReadingRepository.save(reading)
        
        // Check for anomalies and create alerts if needed
        alertService.checkForAnomalies(savedReading)
        
        return savedReading.toDTO()
    }
}