import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.junit.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class EquationParserTest {

    @Test
    public void testEmpty() {
        final EquationParser parser = new EquationParser("");
        assertThat(parser.hasNext(), is(false));
        assertThrows(NoSuchElementException.class, parser::getNext);
    }

    @Test
    public void testSpaces() {
        final EquationParser parser = new EquationParser("    ");
        assertThat(parser.hasNext(), is(false));
        assertThrows(NoSuchElementException.class, parser::getNext);
    }

    @Test
    public void testParsing() {
        final EquationParser parser = new EquationParser("a + ( sd :d))  9");
        final List<String> tokens = new ArrayList<>();
        while (parser.hasNext()) {
            tokens.add(parser.getNext());
        }
        assertThat(tokens, contains("a", "+", "(", "sd", ":d", ")", ")", "9"));
    }

    @Test
    public void testGetNumber() {
        final EquationParser parser = new EquationParser("");
        assertThat(parser.getNumber("13"), equalTo(13));
        assertThat(parser.getNumber("s13"), equalTo(null));
        assertThat(parser.getNumber("(13)"), equalTo(null));
        assertThat(parser.getNumber(""), equalTo(null));
        assertThat(parser.getNumber("0"), equalTo(0));
        assertThat(parser.getNumber("1245"), equalTo(1245));
    }
}