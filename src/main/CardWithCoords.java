package main;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class CardWithCoords {
    private int x;
    private int y;
    @JsonIgnore
    private Card card;

    public CardWithCoords(final int x, final int y, final GameBoard gameBoard) {
        this.x = x;
        this.y = y;
        if (x == 0) {
            card = gameBoard.getRow0().get(y);
        }
        if (x == 1) {
            card = gameBoard.getRow1().get(y);
        }
        if (x == 2) {
            card = gameBoard.getRow2().get(y);
        }
        if (x == (1 + 2)) {
            card = gameBoard.getRow3().get(y);
        }
    }

    /**
     * x getter
     * @return
     */
    public int getX() {
        return x;
    }

    /**
     * y getter
     * @return
     */
    public int getY() {
        return y;
    }

    /**
     * card getter
     * @return
     */
    public Card getCard() {
        return card;
    }

    /**
     * x setter
     * @param x
     */
    public void setX(final int x) {
        this.x = x;
    }

    /**
     * y setter
     * @param y
     */
    public void setY(final int y) {
        this.y = y;
    }

    /**
     * card setter
     * @param card
     */
    public void setCard(final Card card) {
        this.card = card;
    }
}
