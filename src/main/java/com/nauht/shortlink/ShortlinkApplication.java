package com.nauht.shortlink;

import com.nauht.shortlink.model.LinkMap;
import com.nauht.shortlink.repository.LinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ShortlinkApplication {
	@Autowired
	LinkRepository linkRepository;

	public static void main(String[] args) {
//		linkRepository.save(new LinkMap("asdasd","1a"));
		SpringApplication.run(ShortlinkApplication.class, args);
	}



}
