package dev.spearkkk.untitle01.model.product;

import dev.spearkkk.untitle01.model.category.Category;
import lombok.*;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE, staticName = "of")
@EqualsAndHashCode(of = {"brand", "category"})
public class Product {
    private Long price;
    private String brand;
    private Category category;
}
