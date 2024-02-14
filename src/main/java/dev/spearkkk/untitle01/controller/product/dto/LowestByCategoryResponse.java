package dev.spearkkk.untitle01.controller.product.dto;

import lombok.*;

import java.util.List;

@ToString
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LowestByCategoryResponse {
    private Long totalSum;
    private List<ProductResponse> products;
}
