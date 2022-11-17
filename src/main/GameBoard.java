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

    public GameBoard(StartGameInput startGame) {
        this.round = 0;
        this.cnt = 0;
        this.playerTurn = startGame.getStartingPlayer();
        this.row0 = new ArrayList<Card>(5);
        this.row1 = new ArrayList<Card>(5);
        this.row2 = new ArrayList<Card>(5);
        this.row3 = new ArrayList<Card>(5);

    }

    public void nextRound(Player player1, Player player2) {
        round++;
        player1.drawCard();
        player2.drawCard();
        player1.addMana(round);
        player2.addMana(round);

    }

    public void endPlayerTurn(Player player1, Player player2) {
        this.cnt++;
        if (this.playerTurn == 1)
            this.setPlayerTurn(2);
        else
            this.setPlayerTurn(1);
        if (this.cnt % 2 == 0)
            nextRound(player1, player2);
        unfreeze();
        removeDead();
        removeDead();
        resetAttacks(player1, player2);
    }

    public Player getCurrentPlayer(Player player1, Player player2) {
        if (this.playerTurn == 1){
            return player1;
        } else {
            return player2;
        }
    }

    public void unfreeze() {
        for(int i = 0; i < this.getRow0().size(); i++)
            if (this.getRow0().get(i).getIsFrozen() > 0)
                this.getRow0().get(i).setIsFrozen(this.getRow0().get(i).getIsFrozen() - 0.5);
        for(int i = 0; i < this.getRow1().size(); i++)
            if (this.getRow1().get(i).getIsFrozen() > 0)
                this.getRow1().get(i).setIsFrozen(this.getRow1().get(i).getIsFrozen() - 0.5);
        for(int i = 0; i < this.getRow2().size(); i++)
            if (this.getRow2().get(i).getIsFrozen() > 0)
                this.getRow2().get(i).setIsFrozen(this.getRow2().get(i).getIsFrozen() - 0.5);
        for(int i = 0; i < this.getRow3().size(); i++)
            if (this.getRow3().get(i).getIsFrozen() > 0)
                this.getRow3().get(i).setIsFrozen(this.getRow3().get(i).getIsFrozen() - 0.5);
    }

    public void removeDead() {
        for(int i = 0; i < this.getRow0().size(); i++)
            if (this.getRow0().get(i).getHealth() <= 0)
                this.getRow0().remove(i);
        for(int i = 0; i < this.getRow1().size(); i++)
            if (this.getRow1().get(i).getHealth() <= 0)
                this.getRow1().remove(i);
        for(int i = 0; i < this.getRow2().size(); i++)
            if (this.getRow2().get(i).getHealth() <= 0)
                this.getRow2().remove(i);
        for(int i = 0; i < this.getRow3().size(); i++)
            if (this.getRow3().get(i).getHealth() <= 0)
                this.getRow3().remove(i);
    }

    public void resetAttacks(Player player1, Player player2) {
        for(Card card : this.getRow0())
            card.setHasAttacked(0);
        for(Card card : this.getRow1())
            card.setHasAttacked(0);
        for(Card card : this.getRow2())
            card.setHasAttacked(0);
        for(Card card : this.getRow3())
            card.setHasAttacked(0);
        player1.getHero().setHasAttacked(0);
        player2.getHero().setHasAttacked(0);
    }

    public int checkTank(int idx) {
        int hasTank = 0;
        if (idx == 1) {
            for (Card card : this.getRow2())
                if (card.getIsTank() == 1) {
                    hasTank = 1;
                    break;
                }
            for (Card card : this.getRow3())
                if (card.getIsTank() == 1) {
                    hasTank = 1;
                    break;
                }
        }

        if (idx == 2) {
            for (Card card : this.getRow0())
                if (card.getIsTank() == 1) {
                    hasTank = 1;
                    break;
                }
            for (Card card : this.getRow1())
                if (card.getIsTank() == 1) {
                    hasTank = 1;
                    break;
                }
        }

        return hasTank;
    }

    public ArrayList<Card> getRow0() {
        return row0;
    }

    public ArrayList<Card> getRow1() {
        return row1;
    }

    public ArrayList<Card> getRow2() {
        return row2;
    }

    public ArrayList<Card> getRow3() {
        return row3;
    }

    public int getRound() {
        return round;
    }

    public int getCnt() {
        return cnt;
    }

    public int getPlayerTurn() {
        return playerTurn;
    }

    public void setRow0(ArrayList<Card> row0) {
        this.row0 = row0;
    }

    public void setRow1(ArrayList<Card> row1) {
        this.row1 = row1;
    }

    public void setRow2(ArrayList<Card> row2) {
        this.row2 = row2;
    }

    public void setRow3(ArrayList<Card> row3) {
        this.row3 = row3;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public void setPlayerTurn(int playerTurn) {
        this.playerTurn = playerTurn;
    }
}
