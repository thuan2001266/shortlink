package com.nauht.shortlink.linkmap;

import com.nauht.shortlink.linkmap.LinkMap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LinkRepository extends JpaRepository<LinkMap, Long> {
    List<LinkMap> findByLink(String link);
    List<LinkMap> findByCreatedBy(String createdBy);

//    List<LinkMap> findByCreatedByEmail(String email);
    List<LinkMap> findByShortedLink(String shortedlink);
}
