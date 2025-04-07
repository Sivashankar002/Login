package com.FCAPS.model;

import java.io.Serializable;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;


public class KPI implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@JacksonXmlProperty(localName ="monname")
	private String monType;
	
	@JacksonXmlProperty(localName = "value")
	private String monVal;
	
//	public KPI() {}
//	
//	public KPI(String monType, float monVal) {
//		this.monType=monType;
//		this.monVal=monVal;
//	}
	//Getter and Setter
	 public String getMonType() {return monType;}
	 public void setMonType(String monType) {this.monType = monType;}
	 
	 public String getMonValue() {return monVal;}
	 public void setMonVal(String monVal) {this.monVal = monVal;}
}
