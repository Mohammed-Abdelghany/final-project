package com.example.ecommerce.helper;

import com.example.ecommerce.dto.ProductDto;
import org.springframework.data.domain.PageRequest;



public class Pagination {

    public static PageRequest getPageRequest(int page, int size) {
        if (page < 1) {
            throw new IllegalArgumentException("page.number.invalid");
        }
        return PageRequest.of(page-1, size);
    }

}
