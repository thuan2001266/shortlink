package com.nauht.shortlink.statistic;

import com.nauht.shortlink.linkmap.LinkMap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StatisticRepository extends JpaRepository<ClickRecord, Long> {
    void deleteByLinkId(LinkMap linkId);

    List<ClickRecord> findByLinkIdId(long linkIdId);
}
