package dev.spearkkk.untitle01.controller.product.dto;

import lombok.*;

@ToString
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductResponse {
    private String category;
    private String brand;
    private Long price;
}
