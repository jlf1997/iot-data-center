package com.cimr.api.code.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.cimr.api.code.model.Dict;

public interface DictJpa extends JpaRepository<Dict, Long>,JpaSpecificationExecutor<Dict>{

}
