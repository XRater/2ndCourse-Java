import org.jetbrains.annotations.NotNull;

import java.util.function.BinaryOperator;
import java.util.function.Function;

class Operation implements BinaryOperator<Integer>, Comparable<Operation> {

    private final BinaryOperator<Integer> op;
    private final int priority;

    Operation(final BinaryOperator<Integer> op, final int priority) {
        this.priority = priority;
        this.op = op;
    }

    @Override
    public Integer apply(final Integer a, final Integer b) {
        return op.apply(a, b);
    }

    @Override
    public int compareTo(@NotNull final Operation o) {
        return priority - o.priority;
    }
}
