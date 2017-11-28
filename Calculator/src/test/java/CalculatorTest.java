import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.junit.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * Test class for Calculator.
 */
@SuppressWarnings("unchecked")
public class CalculatorTest {

    private final Stack stack = mock(StackImp.class);
    private Calculator calculator;

    @Before
    public void setUp() {
        when(stack.top()).thenReturn(0);
        when(stack.pop()).thenReturn(0);
        when(stack.isEmpty()).thenReturn(true);
        calculator = new Calculator(stack);
    }

    @Test
    public void testEmpty() {
        calculator.evaluate("");
        verify(stack, times(0)).push(anyInt());
        verify(stack, times(0)).pop();
        verify(stack, times(0)).isEmpty();
        verify(stack, times(0)).top();
        verify(stack, times(1)).clear();
    }

    @Test
    public void testOneAddition() {
        calculator.evaluate("1 2 +");
        verify(stack, times(3)).push(anyInt());
        verify(stack, times(3)).pop();
        verify(stack, times(1)).isEmpty();
        verify(stack, times(0)).top();
        verify(stack, times(1)).clear();
    }

    @Test
    public void testAddAndMul() {
        calculator.evaluate("1 2 3 * +");
        verify(stack, times(5)).push(anyInt());
        verify(stack, times(5)).pop();
        verify(stack, times(1)).isEmpty();
        verify(stack, times(0)).top();
        verify(stack, times(1)).clear();
    }

    @Test
    public void testMix() {
        calculator.evaluate("1 2 + 3 *");
        verify(stack, times(5)).push(anyInt());
        verify(stack, times(5)).pop();
        verify(stack, times(1)).isEmpty();
        verify(stack, times(0)).top();
        verify(stack, times(1)).clear();
    }

    @Test
    public void testBig() {
        calculator.evaluate("1 2 3 4 + * 3 4 - - 1 * *");
        verify(stack, times(13)).push(anyInt());
        verify(stack, times(13)).pop();
        verify(stack, times(1)).isEmpty();
        verify(stack, times(0)).top();
        verify(stack, times(1)).clear();
    }

    @Test
    public void testCalculations() {
        calculator = new Calculator(new StackImp<>());
        assertThat(calculator.evaluate("1"), is(1));
        assertThat(calculator.evaluate("1 2 +"), is(3));
        assertThat(calculator.evaluate("1 2 3 + +"), is(6));
        assertThat(calculator.evaluate("1 2 3 + *"), is(5));
        assertThat(calculator.evaluate("1 2 3 * +"), is(7));
        assertThat(calculator.evaluate("1 2 + 3 *"), is(9));
        assertThat(calculator.evaluate("1 2 * 3 +"), is(5));
        assertThat(calculator.evaluate("1 2 -"), is(-1));
        assertThat(calculator.evaluate("1 2 3 - -"), is(2));
        assertThat(calculator.evaluate("1 2 + 3 4 - *"), is(-3));
    }

    @Test
    public void testExceptions() {
        calculator = new Calculator(new StackImp<>());
        assertThrows(IllegalArgumentException.class, () -> calculator.evaluate("1 2"));
        assertThrows(IllegalArgumentException.class, () -> calculator.evaluate("1 + +"));
        assertThrows(IllegalArgumentException.class, () -> calculator.evaluate("+ 2 1"));
        assertThrows(IllegalArgumentException.class, () -> calculator.evaluate("1 + 2"));
        assertThrows(IllegalArgumentException.class, () -> calculator.evaluate("1 2 3 4 - 1 - + - -"));
        assertThrows(IllegalArgumentException.class, () -> calculator.evaluate("(1 2 +)"));
    }
}