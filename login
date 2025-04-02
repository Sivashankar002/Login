package com.example.cm.repository;

import com.example.cm.dto.DeviceDTO;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DeviceRepository extends CassandraRepository<DeviceDTO, UUID> {
    
    /**
     * Find device by network element ID
     * @param neId The network element ID to search for
     * @return Optional containing DeviceDTO if found
     */
    @Query("SELECT * FROM devices WHERE neid = ?0 ALLOW FILTERING")
    Optional<DeviceDTO> findByNeId(UUID neId);
}


package com.example.cm.service;

import com.example.cm.dto.DeviceDTO;
import com.example.cm.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.UUID;

@Service
public class DeviceService {
    
    private final DeviceRepository deviceRepository;
    private final RestTemplate restTemplate;
    
    @Value("${sbi.interface.url}")
    private String sbiInterfaceUrl;
    
    @Autowired
    public DeviceService(DeviceRepository deviceRepository, RestTemplate restTemplate) {
        this.deviceRepository = deviceRepository;
        this.restTemplate = restTemplate;
    }
    
    /**
     * Check if a device with the given neId exists and retrieve its credentials
     * @param neId The network element ID to check
     * @return Optional containing the DeviceDTO if found
     */
    public Optional<DeviceDTO> getDeviceByNeId(UUID neId) {
        return deviceRepository.findByNeId(neId);
    }
    
    /**
     * Send device credentials to SBI interface microservice
     * @param device The device DTO containing credentials
     * @return Response from the SBI interface service
     */
    public String sendToSbiInterface(DeviceDTO device) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        HttpEntity<DeviceDTO> request = new HttpEntity<>(device, headers);
        
        return restTemplate.postForObject(
                sbiInterfaceUrl + "/api/device-credentials", 
                request, 
                String.class
        );
    }
    
    /**
     * Check for device and send to SBI interface if found
     * @param neId The network element ID to check
     * @return Result message
     */
    public String checkAndSendDeviceCredentials(UUID neId) {
        Optional<DeviceDTO> deviceOpt = getDeviceByNeId(neId);
        
        if (deviceOpt.isPresent()) {
            DeviceDTO device = deviceOpt.get();
            try {
                String response = sendToSbiInterface(device);
                return "Device credentials sent successfully: " + response;
            } catch (Exception e) {
                return "Error sending credentials to SBI interface: " + e.getMessage();
            }
        } else {
            return "Device with neId " + neId + " not found";
        }
    }
}



package com.example.cm.controller;

import com.example.cm.dto.DeviceDTO;
import com.example.cm.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/devices")
public class DeviceController {
    
    private final DeviceService deviceService;
    
    @Autowired
    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }
    
    /**
     * Check if device exists by neId and return its details
     * @param neId The network element ID to check
     * @return ResponseEntity with the device details or not found
     */
    @GetMapping("/{neId}")
    public ResponseEntity<?> getDeviceByNeId(@PathVariable String neId) {
        try {
            UUID uuid = UUID.fromString(neId);
            Optional<DeviceDTO> device = deviceService.getDeviceByNeId(uuid);
            
            if (device.isPresent()) {
                return ResponseEntity.ok(device.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid UUID format");
        }
    }
    
    /**
     * Check if device exists and send to SBI interface if found
     * @param neId The network element ID to check
     * @return ResponseEntity with result message
     */
    @PostMapping("/{neId}/send-to-sbi")
    public ResponseEntity<String> checkAndSendToSbi(@PathVariable String neId) {
        try {
            UUID uuid = UUID.fromString(neId);
            String result = deviceService.checkAndSendDeviceCredentials(uuid);
            
            if (result.contains("not found")) {
                return ResponseEntity.notFound().build();
            } else if (result.contains("Error")) {
                return ResponseEntity.internalServerError().body(result);
            } else {
                return ResponseEntity.ok(result);
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid UUID format");
        }
    }
}



package com.example.cm.dto;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@Table("devices")
public class DeviceDTO {
    
    @Column("username")
    private String userName;
    
    @Column("password")
    private String password;
    
    @Column("ipaddr")
    private String ipAddr;
    
    @PrimaryKey("neid")
    private UUID neId;
    
    // Default constructor required for Cassandra
    public DeviceDTO() {
    }
    
    public DeviceDTO(String userName, String password, String ipAddr, UUID neId) {
        this.userName = userName;
        this.password = password;
        this.ipAddr = ipAddr;
        this.neId = neId;
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

package com.example.cm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableCassandraRepositories(basePackages = "com.example.cm.repository")
public class AppConfig extends AbstractCassandraConfiguration {

    @Override
    protected String getKeyspaceName() {
        return "config_mgmt";
    }

    @Override
    protected String getContactPoints() {
        return "127.0.0.1";
    }

    @Override
    protected int getPort() {
        return 9042;
    }

    @Override
    public SchemaAction getSchemaAction() {
        return SchemaAction.CREATE_IF_NOT_EXISTS;
    }

    @Override
    protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
        CreateKeyspaceSpecification specification = CreateKeyspaceSpecification
                .createKeyspace(getKeyspaceName())
                .ifNotExists()
                .withSimpleReplication(1);
        return Collections.singletonList(specification);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}




# Cassandra Configuration
spring.data.cassandra.keyspace-name=config_mgmt
spring.data.cassandra.contact-points=127.0.0.1
spring.data.cassandra.port=9042
spring.data.cassandra.schema-action=CREATE_IF_NOT_EXISTS
spring.data.cassandra.local-datacenter=datacenter1

# SBI Interface Microservice URL
sbi.interface.url=http://localhost:8081

# Server Configuration
server.port=8080

# Logging Configuration
logging.level.root=INFO
logging.level.com.example.cm=DEBUG
logging.pattern.console=%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n



package com.example.cm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DeviceManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(DeviceManagementApplication.class, args);
    }
}




