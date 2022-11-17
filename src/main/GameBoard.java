package main;

import fileio.StartGameInput;

import java.util.ArrayList;

public class GameBoard {
    private ArrayList<Card> row0;
    private ArrayList<Card> row1;
    private ArrayList<Card> row2;
    private ArrayList<Card> row3;
    private int round;
    private int cnt;
    private int playerTurn;

    public GameBoard(final StartGameInput startGame) {
        this.round = 0;
        this.cnt = 0;
        this.playerTurn = startGame.getStartingPlayer();
        this.row0 = new ArrayList<Card>(1 + 2 + 2);
        this.row1 = new ArrayList<Card>(1 + 2 + 2);
        this.row2 = new ArrayList<Card>(1 + 2 + 2);
        this.row3 = new ArrayList<Card>(1 + 2 + 2);

    }

    /**
     * a method that proceeds the game to its next round:
     * we increase the round counter
     * we make each player draw a card from their chosen decks
     * we add mana to each player in acordance with the round number
     * @param player1
     * @param player2
     */
    public void nextRound(final Player player1, final Player player2) {
        round++;
        player1.drawCard();
        player2.drawCard();
        player1.addMana(round);
        player2.addMana(round);

    }

    /**
     * a method proceeds the game to its next turn:
     * we increase the turn counter
     * we set the current player to the opposite of what it was
     * if two rounds have passed (one for each player) we trigger the next round
     * we check for frozen and dead cards
     * we reset all the cards' attacks, so they will be able to attack again next turn
     * @param player1
     * @param player2
     */
    public void endPlayerTurn(final Player player1, final Player player2) {
        this.cnt++;
        if (this.playerTurn == 1) {
            this.setPlayerTurn(2);
        } else {
            this.setPlayerTurn(1);
        }
        if (this.cnt % 2 == 0) {
            nextRound(player1, player2);
        }
        unfreeze();
        removeDead();
        resetAttacks(player1, player2);
    }

    /**
     * a method that returns the current player
     * @param player1
     * @param player2
     * @return
     */
    public Player getCurrentPlayer(final Player player1, final Player player2) {
        if (this.playerTurn == 1) {
            return player1;
        } else {
            return player2;
        }
    }

    /**
     * a method that unfreezes the cards two player turns after they have been frozen
     */
    public void unfreeze() {
        for (int i = 0; i < this.getRow0().size(); i++) {
            if (this.getRow0().get(i).getIsFrozen() > 0) {
                this.getRow0().get(i).setIsFrozen(this.getRow0().get(i).getIsFrozen() - (1.0 / 2));
            }
        }
        for (int i = 0; i < this.getRow1().size(); i++) {
            if (this.getRow1().get(i).getIsFrozen() > 0) {
                this.getRow1().get(i).setIsFrozen(this.getRow1().get(i).getIsFrozen() - (1.0 / 2));
            }
        }
        for (int i = 0; i < this.getRow2().size(); i++) {
            if (this.getRow2().get(i).getIsFrozen() > 0) {
                this.getRow2().get(i).setIsFrozen(this.getRow2().get(i).getIsFrozen() - (1.0 / 2));
            }
        }
        for (int i = 0; i < this.getRow3().size(); i++) {
            if (this.getRow3().get(i).getIsFrozen() > 0) {
                this.getRow3().get(i).setIsFrozen(this.getRow3().get(i).getIsFrozen() - (1.0 / 2));
            }
        }
    }

    /**
     * a method that checks for cards with 0 health on the game board and removes them
     */
    public void removeDead() {
        for (int i = 0; i < this.getRow0().size(); i++) {
            if (this.getRow0().get(i).getHealth() <= 0) {
                this.getRow0().remove(i);
            }
        }
        for (int i = 0; i < this.getRow1().size(); i++) {
            if (this.getRow1().get(i).getHealth() <= 0) {
                this.getRow1().remove(i);
            }
        }
        for (int i = 0; i < this.getRow2().size(); i++) {
            if (this.getRow2().get(i).getHealth() <= 0) {
                this.getRow2().remove(i);
            }
        }
        for (int i = 0; i < this.getRow3().size(); i++) {
            if (this.getRow3().get(i).getHealth() <= 0) {
                this.getRow3().remove(i);
            }
        }
    }

    /**
     * a method that resets all the cards' and heroes hasAttacked tag
     * @param player1
     * @param player2
     */
    public void resetAttacks(final Player player1, final Player player2) {
        for (Card card : this.getRow0()) {
            card.setHasAttacked(0);
        }
        for (Card card : this.getRow1()) {
            card.setHasAttacked(0);
        }
        for (Card card : this.getRow2()) {
            card.setHasAttacked(0);
        }
        for (Card card : this.getRow3()) {
            card.setHasAttacked(0);
        }
        player1.getHero().setHasAttacked(0);
        player2.getHero().setHasAttacked(0);
    }

    /**
     * a method that checks if the enemy has a tank on the game board
     * @param idx
     * @return
     */
    public int checkTank(final int idx) {
        int hasTank = 0;
        if (idx == 1) {
            for (Card card : this.getRow2()) {
                if (card.getIsTank() == 1) {
                    hasTank = 1;
                    break;
                }
            }
            for (Card card : this.getRow3()) {
                if (card.getIsTank() == 1) {
                    hasTank = 1;
                    break;
                }
            }
        }

        if (idx == 2) {
            for (Card card : this.getRow0()) {
                if (card.getIsTank() == 1) {
                    hasTank = 1;
                    break;
                }
            }
            for (Card card : this.getRow1()) {
                if (card.getIsTank() == 1) {
                    hasTank = 1;
                    break;
                }
            }
        }

        return hasTank;
    }

    /**
     * row0 getter
     * @return
     */
    public ArrayList<Card> getRow0() {
        return row0;
    }

    /**
     * row1 getter
     * @return
     */
    public ArrayList<Card> getRow1() {
        return row1;
    }

    /**
     * row2 getter
     * @return
     */
    public ArrayList<Card> getRow2() {
        return row2;
    }

    /**
     * row3 getter
     * @return
     */
    public ArrayList<Card> getRow3() {
        return row3;
    }

    /**
     * round number getter
     * @return
     */
    public int getRound() {
        return round;
    }

    /**
     * turn counter getter
     * @return
     */
    public int getCnt() {
        return cnt;
    }

    /**
     * turn getter
     * @return
     */
    public int getPlayerTurn() {
        return playerTurn;
    }

    /**
     * row0 setter
     * @param row0
     */
    public void setRow0(final ArrayList<Card> row0) {
        this.row0 = row0;
    }

    /**
     * row1 setter
     * @param row1
     */
    public void setRow1(final ArrayList<Card> row1) {
        this.row1 = row1;
    }

    /**
     * row2 setter
     * @param row2
     */
    public void setRow2(final ArrayList<Card> row2) {
        this.row2 = row2;
    }

    /**
     * row3 setter
     * @param row3
     */
    public void setRow3(final ArrayList<Card> row3) {
        this.row3 = row3;
    }

    /**
     * round number getter
     * @param round
     */
    public void setRound(final int round) {
        this.round = round;
    }

    /**
     * turn counter setter
     * @param cnt
     */
    public void setCnt(final int cnt) {
        this.cnt = cnt;
    }

    /**
     * turn setter
     * @param playerTurn
     */
    public void setPlayerTurn(final int playerTurn) {
        this.playerTurn = playerTurn;
    }
}
