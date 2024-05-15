package com.vinsguru.orderservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductDTO {
    private String id;
    private String description;
    private Integer price;

}
