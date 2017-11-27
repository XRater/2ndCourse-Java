import java.util.HashMap;
import java.util.Map;

/**
 * Option class.
 * <p>
 * Supported operations are stored in OPERATORS map.
 * <p>
 * Supported brackets with pairs are stored in BRACKETS map.
 */
@SuppressWarnings("WeakerAccess")
public class Options {

    public final static Map<String, Operation> OPERATORS = new HashMap<>();
    public final static Map<String, String> BRACKETS = new HashMap<>();

    static {
        OPERATORS.put("+", new Operation((a, b) -> a + b, 1));
        OPERATORS.put("-", new Operation((a, b) -> a - b, 1));
        OPERATORS.put("*", new Operation((a, b) -> a * b, 2));
        OPERATORS.put("/", new Operation((a, b) -> a / b, 3));
        OPERATORS.put("%", new Operation((a, b) -> a % b, 3));
        OPERATORS.put("[", new Operation(null, -1));
        OPERATORS.put("]", new Operation(null, -1));
        OPERATORS.put("(", new Operation(null, -1));
        OPERATORS.put(")", new Operation(null, -1));

        BRACKETS.put(")", "(");
        BRACKETS.put("]", "[");
    }
}
