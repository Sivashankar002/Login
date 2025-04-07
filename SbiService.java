package com.FCAPS.service;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SbiService {
	private final RestTemplate restTemplate = new RestTemplate();
	@Value("${api.sbi.url}")
	private String sbiApiUrl;
	public String fetchPerformanceFile(String neid, String myDirectory) {
		try {
			Map<String, String> requestBody = new HashMap<>();
			requestBody.put("neId", neid);
			requestBody.put("outputPath",myDirectory);
			//Set Headers
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			//Create HTTP request
			HttpEntity<Map<String, String>> request = new HttpEntity<>(requestBody, headers);
			//Send post request to sbi and receive Json response
			ResponseEntity<String> response = restTemplate.exchange(
					sbiApiUrl,
					HttpMethod.POST,
					request, String.class
			);
			//Debugging Log
			System.out.println("Sent request to SBI:"+ requestBody);
			System.out.println("SBI Response: "+ response.getBody());
			
			//Validate response
			if(response.getStatusCode()== HttpStatus.OK && response.getBody()!= null){
				String filePath= response.getBody().trim();
				if(filePath.equalsIgnoreCase("Output path is not set")) {
					System.err.println("SBI returned an error:"+ filePath);
					return null;
				}
				return filePath;
			}
			else {System.err.println("Error: SBI response invalid:"+ response.getBody());
				return null;
			}
		}
		catch(Exception e){
			System.err.println("Error communicating with SBI"+e.getMessage());
			return null;
		}
	}
}


