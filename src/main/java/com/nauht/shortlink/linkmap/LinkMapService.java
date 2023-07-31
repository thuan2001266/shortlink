package com.nauht.shortlink.linkmap;

import com.nauht.shortlink.Pagination.Pagination;
import com.nauht.shortlink.config.JwtService;
import com.nauht.shortlink.statistic.ClickDetail;
import com.nauht.shortlink.statistic.ClickRecord;
import com.nauht.shortlink.statistic.StatisticRepository;
import io.jsonwebtoken.Claims;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class LinkMapService {

    @Autowired
    private LinkRepository linkRepository;

    @Autowired
    private StatisticRepository statisticRepository;

    @Autowired
    private JwtService jwtService;

//    @Value("${admin.emails}")
//    private List<String> adminEmails;

    org.slf4j.Logger logger = LoggerFactory.getLogger(LinkMapService.class);

    public String getUserName(String authorizationHeader) {
        return jwtService.extractUsername(authorizationHeader.substring(7));
    }

    public String getRole(String authorizationHeader) {
        return jwtService.extractRole(authorizationHeader.substring(7));
    }

    public ResponseEntity<List<LinkMap>> findAllLinkMap(String authorizationHeader) {
        try {
            if (getRole(authorizationHeader).equals("ADMIN")) {
                List<LinkMap> allLink = new ArrayList<LinkMap>();
                linkRepository.findAll().forEach(allLink::add);

                if (allLink.isEmpty()) {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }

                return new ResponseEntity<>(allLink, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<LinkMap>> findLinkMapByUserName(String authorizationHeader) {
        try {
            List<LinkMap> allLink = new ArrayList<LinkMap>();
            linkRepository.findByCreatedBy(getUserName(authorizationHeader)).forEach(allLink::add);

            if (allLink.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(allLink, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> findLinkMapByUserNameWithPageAndSearch(String authorizationHeader, Integer page, String search) {
        try {
            List<LinkMap> allLink = new ArrayList<LinkMap>();
            linkRepository.findByCreatedBy(getUserName(authorizationHeader)).forEach(allLink::add);

            if (allLink.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return Pagination.pagination(page, allLink, search);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<LinkMap> createLinkMap(String authorizationHeader, LinkMap linkMap) {
        try {
            LinkMap newLink = linkRepository
                    .save(LinkMap.builder()
                            .link(linkMap.getLink())
                            .shortedLink(linkMap.getShortedLink())
                            .UTMSource(linkMap.getUTMSource())
                            .UTMCampaign(linkMap.getUTMCampaign())
                            .UTMTerm(linkMap.getUTMTerm())
                            .UTMMedium(linkMap.getUTMMedium())
                            .UTMContent(linkMap.getUTMContent())
                            .createdBy(getUserName(authorizationHeader))
                            .createdAt(linkMap.getCreatedAt())
                            .clickedTime(0)
                            .build()

                    );
            return new ResponseEntity<>(newLink, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<LinkMap> getLinkMapById(String authorizationHeader, long id) {
        Optional<LinkMap> linkById = linkRepository.findById(id);
        if (linkById.isPresent()) {
            if (linkById.get().getCreatedBy().equals(getUserName(authorizationHeader)) || getRole(authorizationHeader).equals("ADMIN")) {
                return new ResponseEntity<>(linkById.get(), HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public static String generateRandomString(int length) {
        String characters = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            sb.append(randomChar);
        }

        return sb.toString();
    }

    public ResponseEntity<LinkMap> duplicateLinkMap(String authorizationHeader, long id) {
        Optional<LinkMap> linkById = linkRepository.findById(id);
        if (linkById.isPresent()) {
            if (linkById.get().getCreatedBy().equals(getUserName(authorizationHeader))) {
                LinkMap targetLinkMap = linkById.get();
                while (true) {
                    String randomString = generateRandomString(7);
                    if (!linkRepository.findByShortedLink(randomString).isPresent()) {
                        LinkMap duplicatedLinkMap = LinkMap.builder()
                                .link(targetLinkMap.getLink())
                                .shortedLink(randomString)
                                .UTMSource(targetLinkMap.getUTMSource())
                                .UTMCampaign(targetLinkMap.getUTMCampaign())
                                .UTMTerm(targetLinkMap.getUTMTerm())
                                .UTMMedium(targetLinkMap.getUTMMedium())
                                .UTMContent(targetLinkMap.getUTMContent())
                                .createdBy(targetLinkMap.getCreatedBy())
                                .createdAt(targetLinkMap.getCreatedAt())
                                .clickedTime(0)
                                .build();
                        return ResponseEntity.ok(linkRepository.save(duplicatedLinkMap));
                    }
                }
            }
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<LinkMap> updateLinkMap(String authorizationHeader, LinkMap linkMap) {
        try {
            Optional<LinkMap> linkById = linkRepository.findByShortedLink(linkMap.getShortedLink());
            if (linkById.isPresent()) {
                if (linkById.get().getCreatedBy().equals(getUserName(authorizationHeader)) || getRole(authorizationHeader).equals("ADMIN")) {
                    LinkMap targetLinkMap = linkById.get();
                    targetLinkMap.setLink(linkMap.getLink());
                    targetLinkMap.setShortedLink(linkMap.getShortedLink());
                    targetLinkMap.setUTMSource(linkMap.getUTMSource());
                    targetLinkMap.setUTMMedium(linkMap.getUTMMedium());
                    targetLinkMap.setUTMCampaign(linkMap.getUTMCampaign());
                    targetLinkMap.setUTMTerm(linkMap.getUTMTerm());
                    targetLinkMap.setUTMContent(linkMap.getUTMContent());
                    return ResponseEntity.ok(linkRepository.save(targetLinkMap));
                }
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);

            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<HttpStatus> deleteLinkMap(String authorizationHeader, long id) {
        try {
            Optional<LinkMap> linkById = linkRepository.findById(id);
            if (linkById.isPresent()) {
                if (linkById.get().getCreatedBy().equals(getUserName(authorizationHeader)) || getRole(authorizationHeader).equals("ADMIN")) {
                    List<ClickRecord> clickRecords = statisticRepository.findByLinkIdId(linkById.get().getId());
                    statisticRepository.deleteAll(clickRecords);
                    linkRepository.deleteById(id);
                    return new ResponseEntity<>(HttpStatus.OK);
                }
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            logger.info(e.toString());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
