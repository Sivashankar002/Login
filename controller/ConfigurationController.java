package com.example.cm.controller;


//import com.example.cm.model.Configuration;
//import com.example.cm.model.LoginDetails;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//import com.example.cm.service.ConfigurationService;

@RestController
@RequestMapping("/configurations")
public class ConfigurationController {
	//@Autowired                                                                                                                                                                                                                                                                                                    
	//private ConfigurationService configService;
	
//	@PostMapping("/uploadXML")
//	public String uploadXML(@RequestParam String filePath){
//		configService.parseAndSaveXml(filePath);
//		return "XML data persisted successfully";
//	}
//	@PostMapping
//	public Configuration createConfig(@RequestBody Configuration config) {
//		return configService.addConfiguration(config);
//	}
//	
//	@GetMapping
//	public List<Configuration>getAllConfigs(){
//		return configService.getAllConfigurations();
//	}
//	
//	@GetMapping("/{component_id}")
//	public Optional<Configuration>getConfigById(@PathVariable UUID component_id){
//		return configService.getConfigurationById(component_id);
//	}
//	@PutMapping("/{component_id}")
//	public ResponseEntity <Configuration> updateConfig(@PathVariable UUID component_id, @RequestBody Configuration config) {
//		Configuration updatedConfig=configService.updateConfiguration(component_id, config);
//		return ResponseEntity.ok(updatedConfig);
//	}
//	
//	@DeleteMapping("/{component_id}")
//	public void deleteConfig(@PathVariable UUID component_id) {
//		configService.deleteConfiguration(component_id);
//	}
//	@PostMapping("/login")
//	public String login(@RequestParam String username,@RequestParam String password) {
//		return configService.login(username, password);
//	}
//	
//	@PostMapping("/logout")
//	public String logout(@RequestParam String username) {
//		return configService.logout(username);
//	}
//	
//	
//	
//	@GetMapping("/login-details")
//	public ResponseEntity<List<Configuration>> getLoginDetails(){
//		return ResponseEntity.ok(configService.getLoginDetails());
//	}
//
//	@GetMapping("/logout-details")
//	public ResponseEntity<List<LogoutDetails>> getLogoutDetails(){
//		return ResponseEntity.ok(configService.getLogoutDetails());
//	}
//	
//	@PostMapping("/inventory-discovery")
//	public ResponseEntity<String> initiateInventoryDiscovery(){
//		configService.initiateInventoryDiscovery();
//		return ResponseEntity.ok("Inventory discovery initiated");
//	}

	
	
	
}
