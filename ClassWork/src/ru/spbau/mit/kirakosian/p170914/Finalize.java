package ru.spbau.mit.kirakosian.p170914;

public class Finalize {

    public static final int TIMES = 10000;
    static A x;

    public static void main(String[] args) {
        createA();

        int sum = 0;
        for (Integer a = 0; a < TIMES; a++) {
            for (Integer b = 0; b < TIMES; b++)
                sum += TIMES * a + b;
        }

        x = null;
        createB();

        for (Integer a = 0; a < TIMES; a++) {
            for (Integer b = 0; b < TIMES; b++)
                sum += TIMES * a + b;
        }
        System.out.println(sum);
        x.voice();
    }

    private static void createB() {
        B b = new B();
        b.voice();
    }

    private static void createA() {
        A a = new A();
        a.voice();
    }

    static class A {

        @Override
        protected void finalize() {
            x = this;
            System.out.println("I am dying");
        }

        public void voice() {
            System.out.println("I am A");
        }
    }

    static class B {

        public void voice() {
            System.out.println("I am B");
        }

        @Override
        protected void finalize() {
            System.out.println("Me too!");
        }
    }
}
