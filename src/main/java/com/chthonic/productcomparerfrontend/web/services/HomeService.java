package com.chthonic.productcomparerfrontend.web.services;

import com.chthonic.productcomparerfrontend.web.dto.Product;

import java.util.List;

public interface HomeService {
    List<Product> getProductsByName(String name);
}
