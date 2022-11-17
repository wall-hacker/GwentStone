package main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;
import main.cards.Card;
import main.cards.CardWithCoords;

import java.util.ArrayList;

public class Solver {

    private ObjectMapper objectMapper;
    private ArrayNode output;
    private Game game;

    public Solver(final ObjectMapper objectMapper, final ArrayNode output, final Game game) {
        this.objectMapper = objectMapper;
        this.output = output;
        this.game = game;
    }

    /**
     * solve the output for the getPlayerDeck command
     * @param playerIdx
     * @param player1
     * @param player2
     * @param debug
     */
    public void solveGetPlayerDeck(final int playerIdx, final Player player1, final Player player2,
                                   final Debug debug) {
        ObjectNode node = objectMapper.createObjectNode();
        node.put("command", "getPlayerDeck");
        node.put("playerIdx", playerIdx);
        node.putPOJO("output", debug.getPlayerDeck(playerIdx, player1, player2));
        output.add(node);
    }

    /**
     * solve the output for the getCardsInHand command
     * @param playerIdx
     * @param player1
     * @param player2
     * @param debug
     */
    public void solveGetCardsInHand(final int playerIdx, final Player player1, final Player player2,
                                    final Debug debug) {
        ObjectNode node = objectMapper.createObjectNode();
        node.put("command", "getCardsInHand");
        node.put("playerIdx", playerIdx);
        ArrayList<Card> cards = debug.getCardsInHand(playerIdx, player1, player2);
        ArrayNode cardsNode = node.putArray("output");
        for (int o = 0; o < cards.size(); o++) {
            cardsNode.add(cards.get(o).outputCard2());
        }
        output.add(node);
    }

    /**
     * solve the output for the getPlayerHero command
     * @param playerIdx
     * @param player1
     * @param player2
     */
    public void solveGetPlayerHero(final int playerIdx, final Player player1,
                                   final Player player2) {
        ObjectNode node = objectMapper.createObjectNode();
        node.put("command", "getPlayerHero");
        node.put("playerIdx", playerIdx);
        if (playerIdx == 1) {
            node = player1.getHero().outputHero(node);
        }
        if (playerIdx == 2) {
            node = player2.getHero().outputHero(node);
        }
        output.add(node);
    }

    /**
     * solve the output for the getPlayerTurn command
     * @param gameBoard
     * @param debug
     */
    public void solveGetPlayerTurn(final GameBoard gameBoard,
                                   final Debug debug) {
        ObjectNode node = objectMapper.createObjectNode();
        node.put("command", "getPlayerTurn");
        node.put("output", debug.getPlayerTurn(gameBoard));
        output.add(node);
    }

    /**
     * solve the output for the placeCard command
     * @param curentAction
     * @param gameBoard
     * @param player1
     * @param player2
     */
    public void solvePlaceCard(final ActionsInput curentAction, final GameBoard gameBoard,
                               final Player player1, final Player player2) {
        ObjectNode node = objectMapper.createObjectNode();
        int handIdx = curentAction.getHandIdx();
        Card card = gameBoard.getCurrentPlayer(player1, player2).getHand().get(handIdx);
        if (card.getIsEnvironment() == 1) {
            node.put("command", "placeCard");
            node.put("handIdx", handIdx);
            node.put("error", "Cannot place environment card on table.");
            output.add(node);
        } else if (card.getMana()
                > gameBoard.getCurrentPlayer(player1, player2).getTotalMana()) {
            node.put("command", "placeCard");
            node.put("handIdx", handIdx);
            node.put("error", "Not enough mana to place card on table.");
            output.add(node);
        } else if ((card.getIsFrontRow() == 1 && gameBoard.getPlayerTurn() == 1
                && gameBoard.getRow2().size() == (1 + 2 + 2))
                || (card.getIsFrontRow() == 1 && gameBoard.getPlayerTurn() == 2
                && gameBoard.getRow1().size() == (1 + 2 + 2))
                || (card.getIsBackRow() == 1 && gameBoard.getPlayerTurn() == 1
                && gameBoard.getRow3().size() == (1 + 2 + 2))
                || (card.getIsBackRow() == 1 && gameBoard.getPlayerTurn() == 2
                && gameBoard.getRow0().size() == (1 + 2 + 2))) {
            node.put("command", "placeCard");
            node.put("handIdx", handIdx);
            node.put("error", "Cannot place card on table since row is full.");
            output.add(node);
        } else {
            gameBoard.getCurrentPlayer(player1, player2).placeCard(handIdx, gameBoard,
                    gameBoard.getCurrentPlayer(player1, player2));
        }
    }

