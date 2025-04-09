package com.example.cm.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.example.cm.model.Device;
import java.util.List;




@Repository
public interface DeviceRepository extends CassandraRepository<Device,UUID>{
	Optional<Device>findByIpAddr(String ipAddr);
	//Optional<Device> findByNeId(UUID neId);
	
	boolean existsByIpAddr(String ipAddr);
}
