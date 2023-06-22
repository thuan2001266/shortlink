package com.nauht.shortlink.model;

import jakarta.persistence.*; // for Spring Boot 3

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

    public LinkMap() {

    }

    public LinkMap(String link, String shortedLink) {
        this.link = link;
        this.shortedLink = shortedLink;
    }

    public long getId() {
        return id;
    }



    public void setId(long id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getShortedLink() {
        return shortedLink;
    }

    public void setShortedLink(String shortedLink) {
        this.shortedLink = shortedLink;
    }

    @Override
    public String toString() {
        return "LinkMap{" +
                "id=" + id +
                ", link='" + link + '\'' +
                ", shortedLink='" + shortedLink + '\'' +
                '}';
    }
}