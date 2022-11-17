package main;

import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;

public class Debug {

    /**
     * a method that returns the deck of cards based on the given playerIdx
     * @param playerIdx
     * @param player1
     * @param player2
     * @return
     */
    public ArrayList<Card> getPlayerDeck(final int playerIdx, final Player player1,
                                         final Player player2) {
        if (playerIdx == 1) {
            return player1.getDeck();
        } else {
            return player2.getDeck();
        }
    }

    /**
     * a method that returns the hand of cards based on the given playerIdx
     * @param playerIdx
     * @param player1
     * @param player2
     * @return
     */
    public ArrayList<Card> getCardsInHand(final int playerIdx, final Player player1,
                                          final Player player2) {
        ArrayList<Card> hand = new ArrayList<Card>();
        if (playerIdx == 1) {
            for (int i = 0; i < player1.getHand().size(); i++) {
                hand.add(player1.getHand().get(i));
            }
        }
        if (playerIdx == 2) {
            for (int i = 0; i < player2.getHand().size(); i++) {
                hand.add(player2.getHand().get(i));
            }
        }
        return hand;
    }

    /**
     * a method that returns the hero card based on the given playerIdx
     * @param playerIdx
     * @param player1
     * @param player2
     * @return
     */
    public Hero getPlayerHero(final int playerIdx, final Player player1, final Player player2) {
        if (playerIdx == 1) {
            return player1.getHero();
        } else {
            return player2.getHero();
        }
    }

    /**
     * a method that returns which player's turn it is currently
     * @param gameBoard
     * @return
     */
    public int getPlayerTurn(final GameBoard gameBoard) {
        return gameBoard.getPlayerTurn();
    }

    /**
     * a method that returns the current total mana of a player based on the given playerIdx
     * @param playerIdx
     * @param player1
     * @param player2
     * @return
     */
    public int getPlayerMana(final int playerIdx, final Player player1, final Player player2) {
        if (playerIdx == 1) {
            return player1.getTotalMana();
        } else {
            return player2.getTotalMana();
        }
    }

    /**
     * a method that builds and returns the 4x5 array that represents the game board by adding
     * all the rows together
     * @param gameBoard
     * @return
     */
    public ArrayList<ArrayList<Card>> getCardsOnTable(final GameBoard gameBoard) {
        ArrayList<ArrayList<Card>> board = new ArrayList<ArrayList<Card>>();
        board.add(gameBoard.getRow0());
        board.add(gameBoard.getRow1());
        board.add(gameBoard.getRow2());
        board.add(gameBoard.getRow3());
        return board;
    }

    /**
     * a method that returns the hand of environment cards based on the given playerIdx
     * @param playerIdx
     * @param player1
     * @param player2
     * @return
     */
    public ArrayList<Card> getEnvironmentCardsInHand(final int playerIdx, final Player player1,
                                                     final Player player2) {
        ArrayList<Card> environmentCards = new ArrayList<Card>();
        if (playerIdx == 1) {
            for (int i = 0; i < player1.getHand().size(); i++) {
                if (player1.getHand().get(i).getIsEnvironment() == 1) {
                    environmentCards.add(player1.getHand().get(i));
                }
            }
        }
        if (playerIdx == 2) {
            for (int i = 0; i < player2.getHand().size(); i++) {
                if (player2.getHand().get(i).getIsEnvironment() == 1) {
                    environmentCards.add(player2.getHand().get(i));
                }
            }
        }
        return environmentCards;
    }

    /**
     * a method that returns a cards based on its X and Y coordinates on the game board
     * @param gameBoard
     * @param x
     * @param y
     * @param node
     * @return
     */
    public ObjectNode getCardAtPosition(final GameBoard gameBoard, final int x, final int y,
                                        final ObjectNode node) {
        if (x == 0) {
            gameBoard.getRow0().get(y).outputCard(node);
        }
        if (x == 1) {
            gameBoard.getRow1().get(y).outputCard(node);
        }
        if (x == 2) {
            gameBoard.getRow2().get(y).outputCard(node);
        }
        if (x == (1 + 2)) {
            gameBoard.getRow3().get(y).outputCard(node);
        }
        return node;
    }

    /**
     * a method that returns an array containing all the frozen cards on the game board
     * @param gameBoard
     * @return
     */
    public ArrayList<Card> getFrozenCardsOnTable(final GameBoard gameBoard) {
        ArrayList<Card> frozenCards = new ArrayList<>();
        for (int i = 0; i < gameBoard.getRow0().size(); i++) {
            if (gameBoard.getRow0().get(i).getIsFrozen() != 0) {
                frozenCards.add(gameBoard.getRow0().get(i));
            }
        }
        for (int i = 0; i < gameBoard.getRow1().size(); i++) {
            if (gameBoard.getRow1().get(i).getIsFrozen() != 0) {
                frozenCards.add(gameBoard.getRow1().get(i));
            }
        }
        for (int i = 0; i < gameBoard.getRow2().size(); i++) {
            if (gameBoard.getRow2().get(i).getIsFrozen() != 0) {
                frozenCards.add(gameBoard.getRow2().get(i));
            }
        }
        for (int i = 0; i < gameBoard.getRow3().size(); i++) {
            if (gameBoard.getRow3().get(i).getIsFrozen() != 0) {
                frozenCards.add(gameBoard.getRow3().get(i));
            }
        }
        return frozenCards;
    }
}
