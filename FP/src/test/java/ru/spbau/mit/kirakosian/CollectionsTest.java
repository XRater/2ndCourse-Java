package ru.spbau.mit.kirakosian;

import org.junit.Test;
import ru.spbau.mit.kirakosian.simple_functions.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

@SuppressWarnings("unused")
public class CollectionsTest {

    @Test
    public void testMapId() {
        final List<String> list = Arrays.asList("one", "two", "three");
        assertThat(Collections.map(new Id<>(), list), is(list));
    }

    @Test
    public void testMapNeg() {
        final List<Integer> list = Arrays.asList(1, 2, 3);
        final List<Integer> ans = Arrays.asList(-1, -2, -3);
        assertThat(Collections.map(new Negate(), list), is(ans));
    }

    @Test
    public void testMapEmpty() {
        final List<Object> list = java.util.Collections.emptyList();
        assertThat(Collections.map(new Const42<>(), list), is(list));
    }

    @SuppressWarnings("UnusedAssignment")
    @Test
    public void testMapTypes() {
        final List<Base> listB = Arrays.asList(new Base(), new Derived());
        final List<Derived> listD = Arrays.asList(new Derived(), new Derived());

        List<Base> ansB;
        List<Derived> ansD;

        ansB = Collections.map(new BaseToDerived(), listB);
        ansB = Collections.map(new BaseToDerived(), listD);
        ansB = Collections.map(new DerivedToBase(), listD);
        ansB = Collections.map(new DerivedToBase(), listD);
        ansD = Collections.map(new BaseToDerived(), listB);
        ansD = Collections.map(new BaseToDerived(), listD);
    }

    @Test
    public void testFilterAll() {
        final List<Integer> list = Arrays.asList(1, 2, 3);
        assertThat(Collections.filter(new IsPositive(), list), is(list));
    }

    @Test
    public void testFilterEmpty() {
        final List<Integer> list = java.util.Collections.emptyList();
        assertThat(Collections.filter(new IsPositive(), list), is(list));
    }

    @Test
    public void testFilterBase() {
        final List<Integer> list = Arrays.asList(1, 2, 3);
        final List<Integer> ans = Arrays.asList(1, 3);
        assertThat(Collections.filter(new IsOdd(), list), is(ans));
    }

    @Test
    public void testFilterType() {
        final List<Base> listB = Arrays.asList(new Base(), new Derived());
        final List<Derived> listD = Arrays.asList(new Derived(), new Derived());

        final List<Base> ansB = Collections.filter(new Tru<>(), listB);
        Collections.filter(new Tru<Base>(), listD);
        Collections.filter(new Tru<>(), listD);
    }

    @Test
    public void testTakeWhileAll() {
        final List<Integer> list = Arrays.asList(1, 2, 3);
        assertThat(Collections.takeWhile(new IsPositive(), list), is(list));
    }

    @Test
    public void testTakeWhileEmpty() {
        final List<Integer> list = java.util.Collections.emptyList();
        assertThat(Collections.takeWhile(new IsPositive(), list), is(list));
    }

    @Test
    public void testTakeWhileBase() {
        final List<Integer> list1 = Arrays.asList(1, 3, 5, 2, 4);
        final List<Integer> ans1 = Arrays.asList(1, 3, 5);
        assertThat(Collections.takeWhile(new IsOdd(), list1), is(ans1));

        final List<Integer> list2 = Arrays.asList(0, 2, 3, 5, 1, 4);
        final List<Integer> ans2 = java.util.Collections.emptyList();
        assertThat(Collections.takeWhile(new IsOdd(), list2), is(ans2));

        final List<Integer> list3 = Arrays.asList(1, 3, 2, 5, 4, 7);
        final List<Integer> ans3 = Arrays.asList(1, 3);
        assertThat(Collections.takeWhile(new IsOdd(), list3), is(ans3));
    }

    @Test
    public void testTakeWhileType() {
        final List<Base> listB = Arrays.asList(new Base(), new Derived());
        final List<Derived> listD = Arrays.asList(new Derived(), new Derived());

        final List<Base> ansB = Collections.takeWhile(new Tru<>(), listB);
        Collections.takeWhile(new Tru<Base>(), listD);
        Collections.takeWhile(new Tru<>(), listD);
    }

    @Test
    public void testTakeUnlessAll() {
        final List<Integer> list = Arrays.asList(-2, -4, -6);
        assertThat(Collections.takeUnless(new IsPositive(), list), is(list));
    }

    @Test
    public void testTakeUnlessEmpty() {
        final List<Integer> list = java.util.Collections.emptyList();
        assertThat(Collections.takeUnless(new IsPositive(), list), is(list));
    }

    @Test
    public void testTakeUnlessBase() {
        final List<Integer> list1 = Arrays.asList(2, 4, 6, 1, 3);
        final List<Integer> ans1 = Arrays.asList(2, 4, 6);
        assertThat(Collections.takeUnless(new IsOdd(), list1), is(ans1));

        final List<Integer> list2 = Arrays.asList(1, 2, 3, 5, 1, 4);
        final List<Integer> ans2 = java.util.Collections.emptyList();
        assertThat(Collections.takeUnless(new IsOdd(), list2), is(ans2));

        final List<Integer> list3 = Arrays.asList(2, 4, 1, 3, 4, 7);
        final List<Integer> ans3 = Arrays.asList(2, 4);
        assertThat(Collections.takeUnless(new IsOdd(), list3), is(ans3));
    }

