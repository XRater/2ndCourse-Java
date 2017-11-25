public class Main {

    public static void main(final String[] args) {
        final PolandNotation polandNotation = new PolandNotation();
        final String poland = polandNotation.makePoland("15 / (4 + 3)");
        final Stack<Integer> stack = new StackImp<>();
        final Calculator calculator = new Calculator(stack);
        System.out.println(calculator.evaluate(poland));
    }

}
