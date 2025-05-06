package com.industrial.monitoring.dto

import com.industrial.monitoring.model.Machine
import com.industrial.monitoring.model.MachineStatus
import java.time.LocalDateTime

data class MachineDTO(
    val id: Long = 0,
    val name: String,
    val model: String,
    val serialNumber: String,
    val description: String? = null,
    val location: String,
    val installationDate: LocalDateTime,
    val lastMaintenanceDate: LocalDateTime? = null,
    val status: MachineStatus = MachineStatus.OPERATIONAL
)

data class MachineCreateDTO(
    val name: String,
    val model: String,
    val serialNumber: String,
    val description: String? = null,
    val location: String,
    val installationDate: LocalDateTime,
    val lastMaintenanceDate: LocalDateTime? = null,
    val status: MachineStatus = MachineStatus.OPERATIONAL
)

data class MachineUpdateDTO(
    val name: String? = null,
    val model: String? = null,
    val serialNumber: String? = null,
    val description: String? = null,
    val location: String? = null,
    val lastMaintenanceDate: LocalDateTime? = null,
    val status: MachineStatus? = null
)

fun Machine.toDTO(): MachineDTO = MachineDTO(
    id = this.id,
    name = this.name,
    model = this.model,
    serialNumber = this.serialNumber,
    description = this.description,
    location = this.location,
    installationDate = this.installationDate,
    lastMaintenanceDate = this.lastMaintenanceDate,
    status = this.status
)