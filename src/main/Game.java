package main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Game {


    ObjectMapper objectMapper;
    Input input;
    ArrayNode output;

    int playerOneWins = 0, playerTwoWins = 0, totalGamesPlayed = 0;

    public Game(ObjectMapper objectMapper, Input input, ArrayNode output) {
        this.objectMapper = objectMapper;
        this.input = input;
        this.output = output;
    }

    public void chooseDeck(StartGameInput startGame, DecksInput deck, Player player, int playerIdx) {
        ArrayList<CardInput> chosenDeck = new ArrayList<>();
        chosenDeck = deck.getDecks().get(playerIdx);
        for(int i = 0; i < chosenDeck.size(); i++) {
            String name = chosenDeck.get(i).getName();
            Card card = null;
            switch (name) {
                case "Winterfell":
                    card = new Winterfell(chosenDeck.get(i));
                    break;
                case "Firestorm":
                    card = new Firestorm(chosenDeck.get(i));
                    break;
                case "Heart Hound":
                    card = new HeartHound(chosenDeck.get(i));
                    break;
                case "The Ripper":
                    card = new TheRipper(chosenDeck.get(i));
                    break;
                case "Miraj":
                    card = new Miraj(chosenDeck.get(i));
                    break;
                case "The Cursed One":
                    card = new TheCursedOne(chosenDeck.get(i));
                    break;
                case "Disciple":
                    card = new Disciple(chosenDeck.get(i));
                    break;
                case "Sentinel":
                    card = new Sentinel(chosenDeck.get(i));
                    break;
                case "Berserker":
                    card = new Berserker(chosenDeck.get(i));
                    break;
                case "Goliath":
                    card = new Goliath(chosenDeck.get(i));
                    break;
                case "Warden":
                    card = new Warden(chosenDeck.get(i));
                    break;
            }
            player.getDeck().add(card);
        }
        Collections.shuffle(player.getDeck(), new Random(startGame.getShuffleSeed()));
    }
    public void chooseHero(StartGameInput startGame, Player player1, Player player2) {
        Hero hero1 = null;
        String name1 = startGame.getPlayerOneHero().getName();
        switch (name1) {
            case "Lord Royce":
                hero1 = new LordRoyce(startGame.getPlayerOneHero());
                break;
            case "Empress Thorina":
                hero1 = new EmpressThorina(startGame.getPlayerOneHero());
                break;
            case "General Kocioraw":
                hero1 = new GeneralKocioraw(startGame.getPlayerOneHero());
                break;
            case "King Mudface":
                hero1 = new KingMudface(startGame.getPlayerOneHero());
                break;
        }
        player1.setHero(hero1);

        Hero hero2 = null;
        String name2 = startGame.getPlayerTwoHero().getName();
        switch (name2) {
            case "Lord Royce":
                hero2 = new LordRoyce(startGame.getPlayerTwoHero());
                break;
            case "Empress Thorina":
                hero2 = new EmpressThorina(startGame.getPlayerTwoHero());
                break;
            case "General Kocioraw":
                hero2 = new GeneralKocioraw(startGame.getPlayerTwoHero());
                break;
            case "King Mudface":
                hero2 = new KingMudface(startGame.getPlayerTwoHero());
                break;
        }
        player2.setHero(hero2);
    }

    public void Start() {

        DecksInput playerOneDecks = input.getPlayerOneDecks();
        DecksInput playerTwoDecks = input.getPlayerTwoDecks();
        ArrayList<GameInput> games = input.getGames();

        for(int i = 0; i < games.size(); i++) {
            Debug debug = new Debug();
            Player player1 = new Player();
            Player player2 = new Player();

            StartGameInput startGame = new StartGameInput();
            startGame = games.get(i).getStartGame();

            ArrayList<ActionsInput> actions = new ArrayList<>();
            actions = games.get(i).getActions();

            chooseDeck(startGame, playerOneDecks, player1, startGame.getPlayerOneDeckIdx());
            chooseDeck(startGame, playerTwoDecks, player2, startGame.getPlayerTwoDeckIdx());
            chooseHero(startGame, player1, player2);

            GameBoard gameBoard = new GameBoard(startGame);
            gameBoard.nextRound(player1, player2);


            for(int j = 0; j < actions.size(); j++) {

                ActionsInput curentAction = actions.get(j);
                int playerIdx = curentAction.getPlayerIdx();


                if (curentAction.getCommand().equals("getPlayerDeck")) {
                    ObjectNode node = objectMapper.createObjectNode();
                    node.put("command", "getPlayerDeck");
                    node.put("playerIdx", playerIdx);
                    node.putPOJO("output", debug.getPlayerDeck(playerIdx, player1, player2));
                    output.add(node);
                }

                if (curentAction.getCommand().equals("getCardsInHand")) {
                    ObjectNode node = objectMapper.createObjectNode();
                    node.put("command", "getCardsInHand");
                    node.put("playerIdx", playerIdx);
                    ArrayList<Card> cards = debug.getCardsInHand(playerIdx, player1, player2);

                    ArrayNode cardsNode = node.putArray("output");
                    for (int o = 0; o < cards.size(); o++)
                        cardsNode.add(cards.get(o).outputCard2());

                    output.add(node);
                }

                if (curentAction.getCommand().equals("getPlayerHero")) {
                    ObjectNode node = objectMapper.createObjectNode();
                    node.put("command", "getPlayerHero");
                    node.put("playerIdx", curentAction.getPlayerIdx());
                    if (playerIdx == 1)
                        node = player1.getHero().outputHero(node);
                    if (playerIdx == 2)
                        node = player2.getHero().outputHero(node);
                    output.add(node);
                }

                if (curentAction.getCommand().equals("getPlayerTurn")) {
                    ObjectNode node = objectMapper.createObjectNode();
                    node.put("command", "getPlayerTurn");
                    node.put("output", debug.getPlayerTurn(gameBoard));
                    output.add(node);
                }

                if (curentAction.getCommand().equals("endPlayerTurn")) {
                    gameBoard.endPlayerTurn(player1, player2);
                }

                if (curentAction.getCommand().equals("placeCard")) {
                    ObjectNode node = objectMapper.createObjectNode();
                    int handIdx = curentAction.getHandIdx();

                    Card card = gameBoard.getCurrentPlayer(player1, player2).getHand().get(handIdx);

                    if (card.getIsEnvironment() == 1) {
                        node.put("command", "placeCard");
                        node.put("handIdx", handIdx);
                        node.put("error", "Cannot place environment card on table.");
                        output.add(node);
                    } else if (card.getMana() > gameBoard.getCurrentPlayer(player1, player2).getTotalMana()) {
                        node.put("command", "placeCard");
                        node.put("handIdx", handIdx);
                        node.put("error", "Not enough mana to place card on table.");
                        output.add(node);
                    } else if ((card.getIsFrontRow() == 1 && gameBoard.getPlayerTurn() == 1 && gameBoard.getRow2().size() == 5) ||
                            (card.getIsFrontRow() == 1 && gameBoard.getPlayerTurn() == 2 && gameBoard.getRow1().size() == 5) ||
                            (card.getIsBackRow() == 1 && gameBoard.getPlayerTurn() == 1 && gameBoard.getRow3().size() == 5) ||
                            (card.getIsBackRow() == 1 && gameBoard.getPlayerTurn() == 2 && gameBoard.getRow0().size() == 5)) {
                        node.put("command", "placeCard");
                        node.put("handIdx", handIdx);
                        node.put("error", "Cannot place card on table since row is full.");
                        output.add(node);
                    } else {
                        gameBoard.getCurrentPlayer(player1, player2).placeCard(handIdx, gameBoard, gameBoard.getCurrentPlayer(player1, player2));
                    }

                }

                if (curentAction.getCommand().equals("getPlayerMana")) {
                    ObjectNode node = objectMapper.createObjectNode();
                    node.put("command", "getPlayerMana");
                    node.put("playerIdx", curentAction.getPlayerIdx());
                    node.putPOJO("output", debug.getPlayerMana(playerIdx, player1, player2));
                    output.add(node);
                }

                if (curentAction.getCommand().equals("getCardsOnTable")) {
                    ObjectNode node = objectMapper.createObjectNode();
                    node.put("command", "getCardsOnTable");
                    node.putPOJO("output", debug.getCardsOnTable(gameBoard));
                    output.add(node);
                }

                if (curentAction.getCommand().equals("getEnvironmentCardsInHand")) {
                    ObjectNode node = objectMapper.createObjectNode();
                    node.put("command", "getEnvironmentCardsInHand");
                    node.put("playerIdx", curentAction.getPlayerIdx());
                    node.putPOJO("output", debug.getEnvironmentCardsInHand(playerIdx, player1, player2));
                    output.add(node);
                }

                if (curentAction.getCommand().equals("getCardAtPosition")) {
                    ObjectNode node = objectMapper.createObjectNode();

                    if (curentAction.getX() > 4 || curentAction.getX() < 0 ||
                            (curentAction.getX() == 0 && (curentAction.getY() > gameBoard.getRow0().size() || curentAction.getY() < 0)) ||
                            (curentAction.getX() == 1 && (curentAction.getY() > gameBoard.getRow1().size() || curentAction.getY() < 0)) ||
                            (curentAction.getX() == 2 && (curentAction.getY() > gameBoard.getRow2().size() || curentAction.getY() < 0)) ||
                            (curentAction.getX() == 3 && (curentAction.getY() > gameBoard.getRow3().size() || curentAction.getY() < 0))) {
                        node.put("command", "getCardAtPosition");
                        node.put("x", curentAction.getX());
                        node.put("y", curentAction.getY());
                        node.put("output", "No card available at that position.");
                        output.add(node);
                    } else {
                        node.put("command", "getCardAtPosition");
                        node.put("x", curentAction.getX());
                        node.put("y", curentAction.getY());
                        node = debug.getCardAtPosition(gameBoard, curentAction.getX(), curentAction.getY(), node);
                        output.add(node);
                    }

                }

                if (curentAction.getCommand().equals("useEnvironmentCard")) {
                    ObjectNode node = objectMapper.createObjectNode();
                    int handIdx = curentAction.getHandIdx();
                    int affectedRow = curentAction.getAffectedRow();

                    Card card = gameBoard.getCurrentPlayer(player1, player2).getHand().get(handIdx);

                    if (card.getIsEnvironment() != 1) {
                        node.put("command", "useEnvironmentCard");
                        node.put("handIdx", handIdx);
                        node.put("affectedRow", affectedRow);
                        node.put("error", "Chosen card is not of type environment.");
                        output.add(node);
                    } else if (card.getMana() > gameBoard.getCurrentPlayer(player1, player2).getTotalMana()) {
                        node.put("command", "useEnvironmentCard");
                        node.put("handIdx", handIdx);
                        node.put("affectedRow", affectedRow);
                        node.put("error", "Not enough mana to use environment card.");
                        output.add(node);
                    } else if (gameBoard.getPlayerTurn() == 1 && (affectedRow == 2 || affectedRow == 3) ||
                            (gameBoard.getPlayerTurn() == 2 && (affectedRow == 0 || affectedRow == 1))) {
                        node.put("command", "useEnvironmentCard");
                        node.put("handIdx", handIdx);
                        node.put("affectedRow", affectedRow);
                        node.put("error", "Chosen row does not belong to the enemy.");
                        output.add(node);
                    } else {
                        gameBoard.getCurrentPlayer(player1, player2).useEnvironmentCard(handIdx, gameBoard, gameBoard.getCurrentPlayer(player1, player2), affectedRow, node, output);
                    }

                    gameBoard.removeDead();

                }

                if (curentAction.getCommand().equals("getFrozenCardsOnTable")) {
                    ObjectNode node = objectMapper.createObjectNode();
                    node.put("command", "getFrozenCardsOnTable");
                    node.putPOJO("output", debug.getFrozenCardsOnTable(gameBoard));
                    output.add(node);
                }

                if (curentAction.getCommand().equals("cardUsesAttack")) {
                    ObjectNode node = objectMapper.createObjectNode();

                    CardWithCoords attacker = new CardWithCoords(curentAction.getCardAttacker().getX(), curentAction.getCardAttacker().getY(), gameBoard);
                    CardWithCoords attacked = new CardWithCoords(curentAction.getCardAttacked().getX(), curentAction.getCardAttacked().getY(), gameBoard);

                    if ((attacker.getX() == 0 || attacker.getX() == 1) && (attacked.getX() == 0 || attacked.getX() == 1) ||
                            (attacker.getX() == 2 || attacker.getX() == 3) && (attacked.getX() == 2 || attacked.getX() == 3)){
                        node.put("command", "cardUsesAttack");
                        node.putPOJO("cardAttacker", attacker);
                        node.putPOJO("cardAttacked", attacked);
                        node.put("error", "Attacked card does not belong to the enemy.");
                        output.add(node);
                    } else if (attacker.getCard().getHasAttacked() == 1) {
                        node.put("command", "cardUsesAttack");
                        node.putPOJO("cardAttacker", attacker);
                        node.putPOJO("cardAttacked", attacked);
                        node.put("error", "Attacker card has already attacked this turn.");
                        output.add(node);
                    } else if (attacker.getCard().getIsFrozen() != 0) {
                        node.put("command", "cardUsesAttack");
                        node.putPOJO("cardAttacker", attacker);
                        node.putPOJO("cardAttacked", attacked);
                        node.put("error", "Attacker card is frozen.");
                        output.add(node);
                    } else if(((attacker.getX() == 0 || attacker.getX() == 1) && gameBoard.checkTank(1) == 1 && attacked.getCard().getIsTank() != 1) ||
                    ((attacker.getX() == 2 || attacker.getX() == 3) && gameBoard.checkTank(2) == 1 && attacked.getCard().getIsTank() != 1)) {
                        node.put("command", "cardUsesAttack");
                        node.putPOJO("cardAttacker", attacker);
                        node.putPOJO("cardAttacked", attacked);
                        node.put("error", "Attacked card is not of type 'Tank'.");
                        output.add(node);
                    } else {
                        attacker.getCard().setHasAttacked(1);
                        attacked.getCard().setHealth(attacked.getCard().getHealth() - attacker.getCard().getAttackDamage());
                    }
                    gameBoard.removeDead();
                }

                if (curentAction.getCommand().equals("cardUsesAbility")) {
                    ObjectNode node = objectMapper.createObjectNode();
                    int ok = 0;

                    CardWithCoords attacker = new CardWithCoords(curentAction.getCardAttacker().getX(), curentAction.getCardAttacker().getY(), gameBoard);
                    CardWithCoords attacked = new CardWithCoords(curentAction.getCardAttacked().getX(), curentAction.getCardAttacked().getY(), gameBoard);

                    if (attacker.getCard().getIsFrozen() != 0 && ok == 0) {
                        ok = 1;
                        node.put("command", "cardUsesAbility");
                        node.putPOJO("cardAttacker", attacker);
                        node.putPOJO("cardAttacked", attacked);
                        node.put("error", "Attacker card is frozen.");
                        output.add(node);
                    }
                    if (attacker.getCard().getHasAttacked() == 1 && ok == 0) {
                        ok = 1;
                        node.put("command", "cardUsesAbility");
                        node.putPOJO("cardAttacker", attacker);
                        node.putPOJO("cardAttacked", attacked);
                        node.put("error", "Attacker card has already attacked this turn.");
                        output.add(node);
                    }
                    if (attacker.getCard().getName().equals("Disciple") &&
                            ((gameBoard.getPlayerTurn() == 1 && (attacked.getX() == 0 || attacked.getX() == 1)) ||
                                    (gameBoard.getPlayerTurn() == 2 && (attacked.getX() == 2 || attacked.getX() == 3))) && ok == 0) {
                        ok = 1;
                        node.put("command", "cardUsesAbility");
                        node.putPOJO("cardAttacker", attacker);
                        node.putPOJO("cardAttacked", attacked);
                        node.put("error", "Attacked card does not belong to the current player.");
                        output.add(node);
                    }
                    if ( (attacker.getCard().getName().equals("The Ripper") || attacker.getCard().getName().equals("Miraj") ||
                            attacker.getCard().getName().equals("The Cursed One"))) {
                        if ((((attacker.getX() == 0 || attacker.getX() == 1) && (attacked.getX() == 0 || attacked.getX() == 1)) ||
                                ((attacker.getX() == 2 || attacker.getX() == 3) && (attacked.getX() == 2 || attacked.getX() == 3))) && ok == 0) {
                            ok = 1;
                            node.put("command", "cardUsesAbility");
                            node.putPOJO("cardAttacker", attacker);
                            node.putPOJO("cardAttacked", attacked);
                            node.put("error", "Attacked card does not belong to the enemy.");
                            output.add(node);
                        }
                        if((((attacker.getX() == 0 || attacker.getX() == 1) && gameBoard.checkTank(1) == 1 && attacked.getCard().getIsTank() != 1) ||
                                ((attacker.getX() == 2 || attacker.getX() == 3) && gameBoard.checkTank(2) == 1 && attacked.getCard().getIsTank() != 1)) && ok == 0) {
                            ok = 1;
                            node.put("command", "cardUsesAbility");
                            node.putPOJO("cardAttacker", attacker);
                            node.putPOJO("cardAttacked", attacked);
                            node.put("error", "Attacked card is not of type 'Tank'.");
                            output.add(node);
                        }
                    }
                    if (ok == 0) {
                        attacker.getCard().setHasAttacked(1);
                        attacker.getCard().useCardAbility(attacked);
                    }
                    gameBoard.removeDead();
                }

                if (curentAction.getCommand().equals("useAttackHero")) {
                    ObjectNode node = objectMapper.createObjectNode();

                    CardWithCoords attacker = new CardWithCoords(curentAction.getCardAttacker().getX(), curentAction.getCardAttacker().getY(), gameBoard);

                    if (attacker.getCard().getIsFrozen() != 0) {
                        node.put("command", "useAttackHero");
                        node.putPOJO("cardAttacker", attacker);
                        node.put("error", "Attacker card is frozen.");
                        output.add(node);
                    } else if (attacker.getCard().getHasAttacked() == 1) {
                        node.put("command", "useAttackHero");
                        node.putPOJO("cardAttacker", attacker);
                        node.put("error", "Attacker card has already attacked this turn.");
                        output.add(node);
                    } else if (((attacker.getX() == 0 || attacker.getX() == 1) && gameBoard.checkTank(1) == 1) ||
                            ((attacker.getX() == 2 || attacker.getX() == 3) && gameBoard.checkTank(2) == 1)) {
                        node.put("command", "useAttackHero");
                        node.putPOJO("cardAttacker", attacker);
                        node.put("error", "Attacked card is not of type 'Tank'.");
                        output.add(node);
                    } else {
                        attacker.getCard().setHasAttacked(1);

                        if ((attacker.getX() == 0 || attacker.getX() == 1))
                            player1.getHero().setHealth(player1.getHero().getHealth() - attacker.getCard().getAttackDamage());
                        if ((attacker.getX() == 2 || attacker.getX() == 3))
                            player2.getHero().setHealth(player2.getHero().getHealth() - attacker.getCard().getAttackDamage());
                    }

                    if (player1.getHero().getHealth() <= 0) {
                        node.put("gameEnded", "Player two killed the enemy hero.");
                        output.add(node);
                        this.playerTwoWins++;
                        this.totalGamesPlayed++;
                    }
                    if (player2.getHero().getHealth() <= 0) {
                        node.put("gameEnded", "Player one killed the enemy hero.");
                        output.add(node);
                        this.playerOneWins++;
                        this.totalGamesPlayed++;
                    }
                }

                if (curentAction.getCommand().equals("useHeroAbility")) {
                    ObjectNode node = objectMapper.createObjectNode();
                    int affectedRow = curentAction.getAffectedRow();

                    if (gameBoard.getCurrentPlayer(player1, player2).getHero().getMana() > gameBoard.getCurrentPlayer(player1, player2).getTotalMana()) {
                        node.put("command", "useHeroAbility");
                        node.put("affectedRow", affectedRow);
                        node.put("error", "Not enough mana to use hero's ability.");
                        output.add(node);
                    } else if (gameBoard.getCurrentPlayer(player1, player2).getHero().getHasAttacked() == 1) {
                        node.put("command", "useHeroAbility");
                        node.put("affectedRow", affectedRow);
                        node.put("error", "Hero has already attacked this turn.");
                        output.add(node);
                    } else if (((gameBoard.getCurrentPlayer(player1, player2).getHero().getName().equals("Lord Royce")) ||
                            (gameBoard.getCurrentPlayer(player1, player2).getHero().getName().equals("Empress Thorina"))) &&
                            ((gameBoard.getPlayerTurn() == 1 && (affectedRow == 2 || affectedRow == 3)) ||
                            (gameBoard.getPlayerTurn() == 2 && (affectedRow == 0 || affectedRow == 1)))) {
                        node.put("command", "useHeroAbility");
                        node.put("affectedRow", affectedRow);
                        node.put("error", "Selected row does not belong to the enemy.");
                        output.add(node);
                    } else if (((gameBoard.getCurrentPlayer(player1, player2).getHero().getName().equals("General Kocioraw")) ||
                            (gameBoard.getCurrentPlayer(player1, player2).getHero().getName().equals("King Mudface"))) &&
                            ((gameBoard.getPlayerTurn() == 2 && (affectedRow == 2 || affectedRow == 3)) ||
                                    (gameBoard.getPlayerTurn() == 1 && (affectedRow == 0 || affectedRow == 1)))) {
                        node.put("command", "useHeroAbility");
                        node.put("affectedRow", affectedRow);
                        node.put("error", "Selected row does not belong to the current player.");
                        output.add(node);
                    } else {
                        gameBoard.getCurrentPlayer(player1, player2).getHero().setHasAttacked(1);
                        gameBoard.getCurrentPlayer(player1, player2).getHero().useHeroAbility(gameBoard, affectedRow, gameBoard.getCurrentPlayer(player1, player2));
                    }
                }

                if (curentAction.getCommand().equals("getPlayerOneWins")) {
                    ObjectNode node = objectMapper.createObjectNode();
                    node.put("command", "getPlayerOneWins");
                    node.put("output", this.playerOneWins);
                    output.add(node);
                }

                if (curentAction.getCommand().equals("getPlayerTwoWins")) {
                    ObjectNode node = objectMapper.createObjectNode();
                    node.put("command", "getPlayerTwoWins");
                    node.put("output", this.playerTwoWins);
                    output.add(node);
                }

                if (curentAction.getCommand().equals("getTotalGamesPlayed")) {
                    ObjectNode node = objectMapper.createObjectNode();
                    node.put("command", "getTotalGamesPlayed");
                    node.put("output", this.totalGamesPlayed);
                    output.add(node);
                }

            }

        }
    }
}

