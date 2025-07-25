package com.chthonic.productcomparerfrontend.web.services;

import com.chthonic.productcomparerfrontend.web.dto.Product;

import java.util.List;
import java.util.Map;

public interface HomeService {
    List<Product> getProductsByName(String name);
    Map<String, List<Product>> getProductsByNameGroupedByName(String name);
}
