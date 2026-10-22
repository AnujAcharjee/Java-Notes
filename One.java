// Java OOP, CLasses and Objects

// file and public class name must be same - for complier to compile
// class is just a blueprint - we can create multiple objects from a class
// objects are instances of a class
// objects are created in heap memory

public class One {
    public static void main(String[] args) {
        System.out.println("Hello World");

        Car car1 = new Car();
        Car car2 = new Car();
        Car car3 = new Car();

        car1.name = "Audi";
        car1.brand = "Audi";
        car1.price = 5000000;
        car1.year = 2024;
        car1.color = "Red";

        car2.name = "BMW";

        car3.name = "Mercedes";
    
        System.out.println(car1.name);
        System.out.println(car1.brand);
        System.out.println(car1.price);
        System.out.println(car1.year);
        System.out.println(car1.color);
    }
}

class Car{
    String name;
    String brand;
    int price;
    int year;
    String color;
}