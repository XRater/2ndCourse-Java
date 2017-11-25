@SuppressWarnings("WeakerAccess")
public class Calculator {

    private final Stack<Integer> stack;

    public Calculator(final Stack<Integer> stack) {
        this.stack = stack;
    }

    public int evaluate(final String eq) {
        clear();
        final EquationParser parser = new EquationParser(eq);
        while (!parser.isEmpty()) {
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
                stack.push(EquationParser.OPERATORS.get(symbol).apply(b, a));
            }
        }
        return stack.top();
    }

    private void clear() {
        stack.clear();
    }
}
