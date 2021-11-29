package uz.pdp.service;

import uz.pdp.model.Basket;
import uz.pdp.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BasketService implements BaseService<String, Basket, List<Basket>>{
    List<Basket> basketList = new ArrayList<>();

    @Override
    public boolean add(Basket basket) {
        basketList.add(basket);
        return true;
    }

    @Override
    public Basket check(String s) {
        return null;
    }

    @Override
    public Basket check(String t1, String t2) {
        return null;
    }

    @Override
    public List<Basket> list(UUID id) {
        List<Basket> baskets = new ArrayList<>();
        for(Basket basket: basketList)
            if(id.equals(UUID.fromString("00000000-0000-0000-0000-000000000000")))
                baskets.add(basket);
        return baskets;
    }
}
