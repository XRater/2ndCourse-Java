@SuppressWarnings("WeakerAccess")
public class PolandNotation {

    private final Stack<String> operations;

    public PolandNotation(final Stack<String> stack) {
        operations = stack;
    }

    public String makePoland(final String eq) {
        final StringBuilder sb = new StringBuilder();
        final EquationParser parser = new EquationParser(eq);

        while (!parser.hasNext()) {
            final String symbol = parser.getNext();
            final Integer number = parser.getNumber(symbol);
            if (number != null) {
                sb.append(number).append(' ');
            } else if (Calculator.BRACKETS.containsValue(symbol)) {
                operations.push(symbol);
            } else if (Calculator.BRACKETS.containsKey(symbol)) {
                while (!operations.isEmpty() &&
                        !operations.top().equals(Calculator.BRACKETS.get(symbol))) {
                    sb.append(operations.pop()).append(' ');
                }
                operations.pop();
            } else if (Calculator.OPERATORS.containsKey(symbol)) {
                while (!operations.isEmpty() &&
                        Calculator.OPERATORS.get(operations.top()).compareTo(Calculator.OPERATORS.get(symbol)) >= 0) {
                    sb.append(operations.pop()).append(' ');
                }
                operations.push(symbol);
            } else if (symbol.equals(" ")) {
                //noinspection UnnecessaryContinue
                continue;
            } else {
                throw new UnknownSymbolException(symbol);
            }
        }

        while (!operations.isEmpty()) {
            sb.append(operations.pop()).append(' ');
        }
        return sb.toString();
    }

}
