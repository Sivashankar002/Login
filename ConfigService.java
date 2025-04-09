package com.example.cm.service;

import com.example.cm.model.Equipment;
import com.example.cm.model.Port;
import com.example.cm.repository.EquipmentRepository;
import com.example.cm.repository.PortRepository;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.*;

@Service
public class ConfigService {

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private PortRepository portRepository;
    
    @Value("${sbi.xml.url}")
	private String sbiXmlUrl;
    
    public void fetchFromSbi() throws Exception{
    	RestTemplate restTemplate= new RestTemplate();
    	String xml=restTemplate.getForObject(sbiXmlUrl,String.class );
    	parseAndSaveXml(xml);
    }
    public void parseAndSaveXml(String xml) throws IOException { //String xml
        XmlMapper xmlMapper = new XmlMapper();
        XmlComponentsWrapper wrapper = xmlMapper.readValue(xml, XmlComponentsWrapper.class);
        System.out.println(wrapper.componentList.toString());
        for (XmlComponent component : wrapper.componentList) {
            String type = component.state != null ? component.state.type : null;
            if (type != null && type.contains("PORT")) {
                // Save to Port table
                Port port = new Port();
                port.setPortName(component.name);
                port.setPortType(type);
                port.setParent(component.state.parent);
                port.setNeId(UUID.randomUUID());
                port.setCategory("ABC");
                portRepository.save(port);

            }
            else{
                // Save to Equipment table
                Equipment equipment = new Equipment();
                equipment.setEquipId(component.state.id);
                System.out.println(component.state.id);
                equipment.setNeId(UUID.randomUUID());
                equipment.setEquipName(component.name);
                equipment.setEquipType(type);
                
                Map<String, String> attributes = new HashMap<>();
                if (component.state != null) {
                    attributes.put("location", component.state.location);
                    attributes.put("mfg-name", component.state.mfgName);
                    attributes.put("mfg-date", component.state.mfgDate);
                    attributes.put("serial-no", component.state.serialNo);
                    attributes.put("part-no", component.state.partNo);
                    attributes.put("clei-code", component.state.cleiCode);
                }
                System.out.println(component.state.mfgDate);
                System.out.println(attributes.toString());
                equipment.setEquipAttributes(attributes);
                equipmentRepository.save(equipment);
            }
        }
    }
}
