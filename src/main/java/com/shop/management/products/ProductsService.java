package com.shop.management.products;

import com.shop.management.orders.Orders;
import com.shop.management.products.dto.ProductUpdateDTO;
import com.shop.management.products.dto.ProductsDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductsService {

    private final ProductsRepository productsRepository;

    public List<ProductsDTO> getProductsList() {

        List<ProductsDTO> list = productsRepository.findAll().stream().map(ProductsDTO::fromEntity).toList();

        return list;
    }

    public void updateProduct(ProductUpdateDTO product) {
        productsRepository.save(Orders.fromUpdateDto(product));
    }
}
