//package com.example.cm.service;
//
//import com.example.cm.model.Configuration;
//import com.example.cm.model.Device;
//import com.example.cm.model.Equipment;
////import com.example.cm.model.LoginDetails;
//import com.example.cm.model.xml.ConfigurationsXML;
//import com.example.cm.model.xml.DeviceXML;
//import com.example.cm.model.xml.EquipmentXML;
//
//import java.io.File;
//import java.io.IOException;
//
//import org.springframework.http.*;
//
//import java.net.URI;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.util.UriComponentsBuilder;
//
//import com.example.cm.repository.ConfigurationRepository;
//import com.example.cm.repository.DeviceRepository;
//import com.example.cm.repository.EquipmentRepository;
//import com.fasterxml.jackson.dataformat.xml.XmlMapper;
//
//import ch.qos.logback.classic.net.server.HardenedLoggingEventInputStream;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Optional;
//import java.util.Map;
//import java.util.UUID;
//
//@Service
//public class ConfigurationService {
////	@Autowired
////	private DeviceRepository deviceRepository;
////	
////	@Autowired
////	private EquipmentRepository equipmentRepository;
//	
//	
//	
////	public void parseAndSaveXml(String filePath) {
////		try {
////			ConfigurationsXML configData=JacksonXMLParser.parseXML(filePath);
////			List<DeviceXML> devices=configData.getDevice();
////			for(DeviceXML deviceXML:devices) {
////				Device device=new Device();
////				device.setNe_id(deviceXML.getNe_Id());
////				device.setIp_addr(deviceXML.getIpAddr());
////				device.setName(deviceXML.getName());
////				device.setPassword(deviceXML.getPassword());
////				device.setPort(deviceXML.getPort());
////				device.setType(deviceXML.getType());
////				device.setUser_name(deviceXML.getUser_name());
////				deviceRepository.save(device);	
////			}
////			
////			List<EquipmentXML> equipments=configData.getEquipment();
////			for(EquipmentXML equipmentXML: equipments) {
////				Equipment equipment=new Equipment();
////				equipment.setEquip_id(equipmentXML.getEquipId());
////				equipment.setEquip_attributes(equipmentXML.getEquipAttributes());
////				equipment.setName(equipmentXML.getName());
////				equipment.setNe_id(equipmentXML.getNe_Id());
////				equipment.setType(equipmentXML.getType());
////				equipmentRepository.save(equipment);
////			}
////		}
////		catch (IOException e) {
////			
////			e.printStackTrace();
////		}
////	}
////	public List<Configuration> getLoginDetails(){
////		return configRepository.findAll();
////	}
////	
////	
////	@Autowired
////	private RestTemplate restTemplate;
////	private final String BASE_URL="http://sbi-server-url/auth";
////	
////	
////	public List<Configuration> getAllPorts(){
////		
////	}
////	
////	
////	public ConfigurationService(RestTemplate restTemplate) {
////		this.restTemplate=restTemplate;
////	}
//////	
////	public String login(String username,String password) {
////		String url=BASE_URL+"/login";
////		
////		Map<String, String>requestBody=new HashMap<>();
////		requestBody.put("username", username);
////		requestBody.put("password", password);
////		
////		HttpHeaders headers=new HttpHeaders();
////		headers.setContentType(MediaType.APPLICATION_JSON);
////		
////		HttpEntity<Map<String, String>>request=new HttpEntity<>(requestBody,headers);
////		
////		ResponseEntity<String> response=restTemplate.postForEntity(url, request, String.class);
////		return response.getBody();
////	}
//////	
////	public String logout(String username) {
////		String url=UriComponentsBuilder.fromHttpUrl(BASE_URL+"/logout").queryParam("username", username).toUriString();
////		HttpHeaders headers=new HttpHeaders();
////		headers.setContentType(MediaType.APPLICATION_JSON);
////		
////		HttpEntity<Void> request=new HttpEntity<>(headers);
////		ResponseEntity<String>response=restTemplate.exchange(url, HttpMethod.POST,request,String.class);
////		return response.getBody();
////	}
////	public List<LogoutDetails> getLogoutDetails(){
////		return configRepository.findAllLogoutDetails();
////	}
////	
////	public void initiateInventoryDiscovery() {
////		String sbiUrl="http://sbi-layer/api/inventory-discovery";
////		ResponseEntity<String> response=restTemplate.postForEntity(sbiUrl,null,String.class);
////		System.out.println("SBI Response:"+response.getBody());
////	}
////	
////	
////	
//	
//	
//	
////	
//	
//	
//	
//}
