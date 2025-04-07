package com.FCAPS.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.FCAPS.model.PMData;
import com.FCAPS.service.ConfigService;
import com.FCAPS.service.PMDataService;
import com.FCAPS.service.SbiService;
import com.FCAPS.util.XMLParser;

@RestController
@RequestMapping("/api/pmdata")
public class PMDataController {

	private final PMDataService pmDataService;
	private final XMLParser xmlParser;
	private final ConfigService configService;
	private final SbiService sbiService;

	public PMDataController(PMDataService pmDataService, XMLParser xmlParser, ConfigService configService,SbiService sbiService) {
		this.pmDataService=pmDataService;
		this.xmlParser=xmlParser;
		this.configService=configService;
		this.sbiService=sbiService;
	}
	
	@GetMapping("/all")
	public List<PMData>getAllData(){
		return pmDataService.getAllData();
	}
	
	@GetMapping("/get-neid")
	public String getNeid(){
		return configService.getNeid();
	}
	
	@GetMapping("/testing")
	public String hello() {
		return "1001";
	}
	
	@PostMapping("/fetch-and-parse")
	public ResponseEntity <String> fetchAndParseXML(@RequestBody Map<String,String> requestBody) {
		String myDirectory = requestBody.get("mydirectory");
		
		if(myDirectory == null) {
			return ResponseEntity.badRequest().body("Error:mydirectory is missing in the request.");
		}
		//Get neid from configuration module
		String neid = configService.getNeid();
		if(neid == null) {
			return ResponseEntity.badRequest().body("Error:Failed to fetch neid from configuration module.");
		}
		System.out.println("Sending request to SBI with neid: "+neid+ " and directory: "+ myDirectory);
		
		String xmlFilePath = sbiService.fetchPerformanceFile(neid, myDirectory);
//		String xmlFilePath ="/home/test/Documents/neid_1001/PMDATA.xml";
		if(xmlFilePath == null || xmlFilePath.isEmpty()) {
			System.err.println("SBI did not return a valid file path.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: SBI failed to return a valid file path.");
		}
		System.out.println("Received XML file path from SBI:" +xmlFilePath);
		//Parse XML
		boolean parseSuccess = xmlParser.parseXmlFile(xmlFilePath, neid);
		if(!parseSuccess) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error:Failed to parse the XML file.");
		}
		return ResponseEntity.ok("XML file parsed and stored on DB succesfully!");
	}
	
}
//	@PostMapping("/parse-directory")
//	public ResponseEntity<String> parseDirectory(@RequestParam("directoryPath") String directoryPath){
//		try {
//			pmDataService.processDirectory(directoryPath);
//			return ResponseEntity.ok("Directory processed sucess");
//		}
//		catch(IOException e) {
//			return ResponseEntity.badRequest().body("error in parsing directory"+e.getMessage());
//		}
//	}
//	
//	@GetMapping("/range")
//	public ResponseEntity<PMDataListWrapper> getPerformanceFromToTimestamp
//	(@RequestParam("from") String fromTimestamp, @RequestParam("to") String toTimestamp) {
//		PMDataListWrapper dataWrapper = pmDataService.getPerformanceDataBetweenTimestamp(fromTimestamp, toTimestamp);
//		return ResponseEntity.ok(dataWrapper);
//	}
//@PostMapping("/upload")
//public String uploadXML(@RequestParam String filePath){
//	try {
//		String neid = configService.getNeid();
//		if(neid==null) {
//			return "Error: Failed to fetch neid";
//		}
//		List<PMData> pmdataList = xmlParser.parseXmlFile(filePath,neid);
//		
//		pmDataService.storeData(pmdataList);
//		return "XML file processed sucess";
//	}
//	catch(Exception e) {
//		return "error in parsing xml"+e.getMessage();
//	}
//}
//	




