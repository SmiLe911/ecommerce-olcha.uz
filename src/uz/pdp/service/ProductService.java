package uz.pdp.service;

import uz.pdp.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProductService implements BaseService<String, Product, List<Product>> {
    List<Product> productList = new ArrayList<>();

    @Override
    public boolean add(Product product) {
        productList.add(product);
        return true;
    }

    @Override
    public Product check(String productName) {
        for (Product product: productList) {
            if(product.getName().equals(productName))
                return product;
        }
        return null;
    }

    @Override
    public Product check(String t1, String t2) {
        return null;
    }

    @Override
    public List<Product> list(UUID id) {
        List<Product> products = new ArrayList<>();
        for(Product product: productList){
            if(product.getCategoryId().equals(id))
                products.add(product);
        }
        return products;
    }
}
