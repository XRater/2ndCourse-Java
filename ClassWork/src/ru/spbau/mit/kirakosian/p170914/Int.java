package ru.spbau.mit.kirakosian.p170914;

public class Int {

    public static void main(String[] args) {

        String s1 = "Hello";
        s1.intern();

        Integer a = 1;
        Integer b = 1;

        System.out.println(a == b);

        Integer a1 = 1000;
        Integer b1 = 1000;

        System.out.println(a1 == b1);

        String str = "Hello";

        str.chars();
//        str.c
    }

}
