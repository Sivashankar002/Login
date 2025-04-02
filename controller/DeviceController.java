package com.example.cm.controller;

//import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.server.ResponseStatusException;

//import com.example.cm.client.SBIClient;
//
//import com.example.cm.dto.DeviceDTO;
import com.example.cm.model.Device;
import com.example.cm.service.DeviceService;

//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/devices")
public class DeviceController {
	
	private DeviceService deviceService;
	
	@Autowired
	public DeviceController(DeviceService deviceService) {
		this.deviceService = deviceService;
	}
	

	@PostMapping("/add")
	public Device addDevice(@RequestBody Device device) {
		return deviceService.saveDevice(device);
	}
	
//	@GetMapping("/all")
//	public String g()
//	{
//		return "hlo";
//	}
	
	@PostMapping("/login")
	public String getDeviceDetails(@RequestBody String neId){
		UUID networkId=UUID.fromString(neId.replace("\"","").trim());
		return deviceService.getDeviceDetailsByNeId(networkId);

	}
	
}
