package com.project.performance.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.project.performance.model.ContentInfo;
import com.project.performance.repository.ContentRepository;

@Controller
public class ContentInfoController {
	@Autowired 
	ContentRepository contentRepository;
	
	@GetMapping("/crawling")
	public void Crawling() throws IOException {
		//genre 삽입, 전체 : genre만 삭제 
	      List <String> genre = new ArrayList<String>();
	      genre.add(""); // 전체
	      genre.add("로맨스");
	      genre.add("코미디");
	      genre.add("공포");
	      genre.add("드라마");
	      genre.add("판타지");
	      genre.add("시대/역사");
	      genre.add("추리스릴러");
	      
	      //today 삽입 
	      Calendar cal = Calendar.getInstance();
	      int month = cal.get ( cal.MONTH ) + 1 ;
	      int date = cal.get ( cal.DATE ) ;
	      String today = String.valueOf(month)+"월"+String.valueOf(date)+"일";
	      //장르 7개 
	      
	      List<String> categoryList = new ArrayList<String>();
	      categoryList.add("연극");
	      categoryList.add("뮤지컬");
	      
	      List<String> sThumbList = new ArrayList<String>();
	      List<String> sLocationList = new ArrayList<String>();
	      List<String> sPeriodList = new ArrayList<String>();
	      List<String> sTitleList = new ArrayList<String>();
	      List<String> sCategoryList = new ArrayList<String>();
	      List<String> sGenreList = new ArrayList<String>();
	      
	      
	   //1월 6일의 연극 전체 데이터 중 1페이지(8개 내용) 
	      for(int aa=0;aa<categoryList.size();aa++){
	         for(int u=0;u<genre.size();u++){
	            for(int n=1;n<180;n=n+8){
	               String url = String.format("https://m.search.naver.com/p/csearch/content/qapirender.nhn?key=PerformListAPI&where=nexearch&pkid=269&_callback=&q=%s+%s+%s+공연&so=&start=%d&_=1578287261525", today, categoryList.get(aa), genre.get(u), n);
	               //String url = String.format("https://m.search.naver.com/p/csearch/content/qapirender.nhn?key=PerformListAPI&where=nexearch&pkid=269&_callback=&q=%s+뮤지컬+%s+공연&so=&start=%d&_=1578128229725", today, genre.get(u), n)
	            
	               Document doc = Jsoup.connect(url).ignoreContentType(true).get();
	               String clean = doc.toString().replaceAll("&quot;", "").replaceAll("\\\\", "").replaceAll("&lt;", "<").replaceAll("&gt;" , ">");;
	               Document doc2 = Jsoup.parse(clean);
	               
	               Elements contentlist = doc2.select("ul[class]"); 
	               
	               for(int i=0;i<contentlist.size();i++){
	                  
	                  Elements contents = contentlist.get(i).select("li");
	                  
	                  
	                  for(int j=0;j<contents.size();j++){
	                     Elements thumblist = contents.get(j).select("div[class=list_thumb]"); // 이미지<img>
	                        Elements titleList = contents.get(j).select("div[class=list_title]"); // 이름, 장소(list_cate), period
	                        Elements periodList = contents.get(j).select("span[class=period]"); // 기간 
	                        
	                        //img 가져오기
	                        for (int jj=0;jj<thumblist.size();jj++){
	                           String img = thumblist.get(jj).select("img").attr("src");
	                           //System.out.println("img:::"+ img);
	                           sThumbList.add(img);
	                        }
	                        
	                        //이름 가져오기 
	                        for(int k=0;k<titleList.size();k++){
	                           
	                           Elements locationinfos = titleList.get(k).select("a");
	                           //location
	                           for(int kk=0;kk<locationinfos.size();kk++){
	                              if("".equals(locationinfos.get(kk).attr("class"))){
	                                 //System.out.println("location::::" + locationinfos.get(kk).text());
	                                 sLocationList.add(locationinfos.get(kk).text());
	                              }
	                           //title
	                              if("tit".equals(locationinfos.get(kk).attr("class"))){
	                                 //System.out.println("titile::::" + locationinfos.get(kk).text());
	                                 sTitleList.add(locationinfos.get(kk).text());
	                              }
	                           }
	                        }
	                        //period
	                        //System.out.println("periodList:::" + periodList.text());
	                        sPeriodList.add(periodList.text());
	                        //System.out.println("::::::::::::::::::::::::::::::::::"+j+":::::::::::::::::::::::::::::::::::::");
	                        //if(j==7){System.out.println("========================"+((n-1)/8+1)+"page=============================");}
	                        sCategoryList.add(categoryList.get(aa));
	                        sGenreList.add(genre.get(u));
	                  }
	               }
	            }
	         }
	      }
	      
//	      System.out.println(sCategoryList.toString());
//	      System.out.println(sGenreList.toString());
//	      System.out.println(sTitleList.toString());
//	      System.out.println(sPeriodList.toString());
//	      System.out.println(sLocationList.toString());
//	      System.out.println(sThumbList.toString());
	      
	         for(int sqlidx=0;sqlidx<sCategoryList.size();sqlidx++){
	             String category = sCategoryList.get(sqlidx);
	             String sGenre = sGenreList.get(sqlidx);
	             String title = sTitleList.get(sqlidx);
	             String period = sPeriodList.get(sqlidx);
	             String location = sLocationList.get(sqlidx);
	             String thumb = sThumbList.get(sqlidx);
	             
	             ContentInfo contentInfo = new ContentInfo();
	             contentInfo.setCategory(category);
	             contentInfo.setGenre(sGenre);
	             contentInfo.setLocation(location);
	             contentInfo.setPeriod(period);
	             contentInfo.setThumb(thumb);
	             contentInfo.setTitle(title);
	             
	             //System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
	             
	             //입력하려는 컨텐츠가 이미 저장되어 있는지 카운트를 구한다
	             int existcount = contentRepository.existcount(title, category);
	             
	             //ContentInfo existcount = contentRepository.findByTitleAndCategory(title, category);
	             if(existcount <= 0) {
	            	 contentRepository.save(contentInfo);
	             }else {
	            	 contentRepository.updatecontent(period, thumb, sGenre, title, category);
	             }	             
	         }
	      
	}
}
