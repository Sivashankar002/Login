package com.FCAPS.util;
import java.io.File;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

import com.FCAPS.model.KPI;
import com.FCAPS.model.PMData;
import com.FCAPS.model.PMDataKey;
import com.FCAPS.model.PortData;
import com.FCAPS.repository.PMDataRepository;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

@Component
public class XMLParser {
	
	private final PMDataRepository pmDataRepository;
	public XMLParser(PMDataRepository pmDataRepository) {
		this.pmDataRepository = pmDataRepository;
	}

//public List<PMData> parseXmlFile (String filePath) throws Exception{
//	XmlMapper xmlMapper = new XmlMapper();
//	KPIDataWrapper dataWrapper = xmlMapper.readValue(new File(filePath), KPIDataWrapper.class);
//	
//	if(dataWrapper.getKpiEntries() == null) {
//		throw new RuntimeException("Error: No kpi entries found in xml file");
//	}
//	
//	return dataWrapper.getKpiEntries().stream().map(entry ->{
//		PMDataKey key = new PMDataKey();
//		key.setNeid(Integer.parseInt(entry.getNeid()));
//		key.setTimestamp(entry.getTimestamp());
//		key.setPortname(entry.getPortname());
//	
//	PMData pmData = new PMData();
//		
//	pmData.setKey(key);
//	pmData.setDirection(entry.getDirection());
//	pmData.setLocation(entry.getLocation());
//	pmData.setDescription(entry.getDescription());
//	pmData.setUnit(entry.getUnit());
//	
//	Map<String,String> kpiMap = new HashMap<>();
//	for(KPI kpi: entry.getKpis()) {
//		kpiMap.put(kpi.getMonType(), kpi.getMonValue());
//	}
//	pmData.setKpis(kpiMap);
//	
//	return pmData;
//	}).collect(Collectors.toList());
	
	public boolean parseXmlFile(String filePath, String neid) {
		try {
			File xmlfile = new File(filePath);
			XmlMapper xmlMapper = new XmlMapper();
		
		KPIDataWrapper datawrapper = xmlMapper.readValue(xmlfile, KPIDataWrapper.class);
		List<PortData> portDataList = datawrapper.getKpiEntries();
//		
//		for(PortData pmData: pmDataList) {
//			pmData.setNeid(neid);
//		}
		List<PMData> pmDataList = portDataList.stream().map(portData -> {
			PMData pmData = new PMData();
			
			int neidInt=Integer.parseInt(neid);
			
			PMDataKey pmDataKey = new PMDataKey();
			pmDataKey.setNeid(neidInt);
			pmDataKey.setTimestamp(portData.getTimestamp());
			pmDataKey.setPortname(portData.getPortname());
			
			pmData.setKey(pmDataKey);
			
			pmData.setDirection(portData.getDirection());
			pmData.setLocation(portData.getLocation());
			pmData.setDescription(portData.getDescription());
			pmData.setUnit(portData.getUnit());
			Map<String, String> kpiMap = portData.getKpis().stream().collect(Collectors.toMap(KPI::getMonType,KPI::getMonValue, (existing,replacement)-> existing));
			pmData.setKpis(kpiMap);
			return pmData;
		}).collect(Collectors.toList());
		pmDataRepository.saveAll(pmDataList);
		return true;
}
	catch (Exception e) {
		System.err.println("Error parsing XML file"+e.getMessage());
		return false;
	}
}
}

//private final XmlMapper xmlMapper = new XmlMapper();
//private final Pattern neIdPattern = Pattern.compile("neid_(\\d+)");
//
//public PMData parseXmlFile(String filePath) throws IOException {
//	File file = new File(filePath);
//	
//	String neId = extractNeIdFromPath(file.getAbsolutePath());
//	
//	PortData portData= xmlMapper.readValue(file, PortData.class);
//	
//	return convertToPMData(portData,neId);
//}
//
//private String extractNeIdFromPath(String path) {
//	Matcher matcher = neIdPattern.matcher(path);
//	if(matcher.find()) {
//		return matcher.group(1);
//	}
//	return "unknown";
//}
//
//private PMData convertToPMData(PortData portData, String neid) {
//	Instant timestamp;
//	try {
//		timestamp = Instant.parse(portData.getTimestamp());
//	}
//	catch(DateTimeParseException e) {
//		timestamp = Instant.now();
//	}
	
//	PMDataKey key = new PMDataKey();
//	key.setTimestamp(timestamp);
//	key.setNeid(neid);
//	key.setPortName(portData.getPortName());
//	
//	PMData pmData = new PMData();
//	pmData.setKey(key);
//	pmData.setPortName(portData.getPortName());
//	pmData.setDirection(portData.getDirection());
//	pmData.setLocation(portData.getLocation());
//	pmData.setDescription(portData.getDescription());
//	pmData.setUnit(portData.getUnit());
//	
//	if(portData.getKpis()!= null) {
//		pmData.setKpis(new HashSet<> (portData.getKpis()));
//	}
//	return pmData;
//	
//}