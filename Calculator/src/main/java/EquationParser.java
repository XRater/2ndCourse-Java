import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class EquationParser {

    public final static Map<String, Operation> OPERATORS = new HashMap<>();
    public final static Map<String, String> BRACKETS = new HashMap<>();

    static {
        OPERATORS.put("+", new Operation((a, b) -> a + b, 1));
        OPERATORS.put("-", new Operation((a, b) -> a - b, 1));
        OPERATORS.put("*", new Operation((a, b) -> a * b, 2));
        OPERATORS.put("/", new Operation((a, b) -> a / b, 3));
        OPERATORS.put("%", new Operation((a, b) -> a % b, 3));
        OPERATORS.put("[", new Operation(null, -1));
        OPERATORS.put("]", new Operation(null, -1));
        OPERATORS.put("(", new Operation(null, -1));
        OPERATORS.put(")", new Operation(null, -1));

        BRACKETS.put(")", "(");
        BRACKETS.put("]", "[");
    }

    private final Queue<Character> queue = new LinkedList<>();

    EquationParser(final String eq) {
        for (final char c : eq.toCharArray()) {
            queue.add(c);
        }
    }

    String getNext() {
        final char c = queue.remove();
        if (unarySymbol(c)) {
            return Character.toString(c);
        }
        final StringBuilder sb = new StringBuilder();
        sb.append(c);
        while (!queue.isEmpty() && !unarySymbol(queue.element())) {
            sb.append(queue.remove());
        }
        return sb.toString();
    }

    boolean isEmpty() {
        return queue.isEmpty();
    }

    private boolean unarySymbol(final char c) {
        return (c == ' ') || BRACKETS.containsValue(Character.toString(c)) ||
                BRACKETS.containsKey(Character.toString(c));
    }

    Integer getNumber(final String symbol) {
        try {
            return Integer.parseInt(symbol);
        } catch (final NumberFormatException e) {
            return null;
        }
    }
}
