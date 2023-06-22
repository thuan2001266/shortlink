package com.nauht.shortlink.controller;

import com.nauht.shortlink.model.LinkMap;
import com.nauht.shortlink.repository.LinkRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

//@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class LinkController {
    @Autowired
    LinkRepository linkRepository;

    org.slf4j.Logger logger = LoggerFactory.getLogger(LinkController.class);
    @GetMapping("links")
    public ResponseEntity<List<LinkMap>> getAllTutorials() {
        try {
            List<LinkMap> tutorials = new ArrayList<LinkMap>();
            linkRepository.findAll().forEach(tutorials::add);

            if (tutorials.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(tutorials, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/links")
    public ResponseEntity<LinkMap> createLinkMap(@RequestBody LinkMap linkMap) {
        try {
            LinkMap tutorial = linkRepository
                    .save(new LinkMap(linkMap.getLink(), linkMap.getShortedLink()));
            return new ResponseEntity<>(tutorial, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
