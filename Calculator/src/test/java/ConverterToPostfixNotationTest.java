import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ConverterToPostfixNotationTest {

    @Mock
    private Stack<String> mockStack;
    private final Stack<String> stack = new StackImp<>();

    private ConverterToPostfixNotation converterMock;
    private final ConverterToPostfixNotation converter = new ConverterToPostfixNotation(stack);

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        converterMock = new ConverterToPostfixNotation(mockStack);
    }

    @Test
    public void testMockEmpty() {
        when(mockStack.isEmpty()).thenReturn(true);
        converterMock.convertToPostfix("");
        verify(mockStack, times(1)).isEmpty();
        verify(mockStack, never()).pop();
        verify(mockStack, never()).push(anyString());
        verify(mockStack, never()).top();
        verify(mockStack, times(1)).clear();
    }

    @Test
    public void testAddMock() {
        when(mockStack.top()).thenReturn("+", "BUM");
        when(mockStack.isEmpty()).thenReturn(true, false, true);
        when(mockStack.pop()).thenReturn("+");

        converterMock.convertToPostfix("1 + 2");

        verify(mockStack, times(1)).push(any());
        verify(mockStack, times(1)).pop();
        verify(mockStack, times(0)).top();
        verify(mockStack, times(3)).isEmpty();
        verify(mockStack, times(1)).clear();
    }

    @Test
    public void testAddMulMock() {
        final String s = "1 + 2 * 3";

        when(mockStack.top()).thenReturn("+", "BUM");
        when(mockStack.isEmpty()).thenReturn(true, false, false, false, true);
        when(mockStack.pop()).thenReturn("*", "+");

        converterMock.convertToPostfix(s);

        verify(mockStack, times(2)).push(any());
        verify(mockStack, times(2)).pop();
        verify(mockStack, times(1)).top();
        verify(mockStack, times(5)).isEmpty();
        verify(mockStack, times(1)).clear();
    }

    @Test
    public void testAddAddMock() {
        final String s = "1 + 2 + 3";

        when(mockStack.top()).thenReturn("+", "BUM");
        when(mockStack.isEmpty()).thenReturn(true, false, true, false, true);
        when(mockStack.pop()).thenReturn("+", "+");

        converterMock.convertToPostfix(s);

        verify(mockStack, times(2)).push(any());
        verify(mockStack, times(2)).pop();
        verify(mockStack, times(1)).top();
        verify(mockStack, times(5)).isEmpty();
        verify(mockStack, times(1)).clear();
    }

    @Test
    public void testAddSubMock() {
        final String s = "1 + 2 - 3";

        when(mockStack.top()).thenReturn("+", "BUM");
        when(mockStack.isEmpty()).thenReturn(true, false, true, false, true);
        when(mockStack.pop()).thenReturn("+", "-");

        converterMock.convertToPostfix(s);

        verify(mockStack, times(2)).push(any());
        verify(mockStack, times(2)).pop();
        verify(mockStack, times(1)).top();
        verify(mockStack, times(5)).isEmpty();
        verify(mockStack, times(1)).clear();

    }

    @Test
    public void testBracketsMock() {
        final String s = "()";

        when(mockStack.top()).thenReturn("(", "BUM");
        when(mockStack.isEmpty()).thenReturn(true);
        when(mockStack.pop()).thenReturn("(");

        converterMock.convertToPostfix(s);

        verify(mockStack, times(1)).push(any());
        verify(mockStack, times(1)).pop();
        verify(mockStack, times(1)).top();
        verify(mockStack, times(1)).isEmpty();
        verify(mockStack, times(1)).clear();
    }

    @Test
    public void testBracketsAddMock() {
        final String s = "(1 + 2)";

        when(mockStack.top()).thenReturn("(", "+", "(", "BUM");
        when(mockStack.isEmpty()).thenReturn(false, true);
        when(mockStack.pop()).thenReturn("+", "(");

        converterMock.convertToPostfix(s);

        verify(mockStack, times(2)).push(any());
        verify(mockStack, times(2)).pop();
        verify(mockStack, times(3)).top();
        verify(mockStack, times(2)).isEmpty();
        verify(mockStack, times(1)).clear();
    }

    @Test
    public void testBracketsAddMulMock() {
        final String s = "(1 + 2) * 3";

        when(mockStack.top()).thenReturn("(", "+", "(", "BUM");
        when(mockStack.isEmpty()).thenReturn(false, true, false, true);
        when(mockStack.pop()).thenReturn("+", "(", "*");

        converterMock.convertToPostfix(s);

        verify(mockStack, times(3)).push(any());
        verify(mockStack, times(3)).pop();
        verify(mockStack, times(3)).top();
        verify(mockStack, times(4)).isEmpty();
        verify(mockStack, times(1)).clear();
    }

    @Test
    public void testEmpty() {
        assertThat(converter.convertToPostfix(""), is(""));
    }

    @Test
    public void testAdd() {
        assertThat(converter.convertToPostfix("1 + 2"), is("1 2 + "));
    }

    @Test
    public void testAddMul() {
        final String s = "1 + 2 * 3";
        assertThat(converter.convertToPostfix(s), is("1 2 3 * + "));
    }

    @Test
    public void testAddAdd() {
        final String s = "1 + 2 + 3";
        assertThat(converter.convertToPostfix(s), is("1 2 + 3 + "));
    }

    @Test
    public void testAddSub() {
        final String s = "1 + 2 - 3";
        assertThat(converter.convertToPostfix(s), is("1 2 + 3 - "));
    }

    @Test
    public void testBrackets() {
        final String s = "()";
        assertThat(converter.convertToPostfix(s), is(""));
    }

    @Test
    public void testBracketsAdd() {
        final String s = "(1 + 2)";
        assertThat(converter.convertToPostfix(s), is("1 2 + "));
    }

    @Test
    public void testBracketsAddMul() {
        final String s = "(1 + 2) * 3";
        assertThat(converter.convertToPostfix(s), is("1 2 + 3 * "));
    }

    @Test
    public void testThrows() {
        assertThrows(ParseException.class, () -> converter.convertToPostfix("1 & 3"));
        assertThrows(ParseException.class, () -> converter.convertToPostfix(")("));
        assertThrows(ParseException.class, () -> converter.convertToPostfix("(]"));
        assertThrows(ParseException.class, () -> converter.convertToPostfix("(()))("));
        assertThrows(ParseException.class, () -> converter.convertToPostfix("1+4"));
        assertThrows(ParseException.class, () -> converter.convertToPostfix("((("));
        assertThrows(ParseException.class, () -> converter.convertToPostfix("()()("));
        assertThrows(ParseException.class, () -> converter.convertToPostfix("[()]([()]"));
    }
}