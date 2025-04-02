package com.example.cm.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.example.cm.model.Device;



@Repository
public interface DeviceRepository extends CassandraRepository<Device,UUID>{
	Optional<Device> findById(UUID neId);
}
