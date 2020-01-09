package com.project.performance.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.project.performance.model.Scrap;

public interface ScrapRepository extends JpaRepository<Scrap, Long>{
	
	public Scrap save(Scrap scrap);
	List<Scrap> findAllByEmail(String email);
}