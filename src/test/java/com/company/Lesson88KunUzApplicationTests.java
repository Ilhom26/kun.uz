package com.company;

import com.company.service.ArticleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Lesson88KunUzApplicationTests {
	@Autowired
	private ArticleService articleService;
	@Test
	void createProfile() {
		System.out.println(articleService.listRegionId4(1));
		System.out.println(articleService.list4());
		System.out.println(articleService.listCategory4(2));
	}

}
