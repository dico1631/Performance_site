package com.project.performance.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data

@AllArgsConstructor
@NoArgsConstructor

public class ContentInfo {
	
	@Id
	@Column(name = "contentid")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long contentid;
	
	
	@Column(name = "name", length = 20, nullable = false)
	private String name;
	
	@Column(columnDefinition = "location", nullable = true)
	private String location;
	
	@Column(name = "period", nullable = true)
	private String period;
	
	@Column(name = "img", nullable = false)
	private String img;
	
	@Column(name = "genre", nullable = true)
	private String genre;
	
	@Column(name = "category", nullable = false)
	private String category;
}
