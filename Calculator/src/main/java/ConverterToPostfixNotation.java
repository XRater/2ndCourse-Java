import org.jetbrains.annotations.NotNull;

import java.util.NoSuchElementException;

/**
 * This class converts equation to postfix notation.
 * <p>
 * Class takes operations and brackets information from Options class.
 */
@SuppressWarnings("WeakerAccess")
class ConverterToPostfixNotation {

    private final Stack<String> operations;

    /**
     * Constructs class from the given stack.
     *
     * @param stack stack to use.
     */
    ConverterToPostfixNotation(final Stack<String> stack) {
        operations = stack;
    }

    /**
     * Converts given equation written in infix notation into postfix notation.
     *
     * All numbers and operations must be divided with at least one space symbol.
     *
     * Method does not guarantee that output expression will be valid in case if initial
     * equation was not. Additionally, this method does not check correctness of input equation.
     *
     * @param eq equation to convert.
     * @throws ParseException if there was an error while parsing.
     * @return string representation of result equation.
     */
    @NotNull
    String convertToPostfix(@NotNull final String eq) {
        clear();
        final StringBuilder sb = new StringBuilder();
        final EquationParser parser = new EquationParser(eq);

        while (parser.hasNext()) {
            final String symbol = parser.getNext();
            final Integer number = parser.getNumber(symbol);
            if (number != null) {
                sb.append(number).append(' ');
            } else if (Options.BRACKETS.containsValue(symbol)) {
                operations.push(symbol);
            } else if (Options.BRACKETS.containsKey(symbol)) {
                try {
                    while (!operations.top().equals(Options.BRACKETS.get(symbol))) {
                        sb.append(operations.pop()).append(' ');
                    }
                } catch (@NotNull final NoSuchElementException e) {
                    throw new ParseException("No pair bor bracket " + symbol);
                }
                operations.pop();
            } else if (Options.OPERATORS.containsKey(symbol)) {
                while (!operations.isEmpty() &&
                        Options.OPERATORS.get(operations.top()).compareTo(Options.OPERATORS.get(symbol)) >= 0) {
                    sb.append(operations.pop()).append(' ');
                }
                operations.push(symbol);
            } else {
                throw new ParseException(symbol);
            }
        }

        while (!operations.isEmpty()) {
            final String symbol = operations.pop();
            if (Options.BRACKETS.containsValue(symbol)) {
                throw new ParseException("End of the the string, but some brackets are not closed.");
            }
            sb.append(symbol).append(' ');
        }
        return sb.toString();
    }

    private void clear() {
        operations.clear();
    }
}
