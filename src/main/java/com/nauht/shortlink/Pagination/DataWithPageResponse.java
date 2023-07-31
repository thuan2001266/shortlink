package com.nauht.shortlink.Pagination;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DataWithPageResponse {
    List<?> data;
    int currentPage;
    int maxPage;
    int startIndex;

    int size;
}
