package com.example.cm.model;

import java.util.Map;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("equipments")
public class Equipment {
	@PrimaryKey
	private String equip_id;
	private Map<String,String> equip_attributes;
	private String name;
	private String ne_id;
	private String type;
	public String getEquip_id() {
		return equip_id;
	}
	public void setEquip_id(String equip_id) {
		this.equip_id = equip_id;
	}
	public Map<String, String> getEquip_attributes() {
		return equip_attributes;
	}
	public void setEquip_attributes(Map<String, String> equip_attributes) {
		this.equip_attributes = equip_attributes;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNe_id() {
		return ne_id;
	}
	public void setNe_id(String ne_id) {
		this.ne_id = ne_id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}
