package com.nauht.shortlink.linkmap;

import jakarta.persistence.*; // for Spring Boot 3
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "linkmap")
public class LinkMap {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "link")
    private String link;

    @Column(name = "shortedLink")
    private String shortedLink;

    @Column(name = "clickedTime")
    private int clickedTime;

    @Column(name = "UTMSource")
    private String UTMSource;

    @Column(name = "UTMMedium")
    private String UTMMedium;

    @Column(name = "UTMCampaign")
    private String UTMCampaign;

    @Column(name = "UTMTerm")
    private String UTMTerm;

    @Column(name = "UTMContent")
    private String UTMContent;

    @Column(name = "createdBy")
    private String createdBy;

    @Column(name = "createdAt")
    private long createdAt;

    public void clicked() {
        setClickedTime(this.getClickedTime()+1);
    }


}