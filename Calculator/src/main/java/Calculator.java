import java.util.Arrays;
import java.util.List;

public class Calculator {

    public int evaluate(final String eq) {
        final String poland = makePoland(eq);
        System.out.println(poland);
        return 0;
//        return calculateFromPolandForm(poland);
    }

    private int calculateFromPolandForm(final Stack<Character> poland) {
        return 0;
    }

    private String makePoland(final String eq) {
        final StringBuilder sb = new StringBuilder();
        final Stack<Character> operations = new Stack<>();
        for (final char c : eq.toCharArray()) {
            if (c == ' ') {
                continue;
            } if (Character.isDigit(c)) {
                sb.append(c).append(' ');
            } else if (isOperation(c)) {
                pushToStack(sb, operations, c);
            } else {
                throw new UnknownSymbolException(Character.toString(c));
            }
        }
        while (!operations.isEmpty()) {
            sb.append(operations.pop()).append(' ');
        }
        return sb.toString();
    }

    private void pushToStack(final StringBuilder sb, final Stack<Character> operations, final char op) {
        final int priority = getPriority(op);
        while (!operations.isEmpty() && priority <= getPriority(operations.top())) {
            final char lastOp = operations.pop();
            sb.append(lastOp).append(' ');
        }
        operations.push(op);
    }

    private int getPriority(final char op) {
        switch (op) {
            case '+':
                return 1;
            case '-':
                return 1;
            case '*':
                return 2;
            default:
                throw new UnsupportedOperationException("op");
        }
    }

//    public static void main(String[] args) {
//        Calculator calculator = new Calculator();
//        calculator.evaluate("1 + 2");
//        calculator.evaluate("1*1 + 2*3 + 5");
//        calculator.evaluate("1 + 4 + 2");
//        calculator.evaluate("1 + 2 * 3");
//        System.out.println(Character.isDigit('1'));
//    }

}