    /**
     * solve the output for the getPlayerMana command
     * @param debug
     * @param playerIdx
     * @param player1
     * @param player2
     */
    public void solveGetPlayerMana(final Debug debug, final int playerIdx, final Player player1,
                                   final Player player2) {
        ObjectNode node = objectMapper.createObjectNode();
        node.put("command", "getPlayerMana");
        node.put("playerIdx", playerIdx);
        node.putPOJO("output", debug.getPlayerMana(playerIdx, player1, player2));
        output.add(node);
    }

    /**
     * solve the output for the getCardsOnTable command
     * @param debug
     * @param gameBoard
     */
    public void solveGetCardsOnTable(final Debug debug, final GameBoard gameBoard) {
        ObjectNode node = objectMapper.createObjectNode();
        node.put("command", "getCardsOnTable");
        node.putPOJO("output", debug.getCardsOnTable(gameBoard));
        output.add(node);
    }

    /**
     * solve the output for the getEnvironmentCardsInHand command
     * @param debug
     * @param playerIdx
     * @param player1
     * @param player2
     */
    public void solveGetEnvironmentCardsInHand(final Debug debug, final int playerIdx,
                                               final Player player1, final Player player2) {
        ObjectNode node = objectMapper.createObjectNode();
        node.put("command", "getEnvironmentCardsInHand");
        node.put("playerIdx", playerIdx);
        node.putPOJO("output", debug.getEnvironmentCardsInHand(playerIdx,
                player1, player2));
        output.add(node);
    }

    /**
     * solve the output for the getCardAtPosition command
     * @param curentAction
     * @param debug
     * @param gameBoard
     */
    public void solveGetCardAtPosition(final ActionsInput curentAction, final Debug debug,
                                       final GameBoard gameBoard) {
        ObjectNode node = objectMapper.createObjectNode();

        if (curentAction.getX() > (1 + 1 + 2) || curentAction.getX() < 0
                || (curentAction.getX() == 0
                && (curentAction.getY() > gameBoard.getRow0().size()
                || curentAction.getY() < 0)) || (curentAction.getX() == 1
                && (curentAction.getY() > gameBoard.getRow1().size()
                || curentAction.getY() < 0)) || (curentAction.getX() == 2
                && (curentAction.getY() > gameBoard.getRow2().size()
                || curentAction.getY() < 0)) || (curentAction.getX() == (1 + 2)
                && (curentAction.getY() > gameBoard.getRow3().size()
                || curentAction.getY() < 0))) {
            node.put("command", "getCardAtPosition");
            node.put("x", curentAction.getX());
            node.put("y", curentAction.getY());
            node.put("output", "No card available at that position.");
            output.add(node);
        } else {
            node.put("command", "getCardAtPosition");
            node.put("x", curentAction.getX());
            node.put("y", curentAction.getY());
            node = debug.getCardAtPosition(gameBoard, curentAction.getX(),
                    curentAction.getY(), node);
            output.add(node);
        }
    }

