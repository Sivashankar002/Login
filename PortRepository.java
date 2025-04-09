package com.example.cm.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.example.cm.model.Port;

@Repository
public interface PortRepository extends CassandraRepository<Port,String>{

}
