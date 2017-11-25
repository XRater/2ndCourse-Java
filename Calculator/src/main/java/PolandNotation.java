@SuppressWarnings("WeakerAccess")
public class PolandNotation {

    public String makePoland(final String eq) {
        final StringBuilder sb = new StringBuilder();
        final Stack<String> operations = new StackImp<>();
        final EquationParser parser = new EquationParser(eq);

        while (!parser.isEmpty()) {
            final String symbol = parser.getNext();
            final Integer number = parser.getNumber(symbol);
            if (number != null) {
                sb.append(number).append(' ');
            } else if (EquationParser.BRACKETS.containsValue(symbol)) {
                operations.push(symbol);
            } else if (EquationParser.BRACKETS.containsKey(symbol)) {
                while (!operations.isEmpty() &&
                        !operations.top().equals(EquationParser.BRACKETS.get(symbol))) {
                    sb.append(operations.pop()).append(' ');
                }
                operations.pop();
            } else if (EquationParser.OPERATORS.containsKey(symbol)) {
                while (!operations.isEmpty() &&
                        EquationParser.OPERATORS.get(operations.top()).compareTo(EquationParser.OPERATORS.get(symbol)) >= 0) {
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
