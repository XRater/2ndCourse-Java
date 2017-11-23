import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class StackTest {

    @Test
    public void testPush() {
        final Stack<String> stack = new Stack<>();
        stack.push("hello");
        stack.push("null");
        stack.push("");
        assertThrows(NullPointerException.class, () -> stack.push(null));
    }

    @Test
    public void testTop() {
        final Stack<String> stack = new Stack<>();
        assertThrows(NoSuchElementException.class, stack::top);
        stack.push("hello");
        assertThat(stack.top(), is("hello"));
        stack.push("null");
        assertThat(stack.top(), is("null"));
        stack.push("");
        assertThat(stack.top(), is(""));
        stack.push("hello");
        assertThat(stack.top(), is("hello"));
    }

    @Test
    public void testPop() {
        final Stack<String> stack = new Stack<>();
        assertThrows(NoSuchElementException.class, stack::pop);
        stack.push("hello");
        assertThat(stack.pop(), is("hello"));

        stack.push("hello");
        stack.push("two");
        stack.push("one");
        assertThat(stack.pop(), is("one"));
        assertThat(stack.pop(), is("two"));

        stack.push("null");
        assertThat(stack.pop(), is("null"));
        assertThat(stack.pop(), is("hello"));
    }

    @Test
    public void testIsEmpty() {
        final Stack<String> stack = new Stack<>();
        assertThat(stack.isEmpty(), is(true));

        stack.push("hello");
        assertThat(stack.isEmpty(), is(false));
        stack.push("one");
        assertThat(stack.isEmpty(), is(false));

        stack.pop();
        assertThat(stack.isEmpty(), is(false));
        stack.pop();
        assertThat(stack.isEmpty(), is(true));
    }
}