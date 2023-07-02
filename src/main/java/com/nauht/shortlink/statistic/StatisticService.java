package com.nauht.shortlink.statistic;

import com.nauht.shortlink.config.JwtService;
import com.nauht.shortlink.linkmap.LinkMap;
import com.nauht.shortlink.linkmap.LinkMapService;
import com.nauht.shortlink.linkmap.LinkRepository;
import io.swagger.v3.oas.models.links.Link;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StatisticService {
    @Autowired
    private LinkRepository linkRepository;

    @Autowired
    private StatisticRepository statisticRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private LinkMapService linkMapService;

    public ResponseEntity<HttpStatus> handleLinkClick(ClickDetail clickDetail) {
        try {
            Optional<LinkMap> linkMap = linkRepository.findById(clickDetail.getLinkId());
            if (linkMap.isPresent()) {
                LinkMap updateLinkMap = linkMap.get();
                updateLinkMap.clicked();
                linkRepository.save(updateLinkMap);
                statisticRepository.save(ClickRecord.builder()
                        .linkId(linkRepository.findById(clickDetail.getLinkId()).get())
                        .browser(clickDetail.getBrowser())
                        .timeClick(clickDetail.getTimeClick())
                        .location(clickDetail.getLocation())
                        .device(clickDetail.getDevice())
                        .build());
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<ClickRecord>> getAllClick(String authorizationHeader) {
        try {
            if (jwtService.extractRole(authorizationHeader.substring(7)).equals("ADMIN")) {
                List<ClickRecord> allClick = new ArrayList<ClickRecord>();
                statisticRepository.findAll().forEach(allClick::add);

                if (allClick.isEmpty()) {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }

                return new ResponseEntity<>(allClick, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<ClickRecord>> getLinkClick(String authorizationHeader, long linkId) {
        try {
            Optional<LinkMap> linkMap = linkRepository.findById(linkId);
            if (linkMap.isPresent()) {
                if (jwtService.extractRole(authorizationHeader.substring(7)).equals("ADMIN")
                        || linkMap.get().getCreatedBy().equals(jwtService.extractUsername(authorizationHeader.substring(7)))) {
                    List<ClickRecord> allClick = new ArrayList<ClickRecord>();
                    statisticRepository.findByLinkIdId(linkId).forEach(allClick::add);

                    if (allClick.isEmpty()) {
                        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                    }

                    return new ResponseEntity<>(allClick, HttpStatus.OK);
                }
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);


        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
