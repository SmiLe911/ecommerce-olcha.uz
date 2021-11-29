package uz.pdp;

import uz.pdp.model.*;
import uz.pdp.service.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class Main implements Response{

    public static void main(String[] args) {
        System.out.println("olcha.uz\t+998 (71) 202 2021\n");

        Scanner scannerInt = new Scanner(System.in);
        Scanner scannerStr = new Scanner(System.in);
        Scanner scannerDouble = new Scanner(System.in);

        UserService userService = new UserService();
        CategoryService categoryService = new CategoryService();
        ProductService productService = new ProductService();
        HistoryService historyService = new HistoryService();

        int stepCode = 1;
        while (stepCode != 0){
            System.out.println("[1. SIGN IN]\t[2. SIGN UP]\t[3. LOGIN AS A GUEST]\t[0. EXIT]");
            stepCode = scannerInt.nextInt();
            switch (stepCode){
                case 1:
                    signIn(userService, scannerStr, scannerInt, scannerDouble, categoryService, productService, historyService);
                    break;
                case 2:
                    signUp(userService, scannerStr, scannerInt);
                    break;
                case 3:
                    guest(userService, scannerStr, scannerInt, scannerDouble, categoryService, productService);
                    break;
            }
        }
    }

    public static void signIn(UserService userService, Scanner scannerStr, Scanner scannerInt, Scanner scannerDouble, CategoryService categoryService, ProductService productService, HistoryService historyService) {
        System.out.println(ENTER_PHONE_NUMBER);
        String phoneNumber = scannerStr.nextLine();
        System.out.println(ENTER_PASSWORD);
        String password = scannerStr.nextLine();
        User user = userService.check(phoneNumber, password);
        if(user == null)
            System.out.println(WRONG_PHONE_NUMBER_OR_PASSWORD);
        else if(user.getRole().equals(Role.ADMIN))
            menuAdmin(scannerStr, scannerInt, scannerDouble, categoryService, productService);
        else
            menuUser(user, userService, scannerStr, scannerInt, scannerDouble, categoryService, productService, historyService);
    }

    public static void signUp(UserService userService, Scanner scannerStr, Scanner scannerInt) {
        System.out.println(ENTER_NAME);
        String name = scannerStr.nextLine();
        System.out.println(ENTER_PHONE_NUMBER);
        String phoneNumber = scannerStr.nextLine();
        User user = userService.check(phoneNumber);
        if(user == null) {
            int smsCode = userService.sendSmsCode();
            System.out.println(ENTER_SMS_CODE +": " + smsCode);
            int enteredSmsCode = scannerInt.nextInt();
            if(smsCode == enteredSmsCode) {
                System.out.println(CREATE_PASSWORD);
                String password = scannerStr.nextLine();
                if(userService.add(new User(name, phoneNumber, password, smsCode, Role.USER)))
                    System.out.println(USER_SUCCESSFULLY_ADDED);
            }
            else
                System.out.println(WRONG_SMS_CODE);
        }
        else
            System.out.println(USER_ALREADY_EXISTS);
    }

    public static void menuAdmin(Scanner scannerStr, Scanner scannerInt, Scanner scannerDouble, CategoryService categoryService, ProductService productService) {
        int stepCode = 1;
        while (stepCode != 0){
            System.out.println("[1. SEE CATALOG]\t[2. SEE BALANCE]\t[3. ADD CATEGORY]\t[4. ADD PRODUCT]\t[0. EXIT]");
            stepCode = scannerInt.nextInt();
            switch (stepCode){
                case 1:
                    catalog(categoryService, productService, scannerInt);
                    break;
                case 2:
                    System.out.println(Owner.getOwnerBalance());
                    break;
                case 3:
                    addCategory(categoryService, scannerStr, scannerInt);
                    break;
                case 4:
                    addProduct(categoryService, productService, scannerStr, scannerInt, scannerDouble);
                    break;
            }
        }
    }

    public static void catalog(CategoryService categoryService, ProductService productService, Scanner scannerInt) {
        int stepCode = 1;
        while (stepCode != 0) {
            List<Category> categories = categoryService.list(UUID.fromString("00000000-0000-0000-0000-000000000000"));
            if (categories.size() != 0) {
                for (int i = 0; i < categories.size(); i++)
                    System.out.println((i + 1) + ". " + categories.get(i).getName());
                System.out.println("[0. EXIT]");
                stepCode = scannerInt.nextInt();
                if(stepCode == 0)
                    break;
                else if(stepCode > 0 && stepCode <= categories.size()) {
                    while (stepCode != 0) {
                        UUID categoryId = categories.get(stepCode - 1).getId();
                        categories = categoryService.list(categories.get(stepCode - 1).getId());
                        for (int i = 0; i < categories.size(); i++)
                            System.out.println((i + 1) + ". " + categories.get(i).getName());
                        if(categories.size() == 0){
                            List<Product> products = productService.list(categoryId);
                            if(products.size() != 0){
                                for(int i = 0; i < products.size(); i++) {
                                    System.out.println((i + 1) + ". " + products.get(i).getName());
                                    System.out.println("Product price: " + products.get(i).getPrice());
                                    System.out.println("Product discount: " + products.get(i).getDiscount());
                                    System.out.println("Product description: " + products.get(i).getDescription());
                                }
                            }
                            else{
                                System.out.println(EMPTY);
                                break;
                            }
                        }
                        System.out.println("[0. EXIT]");
                        stepCode = scannerInt.nextInt();
                        if(stepCode < 0 || stepCode > categories.size()){
                            System.out.println(WRONG_COMMAND);
                            break;
                        }
                    }
                }
                else {
                    System.out.println(CATEGORY_NOT_FOUND);
                    break;
                }
            }
            else {
                System.out.println(THERE_IS_NO_CATEGORY);
                break;
            }
        }
    }

    public static void addCategory(CategoryService categoryService, Scanner scannerStr, Scanner scannerInt){
        List<Category> categories = categoryService.list(UUID.fromString("00000000-0000-0000-0000-000000000000"));
        if (categories.size() != 0) {
            for (int i = 0; i < categories.size(); i++)
                System.out.println((i + 1) + ". " + categories.get(i).getName());
        }
        else
            System.out.println(EMPTY);
        int stepCode = 1;
        UUID categoryId = UUID.fromString("00000000-0000-0000-0000-000000000000");
        while (stepCode != 0) {
            System.out.println("[1. CONTINUE]\t[2. ADD CATEGORY]\t[0. EXIT]");
            stepCode = scannerInt.nextInt();
            switch (stepCode){
                case 1:
                    System.out.println(CHOOSE_CATEGORY);
                    int stepCode1 = scannerInt.nextInt();
                    if(stepCode1 > 0 && stepCode1 <= categories.size()){
                        categoryId = categories.get(stepCode1 - 1).getId();
                        categories = categoryService.list(categoryId);
                        for(int i = 0; i < categories.size(); i++)
                            System.out.println((i + 1) + ". " + categories.get(i).getName());
                        if(categories.size() == 0)
                            System.out.println(EMPTY);
                    }
                    break;
                case 2:
                    System.out.println(ENTER_CATEGORY_NAME);
                    String categoryName = scannerStr.nextLine();
                    Category category = categoryService.check(categoryName);
                    if(category == null){
                        if(categoryService.add(new Category(categoryName, categoryId)))
                            System.out.println(CATEGORY_SUCCESSFULLY_ADDED);
                    }
                    else
                        System.out.println(CATEGORY_ALREADY_EXISTS);
                    stepCode = 0;
                    break;
                case 0:
                    break;
                default:
                    System.out.println(WRONG_COMMAND);
            }
        }
    }

    public static void addProduct(CategoryService categoryService, ProductService productService, Scanner scannerStr, Scanner scannerInt, Scanner scannerDouble){
        int stepCode = 1;
        while (stepCode != 0) {
            List<Category> categories = categoryService.list(UUID.fromString("00000000-0000-0000-0000-000000000000"));
            if (categories.size() != 0) {
                for (int i = 0; i < categories.size(); i++)
                    System.out.println((i + 1) + ". " + categories.get(i).getName());
                System.out.println("[0. EXIT]");
                stepCode = scannerInt.nextInt();
                if(stepCode == 0)
                    break;
                else if(stepCode > 0 && stepCode <= categories.size()) {
                    while (stepCode != 0) {
                        UUID categoryId = categories.get(stepCode - 1).getId();
                        categories = categoryService.list(categories.get(stepCode - 1).getId());
                        for (int i = 0; i < categories.size(); i++)
                            System.out.println((i + 1) + ". " + categories.get(i).getName());
                        if(categories.size() == 0){
                            List<Product> products = productService.list(categoryId);
                            for(int i = 0; i < products.size(); i++) {
                                System.out.println((i + 1) + ". " + products.get(i).getName());
                                System.out.println("Product price: " + products.get(i).getPrice());
                                System.out.println("Product discount: " + products.get(i).getDiscount());
                                System.out.println("Product description: " + products.get(i).getDescription());
                            }
                            if(products.size() == 0)
                                System.out.println(EMPTY);
                            System.out.println("[1. ADD PRODUCT]\t[0. EXIT]");
                            stepCode = scannerInt.nextInt();
                            if(stepCode == 1) {
                                System.out.println(ENTER_PRODUCT_NAME);
                                String productName = scannerStr.nextLine();
                                System.out.println(ENTER_PRODUCT_PRICE);
                                double productPrice = scannerDouble.nextDouble();
                                System.out.println(ENTER_PRODUCT_DISCOUNT);
                                double productDiscount = scannerDouble.nextDouble();
                                System.out.println(ENTER_PRODUCT_DESCRIPTION);
                                String productDescription = scannerStr.nextLine();
                                Product product = productService.check(productName);
                                if(product == null) {
                                    if(productService.add(new Product(productName, categoryId, productPrice, productDiscount, productDescription)))
                                        System.out.println(PRODUCT_SUCCESSFULLY_ADDED);
                                    break;
                                }
                                else {
                                    System.out.println(PRODUCT_ALREADY_EXISTS);
                                    break;
                                }
                            }
                            else if(stepCode == 0) {
                                break;
                            }
                            else {
                                System.out.println(WRONG_COMMAND);
                                break;
                            }
                        }
                        System.out.println("[0. EXIT]");
                        stepCode = scannerInt.nextInt();
                        if(stepCode < 0 || stepCode > categories.size()){
                            System.out.println(CATEGORY_NOT_FOUND);
                            break;
                        }
                    }
                }
                else {
                    System.out.println(CATEGORY_NOT_FOUND);
                    break;
                }
            }
            else {
                System.out.println(THERE_IS_NO_CATEGORY);
                break;
            }
            break;
        }
    }

    public static void guest(UserService userService, Scanner scannerStr, Scanner scannerInt, Scanner scannerDouble, CategoryService categoryService, ProductService productService) {
        BasketService basketService = new BasketService();
        int stepCode1 = 1;
        while (stepCode1 != 0) {
            System.out.println("[1. SEE CATALOG]\t[2. SEE BASKET]\t[0. EXIT]");
            stepCode1 = scannerInt.nextInt();
            if(stepCode1 == 1) {
                int stepCode = 1;
                while (stepCode != 0) {
                    List<Category> categories = categoryService.list(UUID.fromString("00000000-0000-0000-0000-000000000000"));
                    if (categories.size() != 0) {
                        for (int i = 0; i < categories.size(); i++)
                            System.out.println((i + 1) + ". " + categories.get(i).getName());
                        System.out.println("[0. EXIT]");
                        stepCode = scannerInt.nextInt();
                        if(stepCode == 0)
                            break;
                        else if(stepCode > 0 && stepCode <= categories.size()) {
                            while (stepCode != 0) {
                                UUID categoryId = categories.get(stepCode - 1).getId();
                                categories = categoryService.list(categories.get(stepCode - 1).getId());
                                for (int i = 0; i < categories.size(); i++)
                                    System.out.println((i + 1) + ". " + categories.get(i).getName());
                                if(categories.size() == 0){
                                    List<Product> products = productService.list(categoryId);
                                    if(products.size() != 0){
                                        for(int i = 0; i < products.size(); i++) {
                                            System.out.println((i + 1) + ". " + products.get(i).getName());
                                            System.out.println("Product price: " + products.get(i).getPrice());
                                            System.out.println("Product discount: " + products.get(i).getDiscount());
                                            System.out.println("Product description: " + products.get(i).getDescription());
                                        }
                                        System.out.println("[1. ADD TO BASKET]\t[2. BUY IN ONE CLICK]\t[0. EXIT]");
                                        stepCode = scannerInt.nextInt();
                                        if(stepCode == 1) {
                                            System.out.println(CHOOSE_PRODUCT);
                                            stepCode = scannerInt.nextInt();
                                            if(stepCode > 0 && stepCode <= products.size()) {
                                                System.out.println(ENTER_COUNT);
                                                int count = scannerInt.nextInt();
                                                if(basketService.add(new Basket(products.get(stepCode - 1).getName(), products.get(stepCode - 1).getPrice(), products.get(stepCode - 1).getDiscount(), count)))
                                                    System.out.println(PRODUCT_SUCCESSFULLY_ADDED_TO_BASKET);
                                                break;
                                            }
                                            else {
                                                System.out.println(PRODUCT_NOT_FOUND);
                                                break;
                                            }
                                        }
                                        else if (stepCode == 2) {
                                            System.out.println(CHOOSE_PRODUCT);
                                            stepCode = scannerInt.nextInt();
                                            if(stepCode > 0 && stepCode <= products.size()) {
                                                System.out.println(ENTER_PHONE_NUMBER);
                                                String phoneNumber = scannerStr.nextLine();
                                                System.out.println(PRODUCT_SUCCESSFULLY_FORMALIZED);
                                                Owner.setOwnerBalance(Owner.getOwnerBalance() + products.get(stepCode - 1).getPrice() *((100 - products.get(stepCode - 1).getDiscount()) / 100));
                                            }
                                            else
                                                System.out.println(PRODUCT_NOT_FOUND);
                                        }
                                        else if (stepCode == 0) {
                                            break;
                                        }
                                        else {
                                            System.out.println(WRONG_COMMAND);
                                            break;
                                        }
                                    }
                                    else{
                                        System.out.println(EMPTY);
                                        break;
                                    }
                                }
                                System.out.println("[0. EXIT]");
                                stepCode = scannerInt.nextInt();
                                if(stepCode < 0 || stepCode > categories.size()){
                                    System.out.println(WRONG_COMMAND);
                                    break;
                                }
                            }
                        }
                        else {
                            System.out.println(CATEGORY_NOT_FOUND);
                            break;
                        }
                    }
                    else {
                        System.out.println(THERE_IS_NO_CATEGORY);
                        break;
                    }
                }
            }
            else if(stepCode1 == 2) {
                List<Basket> basketList = basketService.list(UUID.fromString("00000000-0000-0000-0000-000000000000"));
                if(basketList.size() != 0){
                    double total = 0;
                    for(int i = 0; i < basketList.size(); i++) {
                        System.out.println((i + 1) + ". " + basketList.get(i).getName() + " | " + basketList.get(i).getPrice() + " | " + basketList.get(i).getDiscount() + " | " + basketList.get(i).getCount());
                        total += basketList.get(i).getCount()*basketList.get(i).getPrice()*((100 - basketList.get(i).getDiscount()) / 100);
                    }
                    System.out.println("*** TOTAL: " + total + " ***");
                    System.out.println("[1. BUY]\t[0. EXIT]");
                    stepCode1 = scannerInt.nextInt();
                    if(stepCode1 == 1) {
                        System.out.println(ENTER_PHONE_NUMBER);
                        String phoneNumber = scannerStr.nextLine();
                        Owner.setOwnerBalance(Owner.getOwnerBalance() + total);
                        System.out.println(PRODUCT_SUCCESSFULLY_FORMALIZED);
                    }
                    else if(stepCode1 == 0) {
                        break;
                    }
                    else {
                        System.out.println(WRONG_COMMAND);
                        break;
                    }
                }
                else {
                    System.out.println(EMPTY);
                }
            }
            else if(stepCode1 == 0) {
                break;
            }
            else {
                System.out.println(WRONG_COMMAND);
                break;
            }
        }
    }

    public static void menuUser(User user, UserService userService, Scanner scannerStr, Scanner scannerInt, Scanner scannerDouble, CategoryService categoryService, ProductService productService, HistoryService historyService){
        BasketService basketService = new BasketService();
        int stepCode1 = 1;
        while (stepCode1 != 0) {
            System.out.println("[1. SEE CATALOG]\t[2. SEE BASKET]\t.[3. SEE HISTORY]\t[0. EXIT]");
            stepCode1 = scannerInt.nextInt();
            if(stepCode1 == 1) {
                int stepCode = 1;
                while (stepCode != 0) {
                    List<Category> categories = categoryService.list(UUID.fromString("00000000-0000-0000-0000-000000000000"));
                    if (categories.size() != 0) {
                        for (int i = 0; i < categories.size(); i++)
                            System.out.println((i + 1) + ". " + categories.get(i).getName());
                        System.out.println("[0. EXIT]");
                        stepCode = scannerInt.nextInt();
                        if(stepCode == 0)
                            break;
                        else if(stepCode > 0 && stepCode <= categories.size()) {
                            while (stepCode != 0) {
                                UUID categoryId = categories.get(stepCode - 1).getId();
                                categories = categoryService.list(categories.get(stepCode - 1).getId());
                                for (int i = 0; i < categories.size(); i++)
                                    System.out.println((i + 1) + ". " + categories.get(i).getName());
                                if(categories.size() == 0){
                                    List<Product> products = productService.list(categoryId);
                                    if(products.size() != 0){
                                        for(int i = 0; i < products.size(); i++) {
                                            System.out.println((i + 1) + ". " + products.get(i).getName());
                                            System.out.println("Product price: " + products.get(i).getPrice());
                                            System.out.println("Product discount: " + products.get(i).getDiscount());
                                            System.out.println("Product description: " + products.get(i).getDescription());
                                        }
                                        System.out.println("[1. ADD TO BASKET]\t[2. BUY IN ONE CLICK]\t[0. EXIT]");
                                        stepCode = scannerInt.nextInt();
                                        if(stepCode == 1) {
                                            System.out.println(CHOOSE_PRODUCT);
                                            stepCode = scannerInt.nextInt();
                                            if(stepCode > 0 && stepCode <= products.size()) {
                                                System.out.println(ENTER_COUNT);
                                                int count = scannerInt.nextInt();
                                                if(basketService.add(new Basket(products.get(stepCode - 1).getName(), products.get(stepCode - 1).getPrice(), products.get(stepCode - 1).getDiscount(), count)))
                                                    System.out.println(PRODUCT_SUCCESSFULLY_ADDED_TO_BASKET);
                                                break;
                                            }
                                            else {
                                                System.out.println(PRODUCT_NOT_FOUND);
                                                break;
                                            }
                                        }
                                        else if (stepCode == 2) {
                                            System.out.println(CHOOSE_PRODUCT);
                                            stepCode = scannerInt.nextInt();
                                            if(stepCode > 0 && stepCode <= products.size()) {
                                                boolean operation = historyService.add(new History(products.get(stepCode - 1).getName(), user.getId(), products.get(stepCode - 1).getPrice(), products.get(stepCode - 1).getDiscount(), 1));
                                                System.out.println(PRODUCT_SUCCESSFULLY_FORMALIZED);
                                                Owner.setOwnerBalance(Owner.getOwnerBalance() + products.get(stepCode - 1).getPrice() *((100 - products.get(stepCode - 1).getDiscount()) / 100));
                                            }
                                            else
                                                System.out.println(PRODUCT_NOT_FOUND);
                                        }
                                        else if (stepCode == 0) {
                                            break;
                                        }
                                        else {
                                            System.out.println(WRONG_COMMAND);
                                            break;
                                        }
                                    }
                                    else{
                                        System.out.println(EMPTY);
                                        break;
                                    }
                                }
                                System.out.println("[0. EXIT]");
                                stepCode = scannerInt.nextInt();
                                if(stepCode < 0 || stepCode > categories.size()){
                                    System.out.println(WRONG_COMMAND);
                                    break;
                                }
                            }
                        }
                        else {
                            System.out.println(CATEGORY_NOT_FOUND);
                            break;
                        }
                    }
                    else {
                        System.out.println(THERE_IS_NO_CATEGORY);
                        break;
                    }
                }
            }
            else if(stepCode1 == 2) {
                List<Basket> basketList = basketService.list(UUID.fromString("00000000-0000-0000-0000-000000000000"));
                if(basketList.size() != 0){
                    double total = 0;
                    for(int i = 0; i < basketList.size(); i++) {
                        System.out.println((i + 1) + ". " + basketList.get(i).getName() + " | " + basketList.get(i).getPrice() + " | " + basketList.get(i).getDiscount() + " | " + basketList.get(i).getCount());
                        total += basketList.get(i).getCount()*basketList.get(i).getPrice()*((100 - basketList.get(i).getDiscount()) / 100);
                    }
                    System.out.println("*** TOTAL: " + total + " ***");
                    System.out.println("[1. BUY]\t[0. EXIT]");
                    stepCode1 = scannerInt.nextInt();
                    if(stepCode1 == 1) {
                        for(int i = 0; i < basketList.size(); i++) {
                            boolean operation = historyService.add(new History(basketList.get(i).getName(), user.getId(), basketList.get(i).getPrice(), basketList.get(i).getDiscount(), basketList.get(i).getCount()));
                        }
                        Owner.setOwnerBalance(Owner.getOwnerBalance() + total);
                        System.out.println(PRODUCT_SUCCESSFULLY_FORMALIZED);
                    }
                    else if(stepCode1 == 0) {
                        break;
                    }
                    else {
                        System.out.println(WRONG_COMMAND);
                        break;
                    }
                }
                else {
                    System.out.println(EMPTY);
                }
            }
            else if(stepCode1 == 3) {
                List<History> histories = historyService.list(user.getId());
                if(histories.size() != 0) {
                    for(int i = 0; i < histories.size(); i++) {
                        System.out.println((i + 1) + ". " + histories.get(i).getName() + " | " + histories.get(i).getPrice() + " | " + histories.get(i).getDiscount() + " | " + histories.get(i).getCount());
                    }
                }
                else {
                    System.out.println(EMPTY);
                }
            }
            else if(stepCode1 == 0) {
                break;
            }
            else {
                System.out.println(WRONG_COMMAND);
                break;
            }
        }
    }
}
