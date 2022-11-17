package main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;

public class Debug {

    public ArrayList<Card> getPlayerDeck(int playerIdx, Player player1, Player player2) {
        if (playerIdx == 1)
            return player1.getDeck();
        else
            return player2.getDeck();
    }

    public ArrayList<Card> getCardsInHand(int playerIdx, Player player1, Player player2) {
        ArrayList<Card> hand = new ArrayList<Card>();
        if (playerIdx == 1)
            for(int i = 0; i < player1.getHand().size(); i++)
                hand.add(player1.getHand().get(i));
        if (playerIdx == 2)
            for (int i = 0; i < player2.getHand().size(); i++)
                hand.add(player2.getHand().get(i));

        return hand;
    }

    public Hero getPlayerHero(int playerIdx, Player player1, Player player2) {
        if (playerIdx == 1)
            return player1.getHero();
        else
            return player2.getHero();
    }

    public int getPlayerTurn(GameBoard gameBoard) {
        return gameBoard.getPlayerTurn();
    }

    public int getPlayerMana(int playerIdx, Player player1, Player player2) {
        if (playerIdx == 1)
            return player1.getTotalMana();
        else
            return player2.getTotalMana();
    }

    public ArrayList<ArrayList<Card>> getCardsOnTable(GameBoard gameBoard) {
        ArrayList<ArrayList<Card>> table = new ArrayList<ArrayList<Card>>();
        table.add(gameBoard.getRow0());
        table.add(gameBoard.getRow1());
        table.add(gameBoard.getRow2());
        table.add(gameBoard.getRow3());

        return table;
    }

    public ArrayList<Card> getEnvironmentCardsInHand(int playerIdx, Player player1, Player player2) {
        ArrayList<Card> environmentCards = new ArrayList<Card>();
        if (playerIdx == 1)
            for(int i = 0; i < player1.getHand().size(); i++)
                if (player1.getHand().get(i).getIsEnvironment() == 1)
                    environmentCards.add(player1.getHand().get(i));
        if (playerIdx == 2)
            for (int i = 0; i < player2.getHand().size(); i++)
                if (player2.getHand().get(i).getIsEnvironment() == 1)
                    environmentCards.add(player2.getHand().get(i));
        return environmentCards;
    }

    public ObjectNode getCardAtPosition(GameBoard gameBoard, int x, int y, ObjectNode node) {
        if (x == 0)
            gameBoard.getRow0().get(y).outputCard(node);
        if (x == 1)
            gameBoard.getRow1().get(y).outputCard(node);
        if (x == 2)
            gameBoard.getRow2().get(y).outputCard(node);
        if (x == 3) {
            gameBoard.getRow3().get(y).outputCard(node);
        }
        return node;
    }

    public ArrayList<Card> getFrozenCardsOnTable(GameBoard gameBoard) {
        ArrayList<Card> frozenCards = new ArrayList<>();
        for(int i = 0; i < gameBoard.getRow0().size(); i++)
            if (gameBoard.getRow0().get(i).getIsFrozen() != 0)
                frozenCards.add(gameBoard.getRow0().get(i));
        for(int i = 0; i < gameBoard.getRow1().size(); i++)
            if (gameBoard.getRow1().get(i).getIsFrozen() != 0)
                frozenCards.add(gameBoard.getRow1().get(i));
        for(int i = 0; i < gameBoard.getRow2().size(); i++)
            if (gameBoard.getRow2().get(i).getIsFrozen() != 0)
                frozenCards.add(gameBoard.getRow2().get(i));
        for(int i = 0; i < gameBoard.getRow3().size(); i++)
            if (gameBoard.getRow3().get(i).getIsFrozen() != 0)
                frozenCards.add(gameBoard.getRow3().get(i));

        return frozenCards;
    }
}
