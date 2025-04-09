package com.example.cm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.UUID;
//
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.objenesis.instantiator.basic.NewInstanceInstantiator;
//import org.springframework.stereotype.Service;
//import org.springframework.web.server.ResponseStatusException;
//
import com.example.cm.dto.LoginDTO;
import com.example.cm.dto.LogoutDTO;
import com.example.cm.dto.OnboardRequestDTO;
import com.example.cm.dto.OnboardResponseDTO;
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
	
	@Value("${sbi.service.url}")
	private String sbiServiceUrl;
	
	@Value("${sbi.service.logout}")
	private String sbiServiceLogout;

	@Override
	public OnboardResponseDTO saveDevice(OnboardRequestDTO request) {
		if(deviceRepository.existsByIpAddr(request.getIpAddr())) {
			return new OnboardResponseDTO("Device already exists","Failed");
		}
		
		Device device =new Device();
		UUID neId=UUID.randomUUID();
		device.setNeId(neId);
		device.setUserName(request.getUserName());
		device.setPassword(request.getPassword());
		device.setIpAddr(request.getIpAddr());
		device.setLoginStatus("Inactive");
		device.setDeviceType(request.getDeviceType());
		deviceRepository.save(device);
		
		return new OnboardResponseDTO("Device is onboarded successfully","Success");
	}
	@Override
	public boolean deviceExists(String ipAddr) {
		System.out.println("Inside device exists");
        return deviceRepository.existsByIpAddr(ipAddr);
    }
	@Override
	public Optional<Device> getDeviceByIp(String ipAddr) {
        return deviceRepository.findByIpAddr(ipAddr);
    }
	@Override
	public String performLogin(String ipAddr) {
		System.out.println("Hi");
        Optional<Device> deviceOpt = getDeviceByIp(ipAddr);
        LoginDTO[] dtoArray=new LoginDTO[1];
        if (deviceOpt.isPresent()) {
            Device device = deviceOpt.get();
            LoginDTO loginDTO = new LoginDTO(
                device.getUserName(),
                device.getPassword(),
                device.getIpAddr(),
                device.getNeId()
            );
            dtoArray[0]=loginDTO;
            System.out.println(dtoArray[0].getNeId()+" Name"+dtoArray[0].getUserName()+" "+dtoArray[0].getIpAddr());
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");
            
            HttpEntity<LoginDTO[]> requestEntity = new HttpEntity<>(dtoArray, headers);
            System.out.println(sbiServiceUrl);
            try {
                ResponseEntity<Boolean> response = restTemplate.exchange(
                    sbiServiceUrl,
                    HttpMethod.POST,
                    requestEntity,
                    Boolean.class
                );
                if(response.getBody()) {
                	device.setLoginStatus("active");
                	deviceRepository.save(device);
                	return "Device is logged in successfully";
                }
                else {
                	return "Device is not logged in";
                }
                //return response.getBody();
            } catch (Exception e) {
            	
                return "Error communicating with SBI service: " + e.getMessage();
            }
        }
        
        return "Device with IpAddress " + ipAddr + " not found";
    }
	
	@Override
	public String performLogout(String ipAddr) {
		System.out.println("Hi");
        Optional<Device> deviceOpt = getDeviceByIp(ipAddr);
        LogoutDTO[] dtoArray=new LogoutDTO[1];
        if (deviceOpt.isPresent()) {
            Device device = deviceOpt.get();
            LogoutDTO logoutDTO = new LogoutDTO(
                device.getIpAddr(),
                device.getNeId()
            );
            dtoArray[0]=logoutDTO;
           // System.out.println(dtoArray[0].getNeId()+" Name"+dtoArray[0].getUserName()+" "+dtoArray[0].getIpAddr());
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");
            
            HttpEntity<LogoutDTO[]> requestEntity = new HttpEntity<>(dtoArray, headers);
            System.out.println(sbiServiceUrl);
            try {
                ResponseEntity<Boolean> response = restTemplate.exchange(
                    sbiServiceLogout,
                    HttpMethod.POST,
                    requestEntity,
                    Boolean.class
                );
                if(response.getBody()) {
                	device.setLoginStatus("Inactive");
                	deviceRepository.save(device);
                	return "Device is logged out successfully";
                }
                else {
                	return "Device is not logged out";
                }
                //return response.getBody();
            } catch (Exception e) {
            	
                return "Error communicating with SBI service: " + e.getMessage();
            }
        }
        
        return "Device with IpAddress " + ipAddr + " not found";
    }
}
