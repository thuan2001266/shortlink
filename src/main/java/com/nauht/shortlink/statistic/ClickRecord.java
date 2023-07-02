package com.nauht.shortlink.statistic;

import com.nauht.shortlink.linkmap.LinkMap;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ClickRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "linkMapId")
    private LinkMap linkId;

    @Column(name = "timeClick")
    private long timeClick;

    @Column(name = "location")
    private String location;

    @Column(name = "device")
    private String device;

    @Column(name = "browser")
    private String browser;
}
