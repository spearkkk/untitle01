package dev.spearkkk.untitle01.controller.product;

import dev.spearkkk.untitle01.controller.product.dto.*;
import dev.spearkkk.untitle01.model.category.Category;
import dev.spearkkk.untitle01.model.product.Product;
import dev.spearkkk.untitle01.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class ProductController {
    private final ProductService productService;

    @GetMapping("/v1/products/lowest-by-category")
    public LowestByCategoryResponse lowestByCategory() {
        List<Product> products = productService.findLowestProductsByCategory();

        long totalSum = 0L;
        List<ProductResponse> productResponses = new ArrayList<>();
        for (Product product : products) {
            totalSum += product.getPrice();

            productResponses.add(ProductResponse.builder()
                    .brand(product.getBrand())
                    .category(product.getCategory().name())
                    .price(product.getPrice())
                    .build());
        }

        return LowestByCategoryResponse.builder()
                .totalSum(totalSum)
                .products(productResponses)
                .build();
    }

    @GetMapping("/v1/products/lowest-by-brand")
    public LowestByBrandResponse lowestByBrand() {

        List<Product> products = productService.findLowestProductsByBrand();
        Long totalSum = 0L;
        String brand = products.stream().map(Product::getBrand).findAny().orElseThrow();

        List<LowestByBrandResponse.CategoryToPrice> categoryToPrices = new ArrayList<>();
        for (Product product : products) {
            categoryToPrices.add(LowestByBrandResponse.CategoryToPrice.builder()
                    .category(product.getCategory().name())
                    .price(product.getPrice())
                    .build());

            totalSum += product.getPrice();
        }

        return LowestByBrandResponse.builder()
                .lowest(LowestByBrandResponse.InnerResponse.builder()
                        .totalSum(totalSum)
                        .brand(brand)
                        .categories(categoryToPrices)
                        .build())
                .build();
    }

    @GetMapping("/v1/products/lowest-and-highest")
    public LowestAndHighestResponse lowestAndHighest(@RequestParam String category) {
        Optional<Product> lowestProduct = productService.findLowestProductByCategory(category);
        Optional<Product> highestProduct = productService.findHighestProductByCategory(category);

        List<LowestAndHighestResponse.BrandToPrice> lowestBrandToPrices = new ArrayList<>();
        List<LowestAndHighestResponse.BrandToPrice> highestBrandToPrices = new ArrayList<>();

        lowestProduct.ifPresent(it -> lowestBrandToPrices.add(
                LowestAndHighestResponse.BrandToPrice.builder()
                        .brand(it.getBrand())
                        .price(it.getPrice())
                        .build()
        ));

        highestProduct.ifPresent(it -> highestBrandToPrices.add(
                LowestAndHighestResponse.BrandToPrice.builder()
                        .brand(it.getBrand())
                        .price(it.getPrice())
                        .build()
        ));

        return LowestAndHighestResponse.builder()
                .lowest(lowestBrandToPrices)
                .highest(highestBrandToPrices)
                .category(category)
                .build();
    }

    @PostMapping("/v1/products")
    public List<ProductResponse> post(@RequestBody ProductsRequest request) {
        String brand = request.getBrand();
        List<Product> products = new ArrayList<>();
        for (ProductRequest item : request.getItems()) {
            if (Arrays.stream(Category.values()).map(Enum::name).anyMatch(it -> it.equals(item.getCategory()))) {
                products.add(Product.builder()
                        .brand(brand)
                        .category(Category.valueOf(item.getCategory()))
                        .price(item.getPrice()).build());
            } else {
              throw new IllegalArgumentException("유효하지 않은 카테고리의 상품이 존재합니다.");
            }
        }
        return productService.create(products).stream().map(it -> ProductResponse.builder()
                .brand(it.getBrand())
                .category(it.getCategory().name())
                .price(it.getPrice())
                .build()).collect(Collectors.toList());
    }

    @PutMapping("/v1/products/{brand}")
    public ProductResponse put(@PathVariable String brand, @RequestBody ProductRequest request) {
        if (Arrays.stream(Category.values()).map(Enum::name).anyMatch(it -> it.equals(request.getCategory()))) {
            Product updatedProduct = productService.update(brand, Category.valueOf(request.getCategory()), request.getPrice());
            return ProductResponse.builder().brand(updatedProduct.getBrand()).category(updatedProduct.getCategory().name()).price(updatedProduct.getPrice()).build();
        }
        throw new IllegalArgumentException("유효하지 않은 카테고리의 상품이 존재합니다.");
    }

    @DeleteMapping("/v1/products/{brand}")
    public void delete(@PathVariable String brand) {
        productService.delete(brand);
    }
}
