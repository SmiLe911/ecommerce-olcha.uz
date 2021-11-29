package uz.pdp.service;

import uz.pdp.Role;
import uz.pdp.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserService implements BaseService<String, User, List<User>> {
    List<User> userList = new ArrayList<>();

    public UserService() {
        this.userList.add(new User("Admin", "907009911", "123", 10000, Role.ADMIN));
    }

    @Override
    public boolean add(User user) {
        userList.add(user);
        return true;
    }

    @Override
    public User check(String phoneNumber) {
        for (User user: userList) {
            if(user.getPhoneNumber().equals(phoneNumber))
                return user;
        }
        return null;
    }

    @Override
    public User check(String phoneNumber, String password) {
        for (User user: userList) {
            if(user.getPhoneNumber().equals(phoneNumber) && user.getPassword().equals(password))
                return user;
        }
        return null;
    }

    @Override
    public List<User> list(UUID id) {
        return null;
    }

    public int sendSmsCode(){
        return (int) (Math.random() * 90000 + 10000);
    }
}
