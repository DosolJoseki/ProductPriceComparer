package com.chthonic.productcomparerfrontend.web.controllers;

import com.chthonic.productcomparerfrontend.web.dto.Product;
import com.chthonic.productcomparerfrontend.web.services.HomeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class HomeController {
    private final HomeService homeService;

    public HomeController(HomeService homeService) {
        this.homeService = homeService;
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/products")
    public String searchProducts(@RequestParam(value = "query") String query,
                                 @RequestParam(value = "sort", required = false) String sort,
                                 Model model) {
        List<Product> products = homeService.getProductsByName(query);
        products = products.stream().filter(e -> e.getName() != null).collect(Collectors.toList());
        if (sort != null && !sort.isEmpty() && !products.isEmpty()) {
            switch (sort) {
                case "name": products.sort(Comparator.comparing(Product::getName, Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER)));
                    break;
                case "price": products.sort(Comparator.comparing(Product::getPrice));
                    break;
            }
        }

        setLogoImage(products);

        model.addAttribute("products", products);
        model.addAttribute("query", query);

        return "home";
    }

    @GetMapping("/grouped-products")
    public String searchGroupedProducts(@RequestParam(value = "query") String query,
                                        @RequestParam(value = "sort", required = false) String sort,
                                        Model model) {
        Map<String, List<Product>> products = homeService.getProductsByNameGroupedByName(query);
//        products = products.stream().filter(e -> e.getName() != null).collect(Collectors.toList());
//        if (sort != null && !sort.isEmpty() && !products.isEmpty()) {
//            switch (sort) {
//                case "name": products.sort(Comparator.comparing(Product::getName, Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER)));
//                    break;
//                case "price": products.sort(Comparator.comparing(Product::getPrice));
//                    break;
//            }
//        }

        setLogoImageByShop(products);
        products = products.entrySet().stream()
                .filter(e -> e.getValue().size() > 1)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1, // merge function (not used here)
                        LinkedHashMap::new // сохраняем порядок сортировки
                ));
//                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));


        model.addAttribute("groupedProducts", products);
        model.addAttribute("query", query);

        return "home";
    }

    private void setLogoImage(List<Product> products) {
        products.stream().forEach(product -> {
            switch (product.getShopName()) {
                case "Spar":
                    product.setLogoImage("/images/spar-logo.png");
                    break;
                case "Mercator":
                    product.setLogoImage("/images/mercator-logo.webp");
                    break;
            }
        });
    }

    private void setLogoImageByShop(Map<String, List<Product>> products) {
        products.entrySet().forEach(entry -> {
            entry.getValue().forEach(product -> {
                switch (product.getShopName()) {
                    case "Spar":
                        product.setLogoImage("/images/spar-logo.png");
                        break;
                    case "Mercator":
                        product.setLogoImage("/images/mercator-logo.webp");
                        break;
                }
            });
        });
    }
}