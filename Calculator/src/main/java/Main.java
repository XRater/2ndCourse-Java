public class Main {

    public static void main(final String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: <equation>");
        }

        final Stack<Integer> integerStack = new StackImp<>();
        final Stack<String> stringStack = new StackImp<>();

        final PolandNotation polandNotation = new PolandNotation(stringStack);
        final Calculator calculator = new Calculator(integerStack);

        final String poland = polandNotation.makePoland(args[0]);
        System.out.println(calculator.evaluate(poland));
    }

}
