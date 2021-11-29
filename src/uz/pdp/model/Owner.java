package uz.pdp.model;

public class Owner {
    private static double ownerBalance;

    public static double getOwnerBalance() {
        return ownerBalance;
    }

    public static void setOwnerBalance(double ownerBalance) {
        Owner.ownerBalance = ownerBalance;
    }
}
