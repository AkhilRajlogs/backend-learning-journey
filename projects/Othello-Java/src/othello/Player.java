package othello;

public class Player {

    private char symbol;
    private String name;

    public Player(String name, char symbol){
        setName(name);
        setSymbol(symbol);
    }
    

    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
