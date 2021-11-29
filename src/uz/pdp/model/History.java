package uz.pdp.model;

import java.util.UUID;

public class History extends BaseModel{
    private UUID userId;
    private double price;
    private double discount;
    private int count;

    public History() {
    }

    public History(String name, UUID userId, double price, double discount, int count) {
        super(name);
        this.userId = userId;
        this.price = price;
        this.discount = discount;
        this.count = count;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
