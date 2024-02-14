package dev.spearkkk.untitle01.controller.product.dto;

import lombok.*;

import java.util.List;

@ToString
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LowestByBrandResponse {
    private InnerResponse lowest;

    @ToString
    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class InnerResponse {
        private Long totalSum;
        private String brand;
        private List<CategoryToPrice> categories;
    }

    @ToString
    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CategoryToPrice {
        private String category;
        private Long price;
    }
}
