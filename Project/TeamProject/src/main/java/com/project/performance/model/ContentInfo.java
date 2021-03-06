package com.project.performance.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity(name = "content_info")
@Data
public class ContentInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String title;
	private String location;
	private String period;
	private String thumb;
	private String category;
	private String genre;
}
