package com.nauht.shortlink.repository;

import com.nauht.shortlink.model.LinkMap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LinkRepository extends JpaRepository<LinkMap, Long> {
    List<LinkMap> findByLink(String link);


    List<LinkMap> findByShortedLink(String shortedlink);

}
