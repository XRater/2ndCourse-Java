import org.junit.Test;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.junit.MatcherAssert.assertThat;

public class OperationTest {

    @Test
    public void testApply() {
        final Operation plus = new Operation((a, b) -> a + b, 1);
        final Operation minus = new Operation((a, b) -> a - b, 0);
        final Operation first = new Operation((a, b) -> a, -7);

        assertThat(plus.apply(1, 2), is(3));
        assertThat(plus.apply(1, 1), is(2));
        assertThat(plus.apply(-1, 1), is(0));

        assertThat(minus.apply(1, 2), is(-1));
        assertThat(minus.apply(2, 1), is(1));
        assertThat(minus.apply(0, 0), is(0));

        assertThat(first.apply(1, 2), is(1));
        assertThat(first.apply(1, 1), is(1));
        assertThat(first.apply(-1, 1), is(-1));
    }

    @SuppressWarnings("EqualsWithItself")
    @Test
    public void testCompare() {
        final Operation plus = new Operation((a, b) -> a + b, 1);
        final Operation minus = new Operation((a, b) -> a - b, 1);
        final Operation prod = new Operation((a, b) -> a * b, 2);
        final Operation constant = new Operation((a, b) -> 1, 0);

        assertThat(plus.compareTo(plus), is(0));
        assertThat(plus.compareTo(prod), is(lessThan(0)));
        assertThat(plus.compareTo(minus), is(0));
        assertThat(plus.compareTo(constant), is(greaterThan(0)));

        assertThat(minus.compareTo(plus), is(0));
        assertThat(minus.compareTo(prod), is(lessThan(0)));
        assertThat(minus.compareTo(minus), is(0));
        assertThat(minus.compareTo(constant), is(greaterThan(0)));

        assertThat(prod.compareTo(plus), is(greaterThan(0)));
        assertThat(prod.compareTo(prod), is(0));
        assertThat(prod.compareTo(minus), is(greaterThan(0)));
        assertThat(prod.compareTo(constant), is(greaterThan(0)));

        assertThat(constant.compareTo(plus), is(lessThan(0)));
        assertThat(constant.compareTo(prod), is(lessThan(0)));
        assertThat(constant.compareTo(minus), is(lessThan(0)));
        assertThat(constant.compareTo(constant), is(0));
    }

}