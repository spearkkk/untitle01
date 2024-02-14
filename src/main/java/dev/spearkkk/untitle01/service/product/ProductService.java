package dev.spearkkk.untitle01.service.product;

import dev.spearkkk.untitle01.model.category.Category;
import dev.spearkkk.untitle01.model.product.Product;
import dev.spearkkk.untitle01.model.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> findLowestProductsByCategory() {
        List<Product> products = new ArrayList<>();

        for (Category category : Category.values()) {
            Optional<Product> product = productRepository.findLowestPriceProductGroupByCategory(category);
            product.ifPresent(products::add);
        }

        return products;
    }

    public List<Product> findLowestProductsByBrand() {
        Set<String> brands = productRepository.findAllUniqueBrands();
        List<Product> lowestProducts = new ArrayList<>();
        Long lowestTotalSum = Long.MAX_VALUE;

        for (String brand : brands) {
            List<Product> candidates = productRepository.findAllByBrand(brand);
            Long totalSum = candidates.stream().map(Product::getPrice).reduce(Long::sum).orElse(Long.MAX_VALUE);
            if (totalSum < lowestTotalSum) {
                lowestTotalSum = totalSum;
                lowestProducts = candidates;
            }
        }

        return lowestProducts;
    }

    public Optional<Product> findLowestProductByCategory(String category) {
        if (Arrays.stream(Category.values()).map(Enum::name).anyMatch(it -> it.equals(category))) {
            return productRepository.findLowestPriceProductGroupByCategory(Category.valueOf(category));
        }

        return Optional.empty();
    }

    public Optional<Product> findHighestProductByCategory(String category) {
        if (Arrays.stream(Category.values()).map(Enum::name).anyMatch(it -> it.equals(category))) {
            return productRepository.findHighestPriceProductGroupByCategory(Category.valueOf(category));
        }

        return Optional.empty();
    }

    public List<Product> create(List<Product> products) {
        if (products.size() != Category.values().length || products.size() == 0) {
            throw new IllegalArgumentException("새로 추가할 상품은 반드시 모든 카테고리의 상품이 하나씩 필요합니다.");
        }
        Optional<Product> existBrandProduct = productRepository.findFirstByBrand(products.get(0).getBrand());
        if (existBrandProduct.isPresent()) {
            throw new IllegalArgumentException("새로 추가할 상품의 브랜드가 이미 존재합니다.");
        }
        for (Product product : products) {
            productRepository.save(product);
        }
        return products;
    }

    public Product update(String brand, Category category, Long price) {
        List<Product> existProducts = productRepository.findAllByBrand(brand);

        for (Product product : existProducts) {
            if (product.getCategory().equals(category)) {
                return productRepository.update(product.toBuilder().price(price).build());
            }
        }
        throw new IllegalArgumentException("존재하지 않은 브랜드나 카테고리의 상품입니다.");
    }

    public void delete(String brand) {
        List<Product> existProducts = productRepository.findAllByBrand(brand);
        if (existProducts.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않은 브랜드의 상품입니다.");
        }

        for (Product product : existProducts) {
            productRepository.delete(product);
        }
    }
}
