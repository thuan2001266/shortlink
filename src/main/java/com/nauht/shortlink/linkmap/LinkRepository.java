package com.nauht.shortlink.linkmap;

import com.nauht.shortlink.linkmap.LinkMap;
import com.nauht.shortlink.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LinkRepository extends JpaRepository<LinkMap, Long> {
    List<LinkMap> findByLink(String link);
    List<LinkMap> findByCreatedBy(String createdBy);

//    List<LinkMap> findByCreatedByEmail(String email);
    Optional<LinkMap> findByShortedLink(String shortedlink);
}
