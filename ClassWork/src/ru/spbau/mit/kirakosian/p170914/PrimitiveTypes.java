package ru.spbau.mit.kirakosian.p170914;

public class PrimitiveTypes {

    /*
        void
        boolean
        byte
        int
        short
        long
        char
        float
        double
     */

    static class Dog {

        String name;

        public Dog(String name) {
            this.name = name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    private static void pointers() {
        Dog thisDog = new Dog("Sparky");
        Dog thatDog = thisDog;
        thatDog.setName("Wolfie");
        System.out.println(thatDog.getName());
        System.out.println(thisDog.getName());
    }

    public static void main(String[] args) {
        pointers();
        Dog dog = new Dog("Sparky");
        System.out.println(dog);
    }
}
