package com.project.performance.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.performance.model.ContentInfo;

public interface ContentRepository extends JpaRepository<ContentInfo, Long>{
	public ContentInfo findByNameAndLocationAndPeriodAndImgAndGenreAndCategory(String name, String location, String period, String img, String genre, String category);
}
