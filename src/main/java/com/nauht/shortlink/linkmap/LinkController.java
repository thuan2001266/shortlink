package com.nauht.shortlink.linkmap;

import com.nauht.shortlink.config.JwtService;
import com.nauht.shortlink.statistic.ClickDetail;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/linkmap")
@Tag(name = "Linkmap")
@CrossOrigin
public class LinkController {
    @Autowired
    LinkRepository linkRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private LinkMapService linkMapService;

    org.slf4j.Logger logger = LoggerFactory.getLogger(LinkController.class);

    @GetMapping("/getAll")
    public ResponseEntity<List<LinkMap>> getAllLinkMap(@RequestHeader("Authorization") String authorizationHeader) {
        return linkMapService.findAllLinkMap(authorizationHeader);
    }

    @GetMapping
    public ResponseEntity<List<LinkMap>> getAllLinkMapByUserName(@RequestHeader("Authorization") String authorizationHeader) {
        return linkMapService.findLinkMapByUserName(authorizationHeader);
    }
    @GetMapping
    public ResponseEntity<?> getAllLinkMapByUserNameWithPageAndSearch(@RequestHeader("Authorization") String authorizationHeader, @RequestParam(value = "page", defaultValue = "1", required = false) Integer page, @RequestParam(value = "search", defaultValue = "", required = false) String search) {
        return linkMapService.findLinkMapByUserNameWithPageAndSearch(authorizationHeader, page, search);
    }

    @PostMapping
    public ResponseEntity<LinkMap> createLinkMap(@RequestHeader("Authorization") String authorizationHeader, @RequestBody LinkMap linkMap) {
        return linkMapService.createLinkMap(authorizationHeader, linkMap);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LinkMap> getLinkMapById(@RequestHeader("Authorization") String authorizationHeader, @PathVariable("id") long id) {
        return linkMapService.getLinkMapById(authorizationHeader, id);
    }

    @PostMapping("/duplicate/{id}")
    public ResponseEntity<LinkMap> duplicateLinkMap(@RequestHeader("Authorization") String authorizationHeader, @PathVariable("id") long id) {
        return linkMapService.duplicateLinkMap(authorizationHeader, id);
    }

    @PutMapping("/updateLink")
    public ResponseEntity<LinkMap> updateLinkMap(@RequestHeader("Authorization") String authorizationHeader, @RequestBody LinkMap linkMap) {
        return linkMapService.updateLinkMap(authorizationHeader, linkMap);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteLinkMap(@RequestHeader("Authorization") String authorizationHeader, @PathVariable("id") long id) {
        return linkMapService.deleteLinkMap(authorizationHeader, id);
    }
}
