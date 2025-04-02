package com.example.cm.model;


import java.util.Date;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

//import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

//@Table("equipment")
//@JacksonXmlRootElement(localName = "equipment")
public class Configuration {
	
	@PrimaryKey
	private UUID component_id;
	private int device_id;
	private String component_name;
	private UUID parent;
	private Map<String,String> equipment_attributes;
//	private String username;
//	private String password;
//	private String status;
	
	
	public Configuration() {}
	
	

	public Configuration(UUID component_id, int device_id, String component_name, UUID parent,
			Map<String, String> equipment_attributes,String username,String password,String status) {
		super();
		this.component_id = component_id;
		this.device_id = device_id;
		this.component_name = component_name;
		this.parent = parent;
		this.equipment_attributes = equipment_attributes;
//		this.username=username;
//		this.password=password;
//		this.status=status;
	}



//	public String getUsername() {
//		return username;
//	}
//
//
//
//	public void setUsername(String username) {
//		this.username = username;
//	}
//
//
//
//	public String getPassword() {
//		return password;
//	}
//
//
//
//	public void setPassword(String password) {
//		this.password = password;
//	}
//
//
//
//	public String getStatus() {
//		return status;
//	}
//
//
//
//	public void setStatus(String status) {
//		this.status = status;
//	}



	public UUID getComponent_id() {
		return component_id;
	}

	public void setComponent_id(UUID component_id) {
		this.component_id = component_id;
	}

	public int getDevice_id() {
		return device_id;
	}

	public void setDevice_id(int device_id) {
		this.device_id = device_id;
	}

	public String getComponent_name() {
		return component_name;
	}

	public void setComponent_name(String component_name) {
		this.component_name = component_name;
	}

	public UUID getParent() {
		return parent;
	}

	public void setParent(UUID parent) {
		this.parent = parent;
	}

	public Map<String, String> getEquipment_attributes() {
		return equipment_attributes;
	}

	public void setEquipment_attributes(Map<String, String> equipment_attributes) {
		this.equipment_attributes = equipment_attributes;
	}
	
	
	
	
}
