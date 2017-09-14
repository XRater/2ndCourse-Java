package ru.spbau.mit.kirakosian.p170914;

@SuppressWarnings("NonAsciiCharacters")
public class Bool {

    public static void main(String[] args) {

        int m = 0;
        int n = 1;

        boolean b = Boolean.parseBoolean("false");
        System.out  .println("Приает");

        Псина псина = new Псина();
        псина.ЛАЙ();

        int a = Integer.MAX_VALUE;
        System.out.println(a + 1);

        double f = 1.0;

        f /= 0;

        System.out.println(f);

        byte bb = 1;
//        bb = bb + bb;
        boxing(5);
        Integer I = 5;
        unboxing(I);
    }

    static void unboxing(int a) {

    }

    static void boxing(Integer a) {

    }

    static class Псина {

        void ЛАЙ() {
            System.out.println("Гав!");
        }
    }

}
