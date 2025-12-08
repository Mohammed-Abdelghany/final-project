package com.example.ecommerce.controller.vm;

import com.example.ecommerce.dto.ProductDto;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResponse<T> {
    private List<T> content;
    private int currentPage;
    private int totalPages;
    private long totalItems;
    private int pageSize;
    private boolean first;
    private boolean last;

}
