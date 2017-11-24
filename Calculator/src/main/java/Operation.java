public class Operation extends Symbol {

    private final int priority;

    public Operation(final char op, final int priority) {
        super(op);
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }
}
