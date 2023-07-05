package com.nauht.shortlink.statistic;

import com.nauht.shortlink.linkmap.LinkMap;
import com.nauht.shortlink.linkmap.LinkMapService;
import com.nauht.shortlink.user.User;
import com.nauht.shortlink.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/statistic")
public class ClickController {

    @Autowired
    private StatisticService statisticService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/click")
    public ResponseEntity<ClickRecord> clickHandler(@RequestBody ClickDetail clickDetail) {
        return statisticService.handleLinkClick(clickDetail);
    }

    @GetMapping()
    public ResponseEntity<List<ClickRecord>> getAllRecord(@RequestHeader("Authorization") String authorizationHeader) {
        return statisticService.getAllClick(authorizationHeader);
    }

    @GetMapping("/linkmap/{linkId}")
    public ResponseEntity<List<ClickRecord>> getRecordByLinkMapId(@RequestHeader("Authorization") String authorizationHeader, @PathVariable("linkId") long linkId) {
        return statisticService.getLinkClick(authorizationHeader, linkId);
    }
}
