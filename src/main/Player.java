package main;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

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

    /**
     * a method that is called each new round that removes a card from the deck
     * and puts it into the player's hand
     */
    public void drawCard() {
        if (this.deck.size() != 0) {
            Card card = this.deck.remove(0);
            this.hand.add(card);
        }
    }

    /**
     * a method that adds mana to the player based on the current round number
     * @param round
     */
    public void addMana(final int round) {
        int maxRound = 2 * 2 * 2 + 2;
        if (round <= maxRound) {
            this.totalMana += round;
        } else {
            this.totalMana += maxRound;
        }
    }

    /**
     * a method that removes a card from the hand based on the given handIdx and puts it
     * on the game board in the corresponding row
     * @param handIdx
     * @param gameBoard
     * @param player
     */
    public void placeCard(final int handIdx, final GameBoard gameBoard, final Player player) {
        Card card = this.hand.remove(handIdx);
        if (gameBoard.getPlayerTurn() == 1 && card.getIsBackRow() == 1) {
            gameBoard.getRow3().add(card);
        }
        if (gameBoard.getPlayerTurn() == 1 && card.getIsFrontRow() == 1) {
            gameBoard.getRow2().add(card);
        }
        if (gameBoard.getPlayerTurn() == 2 && card.getIsFrontRow() == 1) {
            gameBoard.getRow1().add(card);
        }
        if (gameBoard.getPlayerTurn() == 2 && card.getIsBackRow() == 1) {
            gameBoard.getRow0().add(card);
        }
        player.setTotalMana(player.getTotalMana() - card.getMana());
    }

    /**
     * a method that calls the generic method useEnvironmentCardAbility
     * @param handIdx
     * @param gameBoard
     * @param player
     * @param affectedRow
     * @param node
     * @param output
     */
    public void useEnvironmentCard(final int handIdx, final GameBoard gameBoard,
                                   final Player player, final int affectedRow,
                                   final ObjectNode node, final ArrayNode output) {
        Card card = this.hand.get(handIdx);
        int error = card.useEnvironmentCardAbility(gameBoard, player, affectedRow, node,
                handIdx, output);

        if (error != -1) {
            player.setTotalMana(player.getTotalMana() - card.getMana());
            this.hand.remove(handIdx);
        }
    }

    /**
     * deck getter
     * @return
     */
    public ArrayList<Card> getDeck() {
        return deck;
    }

    /**
     * hand getter
     * @return
     */
    public ArrayList<Card> getHand() {
        return hand;
    }

    /**
     * totalMana getter
     * @return
     */
    public int getTotalMana() {
        return totalMana;
    }

    /**
     * hero getter
     * @return
     */
    public Hero getHero() {
        return hero;
    }

    /**
     * shuffleSeed getter
     * @return
     */
    public int getShuffleSeed() {
        return shuffleSeed;
    }

    /**
     * deck setter
     * @param deck
     */
    public void setDeck(final ArrayList<Card> deck) {
        this.deck = deck;
    }

    /**
     * hand setter
     * @param hand
     */
    public void setHand(final ArrayList<Card> hand) {
        this.hand = hand;
    }

    /**
     * totalMana setter
     * @param totalMana
     */
    public void setTotalMana(final int totalMana) {
        this.totalMana = totalMana;
    }

    /**
     * hero setter
     * @param hero
     */
    public void setHero(final Hero hero) {
        this.hero = hero;
    }

    /**
     * shuffleSeed setter
     * @param shuffleSeed
     */
    public void setShuffleSeed(final int shuffleSeed) {
        this.shuffleSeed = shuffleSeed;
    }
}
