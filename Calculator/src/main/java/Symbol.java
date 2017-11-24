public class Symbol {

    private final char symbol;

    public Symbol(final char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() {
        return symbol;
    }

    public boolean isDigit() {
        return Character.isDigit(symbol);
    }
}
