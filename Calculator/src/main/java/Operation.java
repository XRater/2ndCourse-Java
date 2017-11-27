import org.jetbrains.annotations.NotNull;

import java.util.function.BinaryOperator;
import java.util.function.Function;

/**
 * Binary operation class. Represents two-argument function, but also has priority.
 * <p>
 * Priority shows order of operation's execution. From two operations firstly will be
 * executed operation with higher priority.
 */
class Operation implements BinaryOperator<Integer>, Comparable<Operation> {

    private final BinaryOperator<Integer> op;
    private final int priority;

    /**
     * Constructs operation from the given two-argument function and priority.
     *
     * @param op function to execute.
     * @param priority priority of operation.
     */
    Operation(final BinaryOperator<Integer> op, final int priority) {
        this.priority = priority;
        this.op = op;
    }

    /**
     * Applies operation to two arguments.
     *
     * @param a first argument
     * @param b second argument
     * @return result of operation
     */
    @Override
    public Integer apply(final Integer a, final Integer b) {
        return op.apply(a, b);
    }

    /**
     * Compares two operations y their priorities.
     *
     * @param o operation to compare
     * @return positive int if priority of our operation is larger then priority of the given one,
     * zero if equals and negative int otherwise.
     */
    @Override
    public int compareTo(@NotNull final Operation o) {
        return priority - o.priority;
    }
}
