package com.nauht.shortlink.statistic;

import com.nauht.shortlink.linkmap.LinkMap;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClickDetail {
    private String shortLink;

    private long timeClick;

    private String location;

    private String device;

    private String browser;
}
