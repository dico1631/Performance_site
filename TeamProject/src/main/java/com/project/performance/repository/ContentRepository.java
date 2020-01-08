package com.project.performance.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.project.performance.model.ContentInfo;

public interface ContentRepository extends JpaRepository<ContentInfo, Long>{
//title, category로 데이터 존재하는지 확인 
	
	@Query("select count(*) from content_info con where con.title=:title and con.category=:category")
	int existcount(@Param("title") String title,@Param("category") String category);
	
//신규등록 
	public ContentInfo save(ContentInfo contentInfo);
	
//컨텐츠 중복 시(title, category) genre 추가하여 업데이트 
	
	@Transactional
	@Modifying
	@Query("update content_info set period=:period,thumb=:thumb,genre=concat(genre, ',', :genre) where title=:title and category=:category ")
	void updatecontent(@Param("period") String period,@Param("thumb") String thumb,@Param("genre") String genre,@Param("title") String title,@Param("category") String category);
	
	
// 장르, 카테고리로 조회하여 리턴한다 
//	@Transactional
//	@Modifying
//	@Query("select id,title,location,period,thumb,category,genre from content_info con where con.genre like '%:genre%' and con.category=:category ")
//	List<ContentInfo> selectcontent(@Param("genre") String genre,@Param("category") String category);

	List<ContentInfo> findAllByGenreContainingAndCategory(String genre, String category);
}
