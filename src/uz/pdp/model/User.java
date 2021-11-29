package uz.pdp.model;

import uz.pdp.Role;

public class User extends BaseModel{
    private String phoneNumber;
    private String password;
    private int smsCode;
    private Role role;

    public User() {
    }

    public User(String name, String phoneNumber, String password, int smsCode, Role role) {
        super(name);
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.smsCode = smsCode;
        this.role = role;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(int smsCode) {
        this.smsCode = smsCode;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
