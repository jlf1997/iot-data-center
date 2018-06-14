package com.cimr.api.code.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.cimr.api.code.model.Commands;

public interface CommandsJpa extends JpaRepository<Commands, Long>,JpaSpecificationExecutor<Commands>{

}