    /**
     * solve the output for the useEnvironmentCard command
     * @param curentAction
     * @param player1
     * @param player2
     * @param gameBoard
     */
    public void solveUseEnvironmentCard(final ActionsInput curentAction, final Player player1,
                                        final Player player2, final GameBoard gameBoard) {
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
        } else if (card.getMana()
                > gameBoard.getCurrentPlayer(player1, player2).getTotalMana()) {
            node.put("command", "useEnvironmentCard");
            node.put("handIdx", handIdx);
            node.put("affectedRow", affectedRow);
            node.put("error", "Not enough mana to use environment card.");
            output.add(node);
        } else if (gameBoard.getPlayerTurn() == 1 && (affectedRow == 2
                || affectedRow == (1 + 2)) || (gameBoard.getPlayerTurn() == 2
                && (affectedRow == 0 || affectedRow == 1))) {
            node.put("command", "useEnvironmentCard");
            node.put("handIdx", handIdx);
            node.put("affectedRow", affectedRow);
            node.put("error", "Chosen row does not belong to the enemy.");
            output.add(node);
        } else {
            gameBoard.getCurrentPlayer(player1, player2).useEnvironmentCard(handIdx,
                    gameBoard, gameBoard.getCurrentPlayer(player1, player2),
                    affectedRow, node, output);
        }
        gameBoard.removeDead();
    }

    /**
     * solve the output for the getFrozenCardsOnTable command
     * @param debug
     * @param gameBoard
     */
    public void solveGetFrozenCardsOnTable(final Debug debug, final GameBoard gameBoard) {
        ObjectNode node = objectMapper.createObjectNode();
        node.put("command", "getFrozenCardsOnTable");
        node.putPOJO("output", debug.getFrozenCardsOnTable(gameBoard));
        output.add(node);
    }

    /**
     * solve the output for the cardUsesAttack command
     * @param curentAction
     * @param gameBoard
     */
    public void solveCardUsesAttack(final ActionsInput curentAction,
                                    final GameBoard gameBoard) {
        ObjectNode node = objectMapper.createObjectNode();

        CardWithCoords attacker = new CardWithCoords(
                curentAction.getCardAttacker().getX(),
                curentAction.getCardAttacker().getY(), gameBoard);
        CardWithCoords attacked = new CardWithCoords(
                curentAction.getCardAttacked().getX(),
                curentAction.getCardAttacked().getY(), gameBoard);

        if ((attacker.getX() == 0 || attacker.getX() == 1) && (attacked.getX() == 0
                || attacked.getX() == 1) || (attacker.getX() == 2
                || attacker.getX() == (1 + 2)) && (attacked.getX() == 2
                || attacked.getX() == (1 + 2))) {
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
        } else if (((attacker.getX() == 0 || attacker.getX() == 1)
                && gameBoard.checkTank(1) == 1 && attacked.getCard().getIsTank() != 1)
                || ((attacker.getX() == 2 || attacker.getX() == (1 + 2))
                && gameBoard.checkTank(2) == 1
                && attacked.getCard().getIsTank() != 1)) {
            node.put("command", "cardUsesAttack");
            node.putPOJO("cardAttacker", attacker);
            node.putPOJO("cardAttacked", attacked);
            node.put("error", "Attacked card is not of type 'Tank'.");
            output.add(node);
        } else {
            attacker.getCard().setHasAttacked(1);
            attacked.getCard().setHealth(attacked.getCard().getHealth()
                    - attacker.getCard().getAttackDamage());
        }
        gameBoard.removeDead();
    }

    /**
     * solve the output for the cardUsesAbility command
     * @param curentAction
     * @param gameBoard
     */
    public void solveCardUsesAbility(final ActionsInput curentAction,
                                     final GameBoard gameBoard) {
        ObjectNode node = objectMapper.createObjectNode();
        int ok = 0;

        CardWithCoords attacker = new CardWithCoords(
                curentAction.getCardAttacker().getX(),
                curentAction.getCardAttacker().getY(), gameBoard);
        CardWithCoords attacked = new CardWithCoords(
                curentAction.getCardAttacked().getX(),
                curentAction.getCardAttacked().getY(), gameBoard);

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
        if (attacker.getCard().getName().equals("Disciple")
                && ((gameBoard.getPlayerTurn() == 1 && (attacked.getX() == 0
                || attacked.getX() == 1)) || (gameBoard.getPlayerTurn() == 2
                && (attacked.getX() == 2 || attacked.getX() == (1 + 2)))) && ok == 0) {
            ok = 1;
            node.put("command", "cardUsesAbility");
            node.putPOJO("cardAttacker", attacker);
            node.putPOJO("cardAttacked", attacked);
            node.put("error", "Attacked card does not belong to the current player.");
            output.add(node);
        }
        if ((attacker.getCard().getName().equals("The Ripper")
                || attacker.getCard().getName().equals("Miraj")
                || attacker.getCard().getName().equals("The Cursed One"))) {
            if ((((attacker.getX() == 0 || attacker.getX() == 1)
                    && (attacked.getX() == 0 || attacked.getX() == 1))
                    || ((attacker.getX() == 2 || attacker.getX() == (1 + 2))
                    && (attacked.getX() == 2 || attacked.getX() == (1 + 2))))
                    && ok == 0) {
                ok = 1;
                node.put("command", "cardUsesAbility");
                node.putPOJO("cardAttacker", attacker);
                node.putPOJO("cardAttacked", attacked);
                node.put("error", "Attacked card does not belong to the enemy.");
                output.add(node);
            }
            if ((((attacker.getX() == 0 || attacker.getX() == 1)
                    && gameBoard.checkTank(1) == 1
                    && attacked.getCard().getIsTank() != 1)
                    || ((attacker.getX() == 2 || attacker.getX() == (1 + 2))
                    && gameBoard.checkTank(2) == 1
                    && attacked.getCard().getIsTank() != 1)) && ok == 0) {
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

    /**
     * solve the output for the useAttackHero command
     * @param curentAction
     * @param player1
     * @param player2
     * @param gameBoard
     */
    public void solveUseAttackHero(final ActionsInput curentAction, final Player player1,
                                   final Player player2, final GameBoard gameBoard) {
        ObjectNode node = objectMapper.createObjectNode();

        CardWithCoords attacker = new CardWithCoords(
                curentAction.getCardAttacker().getX(),
                curentAction.getCardAttacker().getY(), gameBoard);

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
        } else if (((attacker.getX() == 0 || attacker.getX() == 1)
                && gameBoard.checkTank(1) == 1)
                || ((attacker.getX() == 2 || attacker.getX() == (1 + 2))
                && gameBoard.checkTank(2) == 1)) {
            node.put("command", "useAttackHero");
            node.putPOJO("cardAttacker", attacker);
            node.put("error", "Attacked card is not of type 'Tank'.");
            output.add(node);
        } else {
            attacker.getCard().setHasAttacked(1);
            if ((attacker.getX() == 0 || attacker.getX() == 1)) {
                player1.getHero().setHealth(player1.getHero().getHealth()
                        - attacker.getCard().getAttackDamage());
            }
            if ((attacker.getX() == 2 || attacker.getX() == (1 + 2))) {
                player2.getHero().setHealth(player2.getHero().getHealth()
                        - attacker.getCard().getAttackDamage());
            }
        }
        if (player1.getHero().getHealth() <= 0) {
            node.put("gameEnded", "Player two killed the enemy hero.");
            output.add(node);
            game.setPlayerTwoWins(game.getPlayerTwoWins() + 1);
            game.setTotalGamesPlayed(game.getTotalGamesPlayed() + 1);
        }
        if (player2.getHero().getHealth() <= 0) {
            node.put("gameEnded", "Player one killed the enemy hero.");
            output.add(node);
            game.setPlayerOneWins(game.getPlayerOneWins() + 1);
            game.setTotalGamesPlayed(game.getTotalGamesPlayed() + 1);
        }
    }

    /**
     * solve the output for the useHeroAbility command
     * @param curentAction
     * @param player1
     * @param player2
     * @param gameBoard
     */
    public void solveUseHeroAbility(final ActionsInput curentAction, final Player player1,
                                    final Player player2, final GameBoard gameBoard) {
        ObjectNode node = objectMapper.createObjectNode();
        int affectedRow = curentAction.getAffectedRow();
        if (gameBoard.getCurrentPlayer(player1, player2).getHero().getMana()
                > gameBoard.getCurrentPlayer(player1, player2).getTotalMana()) {
            node.put("command", "useHeroAbility");
            node.put("affectedRow", affectedRow);
            node.put("error", "Not enough mana to use hero's ability.");
            output.add(node);
        } else if (gameBoard.getCurrentPlayer(player1,
                player2).getHero().getHasAttacked() == 1) {
            node.put("command", "useHeroAbility");
            node.put("affectedRow", affectedRow);
            node.put("error", "Hero has already attacked this turn.");
            output.add(node);
        } else if (((gameBoard.getCurrentPlayer(player1,
                player2).getHero().getName().equals("Lord Royce"))
                || (gameBoard.getCurrentPlayer(player1,
                player2).getHero().getName().equals("Empress Thorina")))
                && ((gameBoard.getPlayerTurn() == 1
                && (affectedRow == 2 || affectedRow == (1 + 2)))
                || (gameBoard.getPlayerTurn() == 2
                && (affectedRow == 0 || affectedRow == 1)))) {
            node.put("command", "useHeroAbility");
            node.put("affectedRow", affectedRow);
            node.put("error", "Selected row does not belong to the enemy.");
            output.add(node);
        } else if (((gameBoard.getCurrentPlayer(player1,
                player2).getHero().getName().equals("General Kocioraw"))
                || (gameBoard.getCurrentPlayer(player1,
                player2).getHero().getName().equals("King Mudface")))
                && ((gameBoard.getPlayerTurn() == 2
                && (affectedRow == 2 || affectedRow == (1 + 2)))
                || (gameBoard.getPlayerTurn() == 1
                && (affectedRow == 0 || affectedRow == 1)))) {
            node.put("command", "useHeroAbility");
            node.put("affectedRow", affectedRow);
            node.put("error", "Selected row does not belong to the current player.");
            output.add(node);
        } else {
            gameBoard.getCurrentPlayer(player1, player2).getHero().setHasAttacked(1);
            gameBoard.getCurrentPlayer(player1, player2).getHero().useHeroAbility(
                    gameBoard, affectedRow, gameBoard.getCurrentPlayer(player1,
                            player2));
        }
    }

    /**
     * solve the output for the getPlayerOneWins command
     */
    public void solveGetPlayerOneWins() {
        ObjectNode node = objectMapper.createObjectNode();
        node.put("command", "getPlayerOneWins");
        node.put("output", game.getPlayerOneWins());
        output.add(node);
    }

    /**
     * solve the output for the getPlayerTwoWins command
     */
    public void solveGetPlayerTwoWins() {
        ObjectNode node = objectMapper.createObjectNode();
        node.put("command", "getPlayerTwoWins");
        node.put("output", game.getPlayerTwoWins());
        output.add(node);
    }

    /**
     * solve the output for the getTotalGamesPlayed command
     */
    public void solveGetTotalGamesPlayed() {
        ObjectNode node = objectMapper.createObjectNode();
        node.put("command", "getTotalGamesPlayed");
        node.put("output", game.getTotalGamesPlayed());
        output.add(node);
    }
}
