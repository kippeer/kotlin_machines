package com.industrial.monitoring.controller

import com.industrial.monitoring.dto.MachineCreateDTO
import com.industrial.monitoring.dto.MachineDTO
import com.industrial.monitoring.dto.MachineUpdateDTO
import com.industrial.monitoring.service.MachineService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/machines")
@Tag(name = "Machine Management", description = "APIs for managing industrial machines")
class MachineController(private val machineService: MachineService) {

    @GetMapping
    @Operation(summary = "Get all machines", description = "Retrieves a list of all machines in the system")
    fun getAllMachines(): ResponseEntity<List<MachineDTO>> {
        return ResponseEntity.ok(machineService.getAllMachines())
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get machine by ID", description = "Retrieves detailed information for a specific machine")
    fun getMachineById(@PathVariable id: Long): ResponseEntity<MachineDTO> {
        return ResponseEntity.ok(machineService.getMachineById(id))
    }
    
    @PostMapping
    @Operation(summary = "Create new machine", description = "Registers a new machine in the system")
    fun createMachine(@Valid @RequestBody machineCreateDTO: MachineCreateDTO): ResponseEntity<MachineDTO> {
        val createdMachine = machineService.createMachine(machineCreateDTO)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMachine)
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update machine", description = "Updates an existing machine's information")
    fun updateMachine(
        @PathVariable id: Long,
        @Valid @RequestBody machineUpdateDTO: MachineUpdateDTO
    ): ResponseEntity<MachineDTO> {
        return ResponseEntity.ok(machineService.updateMachine(id, machineUpdateDTO))
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete machine", description = "Removes a machine from the system")
    fun deleteMachine(@PathVariable id: Long): ResponseEntity<Void> {
        machineService.deleteMachine(id)
        return ResponseEntity.noContent().build()
    }
    
    @GetMapping("/search")
    @Operation(summary = "Search machines", description = "Searches for machines by name, model, or serial number")
    fun searchMachines(@RequestParam keyword: String): ResponseEntity<List<MachineDTO>> {
        return ResponseEntity.ok(machineService.searchMachines(keyword))
    }
}