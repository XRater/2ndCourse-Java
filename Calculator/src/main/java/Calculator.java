import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * This class evaluates equation written in postfix notation using given integer stack objects.
 *
 * Supported operations and brackets are stored in OPERATORS and BRACKETS maps (in Option class).
 *
 * In OPERATORS map keys are string notations of operators and value is "Operation" object for it.
 *
 * In BRACKETS keys are closing brackets and values are opening pairs for them.
 */
@SuppressWarnings("WeakerAccess")
public class Calculator {

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
     * If input equation was not valid IllegalArgumentException will be thrown.
     *
     * @param eq equation to evaluate.
     * @throws IllegalArgumentException if input equation was not valid.
     * @return result of evaluation.
     */
    public int evaluate(final String eq) {
        clear();
        final EquationParser parser = new EquationParser(eq);
        while (parser.hasNext()) {
            final String symbol = parser.getNext();
            final Integer number = parser.getNumber(symbol);
            if (number != null) {
                stack.push(number);
            } else if (symbol.equals(" ")) {
//                noinspection UnnecessaryContinue
                continue;
            } else {
                try {
                    final int a = stack.pop();
                    final int b = stack.pop();
                    stack.push(Options.OPERATORS.get(symbol).apply(b, a));
                } catch (final NoSuchElementException e) {
                    throw new IllegalArgumentException();
                }
            }
        }
        return stack.top();
    }

    private void clear() {
        stack.clear();
    }
}
