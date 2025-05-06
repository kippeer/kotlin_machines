package com.industrial.monitoring.repository

import com.industrial.monitoring.model.Machine
import com.industrial.monitoring.model.MachineStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface MachineRepository : JpaRepository<Machine, Long> {
    fun findByStatus(status: MachineStatus): List<Machine>
    
    fun findByLastMaintenanceDateBefore(date: LocalDateTime): List<Machine>
    
    @Query("SELECT m FROM Machine m WHERE m.name LIKE %:keyword% OR m.model LIKE %:keyword% OR m.serialNumber LIKE %:keyword%")
    fun searchMachines(keyword: String): List<Machine>
}