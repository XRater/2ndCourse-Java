import java.util.Arrays;
import java.util.List;

public class MathSymbol {

    private final Symbol symbol;

    private static final List<Operation> operations = Arrays.asList(
        new Operation('+', 1),
        new Operation('-', 1),
        new Operation('*', 2)
    );

    public MathSymbol(final Symbol symbol) {
        this.symbol = symbol;
    }

    public boolean isOperation() {
        for (final Operation op : operations) {
            if (symbol.getSymbol() == op.getSymbol()) {
                return true;
            }
        }
        return false;
    }
}
