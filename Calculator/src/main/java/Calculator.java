import org.jetbrains.annotations.NotNull;

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
    public int evaluate(@NotNull final String eq) {
        clear();
        final EquationParser parser = new EquationParser(eq);
        if (eq.length() == 0) {
            return 0;
        }
        while (parser.hasNext()) {
            final String symbol = parser.getNext();
            final Integer number = parser.getNumber(symbol);
            if (number != null) {
                stack.push(number);
            } else {
                try {
                    final int a = stack.pop();
                    final int b = stack.pop();
                    stack.push(Options.OPERATORS.get(symbol).apply(b, a));
                } catch (@NotNull final NoSuchElementException e) {
                    throw new IllegalArgumentException();
                }
            }
        }
        final int result = stack.pop();
        if (!stack.isEmpty()) {
            throw new IllegalArgumentException();
        }
        return result;
    }

    private void clear() {
        stack.clear();
    }
}
