package com.vitiwari.References;

@FunctionalInterface
interface A{
    void show();
}

@FunctionalInterface
interface B{
    int add(int i, int j);
}
// There are three types of interfaces - normal, functional and marker
// normal can have any two or more number of functions to be implemented
// functional interface have only one method
// marker interface don't have any method it is only used to talk to compiler
// for eg Serializer which is used to give authority to a class to save data on disk

// We can use lambda expression using functional interface. This is the demonstration for the same.
public class FunInterface {
    public static void main(String[] args) {
        A obj = new A(){
            public void show(){
                System.out.println("In the show");
            }
        };
        obj.show(); // In the show
        // The same can be replaced by the lambda

        A obj2 = () -> System.out.println("In the show");
        A obj3 = () -> {
            System.out.println("In the show");
            System.out.println("Also enjoying");
        };
        // In case of multiple statements

        B obj4 = (int i, int j) -> {return i+j;};
        // This one can be even more simplified
        B obj5 = (i,j) -> i+j;
        // for single statement it will automatically return

        B obj6 = Integer::sum;
        // lambdas can also be replaced with method references
        obj2.show();
        obj3.show();
        System.out.println(obj4.add(1, 2));
        System.out.println(obj5.add(1, 2));
        System.out.println(obj6.add(1, 2));

    }
}
