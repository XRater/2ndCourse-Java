import java.util.LinkedList;
import java.util.Queue;

/**
 * This class parses given equation. It consequently extracts numbers/operations from the equation.
 */
class EquationParser {

    private final Queue<Character> queue = new LinkedList<>();

    /**
     * Constructs parser for equation.
     *
     * @param eq equation to parse.
     */
    EquationParser(final String eq) {
        for (final char c : eq.toCharArray()) {
            queue.add(c);
        }
    }

    /**
     * Returns next number or operation (including brackets). Spaces will be dropped.
     *
     * @return next equation element.
     */
    String getNext() {
        removeSpaces();
        final char c = queue.remove();
        if (isBracket(c)) {
            return Character.toString(c);
        }
        final StringBuilder sb = new StringBuilder();
        sb.append(c);
        while (!queue.isEmpty() && !isBracket(queue.element())) {
            sb.append(queue.remove());
        }
        return sb.toString();
    }

    /**
     * The method checks if parse was completed.
     *
     * @return true if nothing left to parse and false otherwise.
     */
    boolean hasNext() {
        removeSpaces();
        return queue.isEmpty();
    }

    /**
     * The method returns parses string to get integer value. This method do not throw
     * parse exception in opposite to String.parseInt() method. The return value will be null
     * if parse was not successful.
     *
     * @param symbol string to parse
     * @return Integer with string value or null if parse was not successful.
     */
    Integer getNumber(final String symbol) {
        try {
            return Integer.parseInt(symbol);
        } catch (final NumberFormatException e) {
            return null;
        }
    }

    /**
     * The method checks if symbol is bracket.
     *
     * Brackets are all elements in Calculator.BRACKETS map.
     *
     * @param c symbol to check.
     * @return true if the symbol is bracket and false otherwise.
     */
    private boolean isBracket(final char c) {
        return Calculator.BRACKETS.containsValue(Character.toString(c)) ||
                Calculator.BRACKETS.containsKey(Character.toString(c));
    }

    /**
     * The method removes unnecessary spaces from the front of the equation.
     */
    private void removeSpaces() {
        while (!queue.isEmpty() && queue.element() == ' ') {
            queue.remove();
        }
    }
}
