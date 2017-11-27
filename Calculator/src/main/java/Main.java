/**
 * This console utility calculates the value of given math equation. Supported brackets and operations
 * might be customized in Options class.
 */
@SuppressWarnings("WeakerAccess")
public class Main {

    /**
     * The method evaluates value of he given equation.
     *
     * @param args equation to evaluate.
     */
    public static void main(final String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: <equation>");
        }

        final Stack<Integer> integerStack = new StackImp<>();
        final Stack<String> stringStack = new StackImp<>();

        final ConverterToPostfixNotation converterToPostfixNotation = new ConverterToPostfixNotation(stringStack);
        final Calculator calculator = new Calculator(integerStack);

        try {
            final String poland = converterToPostfixNotation.makePoland(args[0]);
            System.out.println(calculator.evaluate(poland));
        } catch (final IllegalArgumentException e) {
            System.out.println("Parse error.");
        }
    }

}
