package com.FCAPS.util;

import java.util.List;

import com.FCAPS.model.PMData;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PMDataListWrapper {
	
 private List <PMData> pmDataList;
 
 public PMDataListWrapper(List<PMData> pmDataList) {
	 this.pmDataList=pmDataList;
 }
 public List<PMData> getPmDataList(){
	 return pmDataList;
 }  
 
 public void setPmDataList(List<PMData> pmDataList) {
	 this.pmDataList = pmDataList;
 }
//	@JsonProperty("PMData")
//	private List <PMData> pmData;
//	
//	public List<PMData> getPmData(){
//		return pmData;
//	}
//	public void setPmData(List<PMData> pmData) {
//		this.pmData=pmData;
//	}

}
