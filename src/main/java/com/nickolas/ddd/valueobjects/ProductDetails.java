package com.nickolas.ddd.valueobjects;

public final class ProductDetails {
    private final String name;
    private final String description;
    private final Dimensions dimensions;

    public ProductDetails(String name, String description, Dimensions dimensions) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }
        this.name = name;
        this.description = description;
        this.dimensions = dimensions;
    }

    public String combineDescriptions(String additionalDescription) {
        return this.description + " " + additionalDescription;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Dimensions getDimensions() {
        return dimensions;
    }
}
