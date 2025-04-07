package com.FCAPS.model;
import java.io.Serializable;
import java.util.List;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;


public class PortData implements Serializable{
	private static final long serialVersionUID = 1L;

	@JacksonXmlProperty(localName = "neid")
	private String neid;
	
	@JacksonXmlProperty(localName = "PortName")
	private String portname;
	
	@JacksonXmlProperty(localName = "Timestamp")
	private String timestamp;
	
	@JacksonXmlProperty(localName = "direction")
	private String direction;
	
	@JacksonXmlProperty(localName = "location")
	private String location;
	
	@JacksonXmlProperty(localName = "Description")
	private String description;
	
	@JacksonXmlProperty(localName = "unit")
	private String unit;
	
	@JacksonXmlElementWrapper(localName= "KPIs")
	@JacksonXmlProperty(localName = "KPI")
	private List<KPI> kpis;
	
	public String getNeid() {return neid;}
	public void setNeid(String neid) {this.neid= neid;}
	
	public String getPortname() {return portname;}
	public void setPortname(String portname) {this.portname= portname;}
	
	public String getTimestamp() {return timestamp;}
	public void setTimestamp(String timestamp) {this.timestamp= timestamp;}
	
	public String getLocation() {return location;}
	public void setLocation(String location) {this.location= location;}
	
	public String getDirection() {return direction;}
	public void setDirection(String direction) {this.direction= direction;}
	
	public String getUnit() {return unit;}
	public void setUnit(String unit) {this.unit= unit;}
	
	public String getDescription() {return description;}
	public void setDescription(String description) {this.description= description;}
	
	public List<KPI> getKpis() {return kpis;}
	public void setKpis(List<KPI> kpis) {this.kpis=kpis;}
}
	
//	public PortData() {}
//	
//	public PortData (String portName, List<KPI>kpiList) {
//		this.portName=portName;
//		this.kpiList=kpiList;
//	}
//	
//	//Getters and Setters
//	public String getPortName() {return portName;}
//	public void setPortName(String portName) {this.portName = portName;}
//	
//	public List<KPI> getKpiList() {return kpiList;}
//	public void setKpiList(List<KPI> kpiList) {this.kpiList = kpiList;}
//
//	
//}