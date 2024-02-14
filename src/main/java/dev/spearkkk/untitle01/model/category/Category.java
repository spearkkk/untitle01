package dev.spearkkk.untitle01.model.category;

public enum Category {
    TOP("상위"),
    OUTER("아우터"),
    BOTTOM("바지"),
    SNEAKERS("스니커즈"),
    BAG("가방"),
    HAT("모자"),
    SOCKS("양말"),
    ACCESSORY("액세서리");

    private final String name;

    Category(String name) {
        this.name = name;
    }
}
