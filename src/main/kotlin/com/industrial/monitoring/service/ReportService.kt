package com.industrial.monitoring.service

import com.industrial.monitoring.dto.EnergyConsumptionReportDTO
import com.industrial.monitoring.dto.FailureReportDTO
import com.industrial.monitoring.dto.UptimeReportDTO
import com.industrial.monitoring.exception.ResourceNotFoundException
import com.industrial.monitoring.model.AlertType
import com.industrial.monitoring.model.MachineStatus
import com.industrial.monitoring.repository.AlertRepository
import com.industrial.monitoring.repository.MachineRepository
import com.industrial.monitoring.repository.SensorReadingRepository
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@Service
class ReportService(
    private val machineRepository: MachineRepository,
    private val sensorReadingRepository: SensorReadingRepository,
    private val alertRepository: AlertRepository
) {

    fun getUptimeReport(startDate: LocalDateTime, endDate: LocalDateTime): List<UptimeReportDTO> {
        val machines = machineRepository.findAll()
        
        return machines.map { machine ->
            // In a real application, we would use historical status changes to calculate exact uptime
            // For simplicity, we'll assume machines are operational by default
            val totalMinutes = ChronoUnit.MINUTES.between(startDate, endDate)
            
            // Count downtime based on alerts (simplified approach)
            val downtimeMinutes = if (machine.status == MachineStatus.OPERATIONAL) {
                0L
            } else {
                // If machine is currently down, consider the time since last status change
                // In a real app, we'd track status change history
                totalMinutes / 10 // Simplified calculation - 10% downtime
            }
            
            val uptimeMinutes = totalMinutes - downtimeMinutes
            val availabilityPercentage = (uptimeMinutes.toDouble() / totalMinutes.toDouble()) * 100
            
            UptimeReportDTO(
                machineId = machine.id,
                machineName = machine.name,
                totalUptime = uptimeMinutes,
                totalDowntime = downtimeMinutes,
                availabilityPercentage = availabilityPercentage,
                lastStatus = machine.status.name,
                lastStatusChangedAt = machine.updatedAt
            )
        }
    }
    
    fun getMachineUptimeReport(machineId: Long, startDate: LocalDateTime, endDate: LocalDateTime): UptimeReportDTO {
        val machine = machineRepository.findById(machineId)
            .orElseThrow { ResourceNotFoundException("Machine not found with id: $machineId") }
            
        // Similar simplified calculation as above
        val totalMinutes = ChronoUnit.MINUTES.between(startDate, endDate)
        val downtimeMinutes = if (machine.status == MachineStatus.OPERATIONAL) {
            0L
        } else {
            totalMinutes / 10 // Simplified calculation
        }
        
        val uptimeMinutes = totalMinutes - downtimeMinutes
        val availabilityPercentage = (uptimeMinutes.toDouble() / totalMinutes.toDouble()) * 100
        
        return UptimeReportDTO(
            machineId = machine.id,
            machineName = machine.name,
            totalUptime = uptimeMinutes,
            totalDowntime = downtimeMinutes,
            availabilityPercentage = availabilityPercentage,
            lastStatus = machine.status.name,
            lastStatusChangedAt = machine.updatedAt
        )
    }
    
    fun getFailureReport(startDate: LocalDateTime, endDate: LocalDateTime): List<FailureReportDTO> {
        val machines = machineRepository.findAll()
        
        return machines.map { machine ->
            // Count alerts that indicate failures
            val alertCount = alertRepository.countAlertsByMachineIdAndTimeRange(machine.id, startDate, endDate)
            
            // In a real app, we'd calculate MTBF from actual failure events
            val mtbf = if (alertCount > 0) {
                ChronoUnit.HOURS.between(startDate, endDate).toDouble() / alertCount
            } else {
                0.0
            }
            
            FailureReportDTO(
                machineId = machine.id,
                machineName = machine.name,
                totalFailures = alertCount,
                meanTimeBetweenFailures = mtbf,
                lastFailureAt = null, // Would get from actual failure records
                criticalFailureCount = alertCount / 3 // Simplified - assume 1/3 of alerts are critical
            )
        }
    }
    
    fun getEnergyConsumptionReport(startDate: LocalDateTime, endDate: LocalDateTime): List<EnergyConsumptionReportDTO> {
        val machines = machineRepository.findAll()
        
        return machines.mapNotNull { machine ->
            // Get energy consumption data if available
            val avgConsumption = sensorReadingRepository.findAverageEnergyConsumptionByMachineIdAndTimeRange(
                machine.id, startDate, endDate
            ) ?: return@mapNotNull null
            
            val maxConsumption = sensorReadingRepository.findMaxEnergyConsumptionByMachineIdAndTimeRange(
                machine.id, startDate, endDate
            ) ?: return@mapNotNull null
            
            val hours = ChronoUnit.HOURS.between(startDate, endDate).toDouble()
            val totalConsumption = avgConsumption * hours
            
            EnergyConsumptionReportDTO(
                machineId = machine.id,
                machineName = machine.name,
                totalConsumption = totalConsumption,
                averageConsumption = avgConsumption,
                peakConsumption = maxConsumption,
                peakConsumptionAt = null // Would get from actual records
            )
        }
    }
}