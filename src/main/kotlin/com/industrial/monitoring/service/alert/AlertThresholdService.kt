package com.industrial.monitoring.service.alert

import com.industrial.monitoring.exception.ResourceNotFoundException
import com.industrial.monitoring.repository.MachineRepository
import org.springframework.stereotype.Service

@Service
class AlertThresholdService(private val machineRepository: MachineRepository) {
    
    data class Thresholds(
        val temperatureHigh: Double,
        val temperatureLow: Double,
        val vibrationHigh: Double,
        val pressureHigh: Double,
        val pressureLow: Double,
        val noiseHigh: Double,
        val energyConsumptionHigh: Double
    )
    
    // In a real application, these would be stored in a database and configurable per machine type
    fun getThresholdsForMachine(machineId: Long): Thresholds {
        // Check if machine exists
        if (!machineRepository.existsById(machineId)) {
            throw ResourceNotFoundException("Machine not found with id: $machineId")
        }
        
        // For now, return default thresholds
        // In a real application, we would fetch specific thresholds for this machine type
        return Thresholds(
            temperatureHigh = 80.0,  // degrees Celsius
            temperatureLow = 10.0,   // degrees Celsius
            vibrationHigh = 10.0,    // mm/s
            pressureHigh = 10.0,     // bar
            pressureLow = 1.0,       // bar
            noiseHigh = 85.0,        // dB
            energyConsumptionHigh = 1000.0 // kWh
        )
    }
}