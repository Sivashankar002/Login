package com.example.cm.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.example.cm.model.Equipment;

@Repository
public interface EquipmentRepository extends CassandraRepository<Equipment, String>{

}
