package com.nauht.shortlink.Pagination;

import com.nauht.shortlink.linkmap.LinkMap;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

public class Pagination {
    public static <T> ResponseEntity<?> pagination(int page, List<T> data, String search) {
        List<T> list = new ArrayList<>();

        if (!search.equals("")) {
            for (int i = 0; i < data.size(); i++) {
                    T item = data.get(i);

                    if (item instanceof LinkMap) {
                        LinkMap linkMap = (LinkMap) item;
                        if (linkMap.getLink().toLowerCase().contains(search.toLowerCase()) || linkMap.getShortedLink().toLowerCase().contains(search.toLowerCase()) || linkMap.getCreatedBy().toLowerCase().contains(search.toLowerCase())) {
                            list.add(item);
                        }
                    }
            }
        } else {
            list = data;
        }
        int index = (page - 1) * 12;
        int count = index + 12;

        List<T> result = new ArrayList<>();
        while (index < list.size() && index < count) {
            result.add(list.get(index));
            index++;
        }

        int totalPage = list.size() / 12;
        if (list.size() % 12 > 0) {

            totalPage += 1;
            if (totalPage < page) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<DataWithPageResponse>(DataWithPageResponse.builder().size(list.size()).maxPage(totalPage).currentPage(page).startIndex(index).data(result).build(), HttpStatus.OK);
    }
}
