package com.FCAPS.util;

import java.util.List;

import com.FCAPS.model.PortData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class KPIDataWrapper {
	
	@JacksonXmlElementWrapper(useWrapping=false)
	@JacksonXmlProperty(localName ="KPIEntry")
	private List<PortData> kpiEntries;
	
	 public List<PortData> getKpiEntries(){
		 return kpiEntries;
	 }  
	 
	 public void setKpiEntries(List<PortData> kpiEntries) {
		 this.kpiEntries = kpiEntries;
	 }
	
	

}
