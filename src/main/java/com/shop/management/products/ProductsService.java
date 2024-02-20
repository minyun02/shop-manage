package com.shop.management.products;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductsService {

    private final ProductsRepository productsRepository;

    public List<ProductsDTO> getProductsList() {
        List<ProductsDTO> list = productsRepository.findAll().stream().map(ProductsDTO::fromEntity).toList();
        System.out.println("list = " + list);
        return list;
    }
}
