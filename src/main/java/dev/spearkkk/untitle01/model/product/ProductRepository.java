package dev.spearkkk.untitle01.model.product;

import dev.spearkkk.untitle01.model.category.Category;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ProductRepository {
    public Optional<Product> findLowestPriceProductGroupByCategory(Category category) {
      Product lowestProduct = null;
      for (Product product : LOCAL_MEMORY_DB) {
          if (product.getCategory().equals(category)) {
              if (lowestProduct == null) {
                  lowestProduct = product;
              } else if (product.getPrice() < lowestProduct.getPrice()) {
                  lowestProduct = product;
              }
          }
      }
      return Optional.ofNullable(lowestProduct);
    }

    public Optional<Product> findHighestPriceProductGroupByCategory(Category category) {
        Product highest = null;
        for (Product product : LOCAL_MEMORY_DB) {
            if (product.getCategory().equals(category)) {
                if (highest == null) {
                    highest = product;
                } else if (highest.getPrice() < product.getPrice()) {
                    highest = product;
                }
            }
        }
        return Optional.ofNullable(highest);
    }

    public Set<String> findAllUniqueBrands() {
        Set<String> brands = new HashSet<>();
        for (Product product : LOCAL_MEMORY_DB) {
            brands.add(product.getBrand());
        }
        return brands;
    }

    public List<Product> findAllByBrand(String brand) {
        List<Product> products = new ArrayList<>();
        for (Product product : LOCAL_MEMORY_DB) {
            if (product.getBrand().equals(brand)) {
                products.add(product);
            }
        }
        return products;
    }

    public Product save(Product entity) {
        LOCAL_MEMORY_DB.add(entity);
        return entity;
    }

    public Product update(Product entity) {
        LOCAL_MEMORY_DB.remove(entity);
        LOCAL_MEMORY_DB.add(entity);
        return entity;
    }

    public boolean delete(Product entity) {
        LOCAL_MEMORY_DB.remove(entity);
        return true;
    }

    public Optional<Product> findFirstByBrand(String brand) {
        return LOCAL_MEMORY_DB.stream().filter(it -> it.getBrand().equals(brand)).findFirst();
    }

    private static final Set<Product> LOCAL_MEMORY_DB;
    static {
        LOCAL_MEMORY_DB = new HashSet<>();

        // A brand
        LOCAL_MEMORY_DB.add(Product.of(11200L, "A", Category.TOP));
        LOCAL_MEMORY_DB.add(Product.of(5500L, "A", Category.OUTER));
        LOCAL_MEMORY_DB.add(Product.of(4200L, "A", Category.BOTTOM));
        LOCAL_MEMORY_DB.add(Product.of(9000L, "A", Category.SNEAKERS));
        LOCAL_MEMORY_DB.add(Product.of(2000L, "A", Category.BAG));
        LOCAL_MEMORY_DB.add(Product.of(1700L, "A", Category.HAT));
        LOCAL_MEMORY_DB.add(Product.of(1800L, "A", Category.SOCKS));
        LOCAL_MEMORY_DB.add(Product.of(2300L, "A", Category.ACCESSORY));

        // B brand
        LOCAL_MEMORY_DB.add(Product.of(10500L, "B", Category.TOP));
        LOCAL_MEMORY_DB.add(Product.of(5900L, "B", Category.OUTER));
        LOCAL_MEMORY_DB.add(Product.of(3800L, "B", Category.BOTTOM));
        LOCAL_MEMORY_DB.add(Product.of(9100L, "B", Category.SNEAKERS));
        LOCAL_MEMORY_DB.add(Product.of(2100L, "B", Category.BAG));
        LOCAL_MEMORY_DB.add(Product.of(2000L, "B", Category.HAT));
        LOCAL_MEMORY_DB.add(Product.of(2000L, "B", Category.SOCKS));
        LOCAL_MEMORY_DB.add(Product.of(2200L, "B", Category.ACCESSORY));

        // C brand
        LOCAL_MEMORY_DB.add(Product.of(10000L, "C", Category.TOP));
        LOCAL_MEMORY_DB.add(Product.of(6200L, "C", Category.OUTER));
        LOCAL_MEMORY_DB.add(Product.of(3300L, "C", Category.BOTTOM));
        LOCAL_MEMORY_DB.add(Product.of(9200L, "C", Category.SNEAKERS));
        LOCAL_MEMORY_DB.add(Product.of(2200L, "C", Category.BAG));
        LOCAL_MEMORY_DB.add(Product.of(1900L, "C", Category.HAT));
        LOCAL_MEMORY_DB.add(Product.of(2200L, "C", Category.SOCKS));
        LOCAL_MEMORY_DB.add(Product.of(2100L, "C", Category.ACCESSORY));

        // D brand
        LOCAL_MEMORY_DB.add(Product.of(10100L, "D", Category.TOP));
        LOCAL_MEMORY_DB.add(Product.of(5100L, "D", Category.OUTER));
        LOCAL_MEMORY_DB.add(Product.of(3000L, "D", Category.BOTTOM));
        LOCAL_MEMORY_DB.add(Product.of(9500L, "D", Category.SNEAKERS));
        LOCAL_MEMORY_DB.add(Product.of(2500L, "D", Category.BAG));
        LOCAL_MEMORY_DB.add(Product.of(1500L, "D", Category.HAT));
        LOCAL_MEMORY_DB.add(Product.of(2400L, "D", Category.SOCKS));
        LOCAL_MEMORY_DB.add(Product.of(2000L, "D", Category.ACCESSORY));

        // E brand
        LOCAL_MEMORY_DB.add(Product.of(10700L, "E", Category.TOP));
        LOCAL_MEMORY_DB.add(Product.of(5000L, "E", Category.OUTER));
        LOCAL_MEMORY_DB.add(Product.of(3800L, "E", Category.BOTTOM));
        LOCAL_MEMORY_DB.add(Product.of(9900L, "E", Category.SNEAKERS));
        LOCAL_MEMORY_DB.add(Product.of(2300L, "E", Category.BAG));
        LOCAL_MEMORY_DB.add(Product.of(1800L, "E", Category.HAT));
        LOCAL_MEMORY_DB.add(Product.of(2100L, "E", Category.SOCKS));
        LOCAL_MEMORY_DB.add(Product.of(2100L, "E", Category.ACCESSORY));

        // F brand
        LOCAL_MEMORY_DB.add(Product.of(11200L, "F", Category.TOP));
        LOCAL_MEMORY_DB.add(Product.of(7200L, "F", Category.OUTER));
        LOCAL_MEMORY_DB.add(Product.of(4000L, "F", Category.BOTTOM));
        LOCAL_MEMORY_DB.add(Product.of(9300L, "F", Category.SNEAKERS));
        LOCAL_MEMORY_DB.add(Product.of(2100L, "F", Category.BAG));
        LOCAL_MEMORY_DB.add(Product.of(1600L, "F", Category.HAT));
        LOCAL_MEMORY_DB.add(Product.of(2300L, "F", Category.SOCKS));
        LOCAL_MEMORY_DB.add(Product.of(1900L, "F", Category.ACCESSORY));

        // G brand
        LOCAL_MEMORY_DB.add(Product.of(10500L, "G", Category.TOP));
        LOCAL_MEMORY_DB.add(Product.of(5800L, "G", Category.OUTER));
        LOCAL_MEMORY_DB.add(Product.of(3900L, "G", Category.BOTTOM));
        LOCAL_MEMORY_DB.add(Product.of(9000L, "G", Category.SNEAKERS));
        LOCAL_MEMORY_DB.add(Product.of(2200L, "G", Category.BAG));
        LOCAL_MEMORY_DB.add(Product.of(1700L, "G", Category.HAT));
        LOCAL_MEMORY_DB.add(Product.of(2100L, "G", Category.SOCKS));
        LOCAL_MEMORY_DB.add(Product.of(2000L, "G", Category.ACCESSORY));

        // H brand
        LOCAL_MEMORY_DB.add(Product.of(10800L, "H", Category.TOP));
        LOCAL_MEMORY_DB.add(Product.of(6300L, "H", Category.OUTER));
        LOCAL_MEMORY_DB.add(Product.of(3100L, "H", Category.BOTTOM));
        LOCAL_MEMORY_DB.add(Product.of(9700L, "H", Category.SNEAKERS));
        LOCAL_MEMORY_DB.add(Product.of(2100L, "H", Category.BAG));
        LOCAL_MEMORY_DB.add(Product.of(1600L, "H", Category.HAT));
        LOCAL_MEMORY_DB.add(Product.of(2000L, "H", Category.SOCKS));
        LOCAL_MEMORY_DB.add(Product.of(2000L, "H", Category.ACCESSORY));

        // I brand
        LOCAL_MEMORY_DB.add(Product.of(11400L, "I", Category.TOP));
        LOCAL_MEMORY_DB.add(Product.of(6700L, "I", Category.OUTER));
        LOCAL_MEMORY_DB.add(Product.of(3200L, "I", Category.BOTTOM));
        LOCAL_MEMORY_DB.add(Product.of(9500L, "I", Category.SNEAKERS));
        LOCAL_MEMORY_DB.add(Product.of(2400L, "I", Category.BAG));
        LOCAL_MEMORY_DB.add(Product.of(1700L, "I", Category.HAT));
        LOCAL_MEMORY_DB.add(Product.of(1700L, "I", Category.SOCKS));
        LOCAL_MEMORY_DB.add(Product.of(2400L, "I", Category.ACCESSORY));
    }
}
