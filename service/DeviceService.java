package com.example.cm.service;



//import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

//import com.example.cm.dto.DeviceDTO;
import com.example.cm.model.Device;


@Service
public interface DeviceService {
	
	Device saveDevice(Device device);
	String getDeviceDetailsByNeId(UUID neId);
}
