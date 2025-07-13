package com.chthonic.productcomparerfrontend.web.dto;

import lombok.Data;

import java.util.List;

@Data
public class GroupedProduct {
    String normalizedName;
    List<Product> products;
}
