package uz.pdp.model;

import java.util.UUID;

public class Basket extends BaseModel{
    private double price;
    private double discount;
    private int count;

    public Basket() {
    }

    public Basket(String name, double price, double discount, int count) {
        super(name);
        this.price = price;
        this.discount = discount;
        this.count = count;
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
