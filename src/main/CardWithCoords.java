package main;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class CardWithCoords {
    private int x;
    private int y;
    @JsonIgnore
    private Card card;

    public CardWithCoords(int x, int y, GameBoard gameBoard) {
        this.x = x;
        this.y = y;
        if (x == 0)
            card = gameBoard.getRow0().get(y);
        if (x == 1)
            card = gameBoard.getRow1().get(y);
        if (x == 2)
            card = gameBoard.getRow2().get(y);
        if (x == 3)
            card = gameBoard.getRow3().get(y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Card getCard() {
        return card;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setCard(Card card) {
        this.card = card;
    }
}
