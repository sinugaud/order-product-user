package com.javapedia.OrderEase.fallback;

import com.javapedia.OrderEase.controller.ProductClient;
import com.javapedia.OrderEase.dto.Product;
import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Log4j2
@Component
public class ProductFallbackService implements FallbackFactory<ProductClient> {

//    @Override
//    public List<Product> getAllProducts() {
//        log.info("fallback method list of product");
//
//        // Return a default list or an empty list if service is unavailable
//        return Collections.emptyList();
//    }
//
//
//
//    @Override
//    public Product getProductById(Long id) {
//        log.info("fallback method product id");
//
////         Return a default product or handle the failure differently
//        return new Product(id, "unavailableProduct");
//    }
//
////    @Override
////    public Product createProduct(Product product) {
////        log.info("fallback method create product");
////
////        // Return a default product or handle the failure differently
////        return new Product(0L, "Failed to create product");
////    }
//
//    @Override
//    public String deleteProductById(Long id) {
//        // Return a default message or handle the failure differently
//        return "Failed to delete product with ID: " + id;
//    }

    @Override
    public ProductClient create(Throwable cause) {
            log.error("An exception occurred when calling the UserSessionClient", cause);
            return new ProductClient() {
                @Override
                public List<Product> getAllProducts() {
                    log.info("fallback method list of product");

                    return List.of();
                }

                @Override
                public Product getProductById(Long id) {
                    log.info("fallback method product id");

                    return null;
                }

                @Override
                public String deleteProductById(Long id) {
                    log.info("Failed to delete product with ID: {}" , id);

                    return "";
                }


            };    }
}
