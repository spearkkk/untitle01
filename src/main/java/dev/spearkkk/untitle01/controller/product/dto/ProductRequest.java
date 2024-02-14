package dev.spearkkk.untitle01.controller.product.dto;

import lombok.*;

@ToString
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductRequest {
    private String category;
    private Long price;
}
