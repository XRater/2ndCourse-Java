package ru.spbau.mit.kirakosian;

import org.junit.Test;
import ru.spbau.mit.kirakosian.simple_functions.*;

import static org.junit.jupiter.api.Assertions.*;

public class Function2Test {

    @Test
    public void testFunction2ApplySum() {
        final Sum sum = new Sum();
        assertEquals((Integer) 1, sum.apply(0, 1));
        assertEquals((Integer) 0, sum.apply(0, 0));
    }

    @Test
    public void testFunction2ApplyConstII() {
        final Const<Integer, Integer> constII = new Const<>();
        assertEquals((Integer) 1, constII.apply(1, 2));
        assertEquals((Integer) 0, constII.apply(0, null));
    }

    @Test
    public void testFunction2ApplyBaseSB() {
        final Const<String, Base> constSB = new Const<>();
        assertEquals("hello", constSB.apply("hello", new Base()));
        assertEquals(null, constSB.apply(null, new Derived()));
    }

    @Test
    public void testFunction2ComposeConstConst42() {
        final Const42 const42 = new Const42();
        final Const<String, String> constSS = new Const<>();
        //noinspection unchecked
        assertEquals(42, constSS.compose(const42).apply("hello", null));
    }

    @Test
    public void testComposeSumNegate() {
        final Sum sum = new Sum();
        final Negate neg = new Negate();
        assertEquals((Integer) 0, sum.compose(neg).apply(-1, 1));
        assertEquals((Integer) 1, sum.compose(neg).apply(1, -2));
    }

    @Test
    public void testComposeBaseDerived() {
        final Const<Base, Object> constB = new Const<>();
        final Const<Derived, Object> constD = new Const<>();
        final Id<Base> idB = new Id<>();
        final Id<Derived> idD = new Id<>();
        constB.compose(idB).apply(new Base(), "hello");
        constB.compose(idB).apply(new Derived(), "hello");
        constD.compose(idB).apply(new Derived(), "hello");
        constD.compose(idD).apply(new Derived(), "hello");
    }

    @Test
    public void testBindSum() {
        final Sum sum = new Sum();
        final Function1<Integer, Integer> add2Left = sum.bind1(2);
        final Function1<Integer, Integer> add2Right = sum.bind2(2);
        assertEquals((Integer) 3, add2Left.apply(1));
        assertEquals((Integer) 3, add2Right.apply(1));
    }

    @Test
    public void testBind1Const() {
        final Const<String, String> constSS = new Const<>();
        final Function1<String, String> constHI = constSS.bind1("HI");
        assertEquals("HI", constHI.apply("one"));
        assertEquals("HI", constHI.apply(null));
    }

    @Test
    public void testBind2Const() {
        final Const<String, String> constSS = new Const<>();
        final Function1<String, String> idOne = constSS.bind2("one");
        final Function1<String, String> idNull = constSS.bind2("one");
        assertEquals("HI", idOne.apply("HI"));
        assertEquals("Hello", idOne.apply("Hello"));
        assertEquals("Hi", idNull.apply("Hi"));
        assertEquals("Hello", idNull.apply("Hello"));
    }

    @Test
    public void testCurrySum() {
        final Sum sum = new Sum();
        assertEquals((Integer) 3, sum.curry().apply(1).apply(2));
        assertEquals((Integer) 3, sum.curry().apply(2).apply(1));
    }

    @Test
    public void testCurryOrder() {
        final Const<String, String> constSS = new Const<>();
        assertEquals("Hi", constSS.curry().apply("Hi").apply("other"));
        assertNotEquals("other", constSS.curry().apply("Hi").apply("other"));
    }

    private static class Base {}

    private static class Derived extends Base {}

}