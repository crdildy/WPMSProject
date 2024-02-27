package com.example.wpms.repository

import com.example.wpms.database.PatientDatabase
import com.example.wpms.model.Device
class DeviceRepository(private val db: PatientDatabase){

    suspend fun insertDevice(device: Device) = db.getDeviceDao().insertDevice(device)
    suspend fun deleteDevice(device: Device) = db.getDeviceDao().deleteDevice(device)
    suspend fun updateDevice(device: Device) = db.getDeviceDao().updateDevice(device)

    fun getAllDevices() = db.getDeviceDao().getAllDevices()
    fun searchDevices(query: Int?) = db.getDeviceDao().searchDevice(query)
}