package com.chthonic.productcomparerfrontend.web.dto;

import lombok.Data;

import java.net.URL;

@Data
public class Product {
    private String shopName;
    private String brand;
    private String name;
    private String title;
    private double price;
    private URL image;
}
