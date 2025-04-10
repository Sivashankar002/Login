package com.example.cm.service;

import com.example.cm.model.Device;
import com.example.cm.model.Equipment;
import com.example.cm.model.Port;
import com.example.cm.repository.DeviceRepository;
import com.example.cm.repository.EquipmentRepository;
import com.example.cm.repository.PortRepository;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import ch.qos.logback.core.pattern.parser.Parser;
import jnr.ffi.Struct.int16_t;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.style.ToStringCreator;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.annotation.ElementType;
import java.lang.reflect.Constructor;
import java.util.*;
import java.util.prefs.NodeChangeEvent;

import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

@Service
public class ConfigService {

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private PortRepository portRepository;
    
//    @Autowired
//    private DeviceRepository deviceRepository;
    
    
    @Value("${sbi.xml.url}")
	private String sbiXmlUrl;
    
    public void fetchFromSbi() throws Exception{
    	Device device = new Device();
    	
    	UUID neId  = device.getNeId();
    	
    	RestTemplate restTemplate= new RestTemplate();
    	String xml=restTemplate.getForObject(sbiXmlUrl,String.class );
    	String xmlData = removeXMLHeaders(xml);
    	parseAndSaveXml(xmlData,neId);
    }
    
    private static String removeXMLHeaders(String xml) throws ParserConfigurationException, SAXException, IOException {
    	ByteArrayInputStream is = new ByteArrayInputStream(xml.getBytes());

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document d = db.parse(is);

        Node rootElement = d.getDocumentElement();
        Element ele = (Element)rootElement;
    	NodeList nl = ele.getElementsByTagName("components");
    	Node dn = nl.item(0);
    	String xmlData = nodeToString(dn);
    	return xmlData;
    }
    
    
    private static String nodeToString(Node node) {
        StringWriter sw = new StringWriter();
        try {
          Transformer t = TransformerFactory.newInstance().newTransformer();
          t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
          t.setOutputProperty(OutputKeys.INDENT, "yes");
          t.transform(new DOMSource(node), new StreamResult(sw));
        } catch (TransformerException te) {
          System.out.println("nodeToString Transformer Exception");
        }
        return sw.toString();
      }
    
    
    public void parseAndSaveXml(String xml,UUID neId) throws IOException { //String xml
        XmlMapper xmlMapper = new XmlMapper();
        XmlComponentsWrapper wrapper = xmlMapper.readValue(xml, XmlComponentsWrapper.class);
        for (XmlComponent component : wrapper.componentList) {
            String type = component.state != null ? component.state.type : null;
            if (type != null && type.contains("PORT")) {
                // Save to Port table
                Port port = new Port();
                port.setPortName(component.name);
                port.setPortType(type);
                port.setParent(component.state.parent);
                port.setNeId(neId);
                port.setCategory("Line");
                portRepository.save(port);

            }
            else{
                // Save to Equipment table
                Equipment equipment = new Equipment();
                equipment.setEquipId(component.state.id);
                equipment.setNeId(neId);
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
                equipment.setEquipAttributes(attributes);
                equipmentRepository.save(equipment);
            }
        }
    }
}
