package com.project.performance.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.Data;

@Entity(name = "scrap_db")
@Data
public class Scrap {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private long contentid;
	private String email;
	private String title;
	private String location;
	private String period;
	private String thumb;
	private String category;
	private String genre;
	
	@Transient
	private String interparkTit;
}
