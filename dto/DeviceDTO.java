package com.example.cm.dto;

import java.util.UUID;

public class DeviceDTO {
	private String userName;
	private String password;
	private String ipAddr;
	private UUID neId;
	public DeviceDTO(String userName, String password, String ipAddr,UUID neId) {
		this.userName = userName;
		this.password = password;
		this.ipAddr = ipAddr;
		this.neId=neId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getIpAddr() {
		return ipAddr;
	}
	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}
	public UUID getNeId() {
		return neId;
	}
	public void setNeId(UUID neId) {
		this.neId = neId;
	}
	
	
	
	
	
	
}
