package main;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;
import fileio.DecksInput;
import fileio.StartGameInput;

import java.util.ArrayList;

public class Player {
    private ArrayList<Card> deck;
    private ArrayList<Card> hand;
    private int totalMana;
    private Hero hero;
    private int shuffleSeed;

    public Player() {
        deck = new ArrayList<Card>();
        hand = new ArrayList<Card>();
        totalMana = 0;
        hero = null;
        shuffleSeed = 0;
    }

    public void drawCard() {
        if (this.deck.size() != 0) {
            Card card = this.deck.remove(0);
            this.hand.add(card);
        }
    }

    public void addMana(int round) {
        if (round <= 10)
            this.totalMana += round;
        else
            this.totalMana += 10;
    }

    public void placeCard(int handIdx, GameBoard gameBoard, Player player) {
        Card card = this.hand.remove(handIdx);
        if (gameBoard.getPlayerTurn() == 1 && card.getIsBackRow() == 1)
            gameBoard.getRow3().add(card);
        if (gameBoard.getPlayerTurn() == 1 && card.getIsFrontRow() == 1)
            gameBoard.getRow2().add(card);
        if (gameBoard.getPlayerTurn() == 2 && card.getIsFrontRow() == 1)
            gameBoard.getRow1().add(card);
        if (gameBoard.getPlayerTurn() == 2 && card.getIsBackRow() == 1)
            gameBoard.getRow0().add(card);
        player.setTotalMana(player.getTotalMana() - card.getMana());
    }

    public void useEnvironmentCard(int handIdx, GameBoard gameBoard, Player player, int affectedRow, ObjectNode node, ArrayNode output) {
        Card card = this.hand.get(handIdx);
        int error = card.useEnvironmentCardAbility(gameBoard, player, affectedRow, node, handIdx, output);

        if (error != 404) {
            player.setTotalMana(player.getTotalMana() - card.getMana());
            this.hand.remove(handIdx);
        }
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public int getTotalMana() {
        return totalMana;
    }

    public Hero getHero() {
        return hero;
    }

    public int getShuffleSeed() {
        return shuffleSeed;
    }

    public void setDeck(ArrayList<Card> deck) {
        this.deck = deck;
    }

    public void setHand(ArrayList<Card> hand) {
        this.hand = hand;
    }

    public void setTotalMana(int totalMana) {
        this.totalMana = totalMana;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public void setShuffleSeed(int shuffleSeed) {
        this.shuffleSeed = shuffleSeed;
    }
}
