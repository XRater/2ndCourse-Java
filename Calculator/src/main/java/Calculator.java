import java.util.HashMap;
import java.util.Map;

/**
 * This class evaluates equation written in postfix notation using given integer stack objects.
 *
 * Supported operations and brackets are stored in OPERATORS and BRACKETS maps.
 *
 * In OPERATORS map keys are string notations of operators and value is "Operation" object for it.
 *
 * In BRACKETS keys are closing brackets and values are opening pairs for them.
 */
@SuppressWarnings("WeakerAccess")
public class Calculator {

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

    private final Stack<Integer> stack;

    /**
     * Constructs Calculator using given stack.
     *
     * @param stack stack to use
     */
    public Calculator(final Stack<Integer> stack) {
        this.stack = stack;
    }

    /**
     * The method evaluates given equation in postfix notation.
     *
     * @param eq equation to evaluate.
     * @return result of evaluation.
     */
    public int evaluate(final String eq) {
        clear();
        final EquationParser parser = new EquationParser(eq);
        while (!parser.hasNext()) {
            final String symbol = parser.getNext();
            final Integer number = parser.getNumber(symbol);
            if (number != null) {
                stack.push(number);
            } else if (symbol.equals(" ")) {
                //noinspection UnnecessaryContinue
                continue;
            } else {
                final int a = stack.pop();
                final int b = stack.pop();
                stack.push(OPERATORS.get(symbol).apply(b, a));
            }
        }
        return stack.top();
    }

    private void clear() {
        stack.clear();
    }
}
