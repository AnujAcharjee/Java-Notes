// Fields, Methods and Constructors

// Methods are not copied into every object
// When Java creates an object, the object only gets reference to the method
// methods exectutable code is loaded seprately by JVM in memory and is shared across all objects

// What happen when we create a object?
// 1. object memory is allocated in heap
// 2. all static variables are initialised
// 3. constructor is called
// 4. object reference is returned

public class Two {

    public static void main(String[] args) {
        Student s1 = new Student("Anuj", 21);

        // s1.name = "Anuj";
        // s1.age = 21;

        // Student s2 = new Student();

        // s2.name = "Anuj";
        // s2.age = 21;

        System.out.println(s1.name);
        System.out.println(s1.age);
        System.out.println(s1.count);
        System.out.println(s1.TOTAL_SCORE);
    }
}

class Student {
    // Instance variables - unique to each object
    String name;
    int age;
    // Static variables - shared across all objects
    static int count;
    // Final variables - cat be re initialised
    final int TOTAL_SCORE = 90;

    Student(String name, int age) {
        this.name = name;
        this.age = age;
        this.count++;
    }

    // method
    void study() {
        System.out.println(name + " is studying");
    }

}
