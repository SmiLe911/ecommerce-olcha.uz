package uz.pdp.service;

import java.util.UUID;

public interface BaseService<T, R, RL> { // T -> UUID or String, R -> Object, RL -> ListOfObjects
    boolean add(R r);
    R check(T t);
    R check(T t1, T t2);
    RL list(UUID id);
}
