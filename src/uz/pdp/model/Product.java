package uz.pdp.model;

import java.util.UUID;

public class Product extends BaseModel{
    private UUID categoryId;
    private double price;
    private double discount;
    private String description;

    public Product() {
    }

    public Product(String name, UUID categoryId, double price, double discount, String description) {
        super(name);
        this.categoryId = categoryId;
        this.price = price;
        this.discount = discount;
        this.description = description;
    }

    public UUID getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(UUID categoryId) {
        this.categoryId = categoryId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
