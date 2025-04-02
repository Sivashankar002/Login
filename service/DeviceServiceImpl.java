package com.example.cm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.Optional;
import java.util.UUID;
//
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.objenesis.instantiator.basic.NewInstanceInstantiator;
//import org.springframework.stereotype.Service;
//import org.springframework.web.server.ResponseStatusException;
//
import com.example.cm.dto.DeviceDTO;
import com.example.cm.model.Device;
import com.example.cm.repository.DeviceRepository;

@Service
public class DeviceServiceImpl implements DeviceService{
	@Autowired
	private DeviceRepository deviceRepository;
	
	public DeviceServiceImpl(DeviceRepository deviceRepository) {
		this.deviceRepository=deviceRepository;
	}
	@Autowired
	private RestTemplate restTemplate;
	
	private static  String SBI_URL="http://localhost:8080/api/sbi/login";

	@Override
	public Device saveDevice(Device device) {
		return deviceRepository.save(device);
	}


	@Override
	public String getDeviceDetailsByNeId(UUID neId){
		
		Optional<Device> deviceOptional=deviceRepository.findById(neId);
		System.out.println("After findbyneid");
		if(deviceOptional.isPresent()) {
			Device device=deviceOptional.get();
			DeviceDTO deviceDTO=new DeviceDTO(device.getUserName(), device.getPassword(), device.getIpAddr(), device.getNeId());
			HttpHeaders headers=new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<DeviceDTO> request=new HttpEntity<>(deviceDTO,headers);
			String response=restTemplate.postForObject(SBI_URL,deviceDTO,String.class);
			return "Response from SBI"+response;
		}
		else {
			return "Device with neId"+neId+"is not found";
		}
	}
}
