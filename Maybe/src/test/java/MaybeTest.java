import exceptions.NoElementInMaybeException;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MaybeTest {

    @Test
    public void testIsPresent() {
        Maybe<Integer> eMI = Maybe.nothing();
        Maybe<String> eMS = Maybe.nothing();
        Maybe<Maybe<Character>> eMM = Maybe.nothing();
        assertFalse(eMI.isPresent());
        assertFalse(eMS.isPresent());
        assertFalse(eMM.isPresent());

        Maybe<Integer> mI = Maybe.just(0);
        Maybe<Integer> mI1 = Maybe.just(1);
        Maybe<Integer> mIN = Maybe.just(null);
        Maybe<String> mS = Maybe.just("hello");
        Maybe<String> mSE = Maybe.just("");
        Maybe<String> mSN = Maybe.just(null);
        Maybe<Maybe<Character>> mM = Maybe.just(Maybe.just('c'));
        Maybe<Maybe<Character>> mMN = Maybe.just(Maybe.nothing());
        Maybe<Maybe<Character>> mMNL = Maybe.just(null);

        assertTrue(mI.isPresent());
        assertTrue(mI1.isPresent());
        assertTrue(mIN.isPresent());
        assertTrue(mS.isPresent());
        assertTrue(mSE.isPresent());
        assertTrue(mSN.isPresent());
        assertTrue(mM.isPresent());
        assertTrue(mMN.isPresent());
        assertTrue(mMNL.isPresent());
    }

    @Test
    public void testGet() throws NoElementInMaybeException {
        Maybe<Integer> eMI = Maybe.nothing();
        Maybe<String> eMS = Maybe.nothing();
        Maybe<Maybe<Character>> eMM = Maybe.nothing();

        assertThrows(NoElementInMaybeException.class, eMI::get);
        assertThrows(NoElementInMaybeException.class, eMS::get);
        assertThrows(NoElementInMaybeException.class, eMM::get);

        Maybe<Integer> mI = Maybe.just(0);
        Maybe<Integer> mI1 = Maybe.just(1);
        Maybe<Integer> mIN = Maybe.just(null);
        Maybe<String> mS = Maybe.just("hello");
        Maybe<String> mSE = Maybe.just("");
        Maybe<String> mSN = Maybe.just(null);
        Maybe<Maybe<Character>> mM = Maybe.just(Maybe.just('c'));
        Maybe<Maybe<Character>> mMN = Maybe.just(Maybe.nothing());
        Maybe<Maybe<Character>> mMNL = Maybe.just(null);

        assertEquals(new Integer(0), mI.get());
        assertEquals(new Integer(1), mI1.get());
        assertEquals(null, mIN.get());
        assertEquals("hello", mS.get());
        assertEquals("", mSE.get());
        assertEquals(null, mSN.get());
        assertEquals(Maybe.just('c'), mM.get());
        assertEquals(Maybe.nothing(), mMN.get());
        assertEquals(null, mMNL.get());
    }

    @Test
    public void testMap() {
        Maybe<Integer> eI = Maybe.nothing();
        Maybe<Integer> mI = Maybe.just(0);
        Maybe<Integer> mI1 = Maybe.just(1);
        Maybe<Integer> mN = Maybe.just(null);
        Maybe<String> mS = Maybe.just("hello");
        Maybe<String> mS1 = Maybe.just("second");

        assertEquals(Maybe.nothing(), eI.map((n) -> n + 1));
        assertEquals(Maybe.just(1), mI.map((n) -> n + 1));
        assertEquals(Maybe.just(2), mI1.map((n) -> n + 1));
        assertEquals(Maybe.just("el"), mS.map((s) -> s.substring(1, 3)));
        assertEquals(Maybe.just("second".hashCode()), mS1.map(String::hashCode));
        assertEquals(Maybe.just("second".length()), mS1.map(String::length));
        assertEquals(Maybe.just(1.2), mS1.map((s) -> 1.2));

        assertThrows(NullPointerException.class, () -> mN.map((n) -> n + 1));
    }

    @Test
    public void testMapInch() throws NoElementInMaybeException {
        Maybe<Derived> mD = Maybe.just(new Derived());

        assertEquals(mD, mD.map(Functions::returnDerived));
        assertEquals(mD, mD.map(Functions::returnBase));
    }

    @Test
    public void testEquals() {
        assertEquals(Maybe.just(1), Maybe.just(1));
        assertEquals(Maybe.just(null), Maybe.just(null));
        assertEquals(Maybe.just("hello"), Maybe.just("hello"));
        assertEquals(Maybe.just(Maybe.nothing()), Maybe.just(Maybe.nothing()));

        assertNotEquals(Maybe.just(0), Maybe.nothing());
        assertNotEquals(Maybe.just(0), null);
        assertNotEquals(Maybe.nothing(), null);
        assertNotEquals(Maybe.nothing(), Maybe.just(Maybe.nothing()));
        assertNotEquals(Maybe.nothing(), "hello");
        assertNotEquals(Maybe.just("hello"), "hello");
        assertNotEquals(Maybe.just(null), null);
        assertNotEquals(Maybe.just(0), 0);
    }

    private static class Functions {

        static Base returnBase(Base b) {
            return b;
        }

        static Derived returnDerived(Derived d) {
            return d;
        }
    }

    private static class Base {
    }

    private static class Derived extends Base {
    }
}
