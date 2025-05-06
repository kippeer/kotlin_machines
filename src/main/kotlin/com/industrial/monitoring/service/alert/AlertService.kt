package com.industrial.monitoring.service.alert

import com.industrial.monitoring.dto.AlertDTO
import com.industrial.monitoring.dto.toDTO
import com.industrial.monitoring.exception.ResourceNotFoundException
import com.industrial.monitoring.model.Alert
import com.industrial.monitoring.model.AlertType
import com.industrial.monitoring.model.SensorReading
import com.industrial.monitoring.repository.AlertRepository
import com.industrial.monitoring.repository.MachineRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class AlertService(
    private val alertRepository: AlertRepository,
    private val machineRepository: MachineRepository,
    private val alertThresholdService: AlertThresholdService
) {

    fun getAlertsByMachineId(machineId: Long): List<AlertDTO> {
        if (!machineRepository.existsById(machineId)) {
            throw ResourceNotFoundException("Machine not found with id: $machineId")
        }
        return alertRepository.findByMachineId(machineId).map { it.toDTO() }
    }
    
    fun getUnresolvedAlertsByMachineId(machineId: Long): List<AlertDTO> {
        if (!machineRepository.existsById(machineId)) {
            throw ResourceNotFoundException("Machine not found with id: $machineId")
        }
        
        val now = LocalDateTime.now()
        val oneYearAgo = now.minusYears(1)
        
        return alertRepository.findByMachineIdAndResolvedAndTimestampBetween(
            machineId, false, oneYearAgo, now
        ).map { it.toDTO() }
    }

    @Transactional
    fun resolveAlert(alertId: Long): AlertDTO {
        val alert = alertRepository.findById(alertId)
            .orElseThrow { ResourceNotFoundException("Alert not found with id: $alertId") }
        
        alert.resolved = true
        alert.resolvedAt = LocalDateTime.now()
        
        return alertRepository.save(alert).toDTO()
    }
    
    @Transactional
    fun checkForAnomalies(reading: SensorReading) {
        val thresholds = alertThresholdService.getThresholdsForMachine(reading.machine.id)
        
        // Check temperature
        if (reading.temperature > thresholds.temperatureHigh) {
            createAlert(
                reading,
                AlertType.TEMPERATURE_HIGH,
                "temperature",
                reading.temperature,
                thresholds.temperatureHigh,
                "Temperature exceeds maximum threshold"
            )
        } else if (reading.temperature < thresholds.temperatureLow) {
            createAlert(
                reading,
                AlertType.TEMPERATURE_LOW,
                "temperature",
                reading.temperature,
                thresholds.temperatureLow,
                "Temperature below minimum threshold"
            )
        }
        
        // Check vibration
        if (reading.vibration > thresholds.vibrationHigh) {
            createAlert(
                reading,
                AlertType.VIBRATION_HIGH,
                "vibration",
                reading.vibration,
                thresholds.vibrationHigh,
                "Vibration exceeds maximum threshold"
            )
        }
        
        // Check pressure
        if (reading.pressure > thresholds.pressureHigh) {
            createAlert(
                reading,
                AlertType.PRESSURE_HIGH,
                "pressure",
                reading.pressure,
                thresholds.pressureHigh,
                "Pressure exceeds maximum threshold"
            )
        } else if (reading.pressure < thresholds.pressureLow) {
            createAlert(
                reading,
                AlertType.PRESSURE_LOW,
                "pressure",
                reading.pressure,
                thresholds.pressureLow,
                "Pressure below minimum threshold"
            )
        }
        
        // Check noise
        if (reading.noise > thresholds.noiseHigh) {
            createAlert(
                reading,
                AlertType.NOISE_HIGH,
                "noise",
                reading.noise,
                thresholds.noiseHigh,
                "Noise level exceeds maximum threshold"
            )
        }
        
        // Check energy consumption if available
        reading.energyConsumption?.let { energy ->
            if (energy > thresholds.energyConsumptionHigh) {
                createAlert(
                    reading,
                    AlertType.ENERGY_CONSUMPTION_HIGH,
                    "energyConsumption",
                    energy,
                    thresholds.energyConsumptionHigh,
                    "Energy consumption exceeds maximum threshold"
                )
            }
        }
    }
    
    private fun createAlert(
        reading: SensorReading,
        type: AlertType,
        parameter: String,
        value: Double,
        threshold: Double,
        message: String
    ) {
        val alert = Alert(
            machine = reading.machine,
            type = type,
            parameter = parameter,
            value = value,
            threshold = threshold,
            message = message
        )
        
        alertRepository.save(alert)
    }
}