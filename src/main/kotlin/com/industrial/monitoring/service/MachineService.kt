package com.industrial.monitoring.service

import com.industrial.monitoring.dto.MachineCreateDTO
import com.industrial.monitoring.dto.MachineDTO
import com.industrial.monitoring.dto.MachineUpdateDTO
import com.industrial.monitoring.dto.toDTO
import com.industrial.monitoring.exception.ResourceNotFoundException
import com.industrial.monitoring.model.Machine
import com.industrial.monitoring.repository.MachineRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class MachineService(private val machineRepository: MachineRepository) {

    fun getAllMachines(): List<MachineDTO> {
        return machineRepository.findAll().map { it.toDTO() }
    }

    fun getMachineById(id: Long): MachineDTO {
        return machineRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Machine not found with id: $id") }
            .toDTO()
    }

    @Transactional
    fun createMachine(machineCreateDTO: MachineCreateDTO): MachineDTO {
        val machine = Machine(
            name = machineCreateDTO.name,
            model = machineCreateDTO.model,
            serialNumber = machineCreateDTO.serialNumber,
            description = machineCreateDTO.description,
            location = machineCreateDTO.location,
            installationDate = machineCreateDTO.installationDate,
            lastMaintenanceDate = machineCreateDTO.lastMaintenanceDate,
            status = machineCreateDTO.status
        )
        
        return machineRepository.save(machine).toDTO()
    }

    @Transactional
    fun updateMachine(id: Long, machineUpdateDTO: MachineUpdateDTO): MachineDTO {
        val machine = machineRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Machine not found with id: $id") }
        
        machineUpdateDTO.name?.let { machine.name = it }
        machineUpdateDTO.model?.let { machine.model = it }
        machineUpdateDTO.serialNumber?.let { machine.serialNumber = it }
        machineUpdateDTO.description?.let { machine.description = it }
        machineUpdateDTO.location?.let { machine.location = it }
        machineUpdateDTO.lastMaintenanceDate?.let { machine.lastMaintenanceDate = it }
        machineUpdateDTO.status?.let { machine.status = it }
        
        machine.updatedAt = LocalDateTime.now()
        
        return machineRepository.save(machine).toDTO()
    }

    @Transactional
    fun deleteMachine(id: Long) {
        if (!machineRepository.existsById(id)) {
            throw ResourceNotFoundException("Machine not found with id: $id")
        }
        machineRepository.deleteById(id)
    }
    
    fun searchMachines(keyword: String): List<MachineDTO> {
        return machineRepository.searchMachines(keyword).map { it.toDTO() }
    }
}