    @Test
    public void testTakeUnlessType() {
        final List<Base> listB = Arrays.asList(new Base(), new Derived());
        final List<Derived> listD = Arrays.asList(new Derived(), new Derived());

        final List<Base> ansB = Collections.takeUnless(new Tru<>(), listB);
        Collections.takeUnless(new Tru<Base>(), listD);
        Collections.takeUnless(new Tru<>(), listD);
    }

    @Test
    public void testFoldLSimple() {
        final List<Integer> list = Arrays.asList(1, 2, 3);
        assertThat(Collections.foldl(new Sum(), list, 0), is(6));
    }

    @Test
    public void testFoldLEmpty() {
        final List<Object> list = java.util.Collections.emptyList();
        final Object o = new Object();
        assertThat(Collections.foldl(new Const<>(), list, o), is(o));
    }

    @Test
    public void testFoldLConcat() {
        final LinkedList<String> list = new LinkedList<>();
        list.addAll(Arrays.asList("one", "two", "three", "four"));
        final Concat<String> concat = new Concat<>();
        final LinkedList<String> emptyList = new LinkedList<>();
        assertThat(Collections.foldl(concat, list, emptyList), is(list));
    }

    @Test
    public void testFoldLTypes() {
        final Const<Base, Base> constBB = new Const<>();
        final Const<Base, Derived> constBD = new Const<>();
        final Const<Derived, Base> constDB = new Const<>();
        final Const<Derived, Derived> constDD = new Const<>();
        final Snd<Base, Base> sndBB = new Snd<>();
        final Snd<Base, Derived> sndBD = new Snd<>();
        final Snd<Derived, Base> sndDB = new Snd<>();
        final Snd<Derived, Derived> sndDD = new Snd<>();
        final List<Base> listB = Arrays.asList(new Base(), new Derived());
        final List<Derived> listD = Arrays.asList(new Derived(), new Derived());
        Collections.foldl(constBB, listB, new Base());
        Collections.foldl(constBB, listB, new Derived());
        Collections.foldl(constBB, listD, new Base());
        Collections.foldl(constBB, listD, new Derived());
        Collections.foldl(constBD, listD, new Base());
        Collections.foldl(constBD, listD, new Derived());

        Collections.foldl(constDB, listB, new Derived());
        Collections.foldl(constDB, listD, new Derived());
        Collections.foldl(constDD, listD, new Derived());

        Collections.foldl(sndBB, listB, new Base());
        Collections.foldl(sndBB, listB, new Derived());
        Collections.foldl(sndBB, listD, new Base());
        Collections.foldl(sndBB, listD, new Derived());
        Collections.foldl(sndBD, listD, new Base());
        Collections.foldl(sndBD, listD, new Derived());

        Collections.foldl(sndDD, listD, new Derived());
    }

    @Test
    public void testFoldRSimple() {
        final List<Integer> list = Arrays.asList(1, 2, 3);
        assertThat(Collections.foldr(new Sum(), list, 0), is(6));
    }

    @Test
    public void testFoldREmpty() {
        final List<Object> list = java.util.Collections.emptyList();
        final Object o = new Object();
        assertThat(Collections.foldr(new Const<>(), list, o), is(o));
    }

    @Test
    public void testFoldRConcat() {
        final LinkedList<String> list = new LinkedList<>();
        list.addAll(Arrays.asList("one", "two", "three", "four"));
        final LinkedList<String> ansList = new LinkedList<>();
        ansList.addAll(Arrays.asList("four", "three", "two", "one"));
        final BackConcat<String> backConcat = new BackConcat<>();
        final LinkedList<String> emptyList = new LinkedList<>();
        assertThat(Collections.foldr(backConcat, list, emptyList), is(ansList));
    }

    @Test
    public void testFoldRTypes() {
        final Const<Base, Base> constBB = new Const<>();
        final Const<Base, Derived> constBD = new Const<>();
        final Const<Derived, Base> constDB = new Const<>();
        final Const<Derived, Derived> constDD = new Const<>();
        final Snd<Base, Base> sndBB = new Snd<>();
        final Snd<Base, Derived> sndBD = new Snd<>();
        final Snd<Derived, Base> sndDB = new Snd<>();
        final Snd<Derived, Derived> sndDD = new Snd<>();
        final List<Base> listB = Arrays.asList(new Base(), new Derived());
        final List<Derived> listD = Arrays.asList(new Derived(), new Derived());

        Collections.foldr(constBB, listB, new Base());
        Collections.foldr(constBB, listB, new Derived());
        Collections.foldr(constBB, listD, new Base());
        Collections.foldr(constBB, listD, new Derived());

        Collections.foldr(constDB, listD, new Base());
        Collections.foldr(constDB, listD, new Derived());
        Collections.foldr(constDD, listD, new Derived());

        Collections.foldr(sndBB, listB, new Base());
        Collections.foldr(sndBB, listB, new Derived());
        Collections.foldr(sndBB, listD, new Base());
        Collections.foldr(sndBB, listD, new Derived());
        Collections.foldr(sndBD, listB, new Derived());
        Collections.foldr(sndBD, listD, new Derived());

        Collections.foldr(sndDB, listD, new Base());
        Collections.foldr(sndDB, listD, new Derived());
        Collections.foldr(sndDD, listD, new Derived());
    }

    private static class Base {}

    private static class Derived extends Base {}

    private static class BaseToDerived extends Function1<Base, Derived> {

        @Override
        public Derived apply(final Base base) {
            return new Derived();
        }
    }

    private static class DerivedToBase extends Function1<Derived, Base> {

        @Override
        public Base apply(final Derived derived) {
            return new Base();
        }
    }
}