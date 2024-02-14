package dev.spearkkk.untitle01.controller.product.dto;

import lombok.*;

import java.util.List;

@ToString
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LowestAndHighestResponse {
    private String category;
    private List<BrandToPrice> lowest;
    private List<BrandToPrice> highest;

    @ToString
    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class BrandToPrice {
        private String brand;
        private Long price;
    }
}
