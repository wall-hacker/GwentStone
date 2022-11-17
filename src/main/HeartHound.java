package main;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.CardInput;

public class HeartHound extends Card {

    @JsonIgnore
    private int attackDamage;
    @JsonIgnore
    private int health;
    public HeartHound(CardInput cardInput) {
        super(cardInput);
        super.setIsEnvironment(1);
    }

    @Override
    public int useEnvironmentCardAbility(GameBoard gameBoard, Player player, int affectedRow, ObjectNode objectNode, int handIdx, ArrayNode output) {

        int maxHealth = -1, maxHealthCardIdx = -1;
        Card maxHealthCard = null;

        if (affectedRow == 0)
            for(int i = 0; i < gameBoard.getRow0().size(); i++)
                if (gameBoard.getRow0().get(i).getHealth() >= maxHealth) {
                    maxHealthCard = gameBoard.getRow0().get(i);
                    maxHealth = gameBoard.getRow0().get(i).getHealth();
                    maxHealthCardIdx = i;
                }
        if (affectedRow == 1)
            for(int i = 0; i < gameBoard.getRow1().size(); i++)
                if (gameBoard.getRow1().get(i).getHealth() >= maxHealth) {
                    maxHealthCard = gameBoard.getRow1().get(i);
                    maxHealth = gameBoard.getRow1().get(i).getHealth();
                    maxHealthCardIdx = i;
                }
        if (affectedRow == 2)
            for(int i = 0; i < gameBoard.getRow2().size(); i++)
                if (gameBoard.getRow2().get(i).getHealth() >= maxHealth) {
                    maxHealthCard = gameBoard.getRow2().get(i);
                    maxHealth = gameBoard.getRow2().get(i).getHealth();
                    maxHealthCardIdx = i;
                }
        if (affectedRow == 3)
            for(int i = 0; i < gameBoard.getRow3().size(); i++)
                if (gameBoard.getRow3().get(i).getHealth() >= maxHealth) {
                    maxHealthCard = gameBoard.getRow3().get(i);
                    maxHealth = gameBoard.getRow3().get(i).getHealth();
                    maxHealthCardIdx = i;
                }
        if ((maxHealthCard.getIsBackRow() == 1 && gameBoard.getPlayerTurn() == 1 && gameBoard.getRow3().size() == 5) ||
                (maxHealthCard.getIsBackRow() == 1 && gameBoard.getPlayerTurn() == 2 && gameBoard.getRow0().size() == 5) ||
                (maxHealthCard.getIsFrontRow() == 1 && gameBoard.getPlayerTurn() == 1 && gameBoard.getRow2().size() == 5) ||
                (maxHealthCard.getIsFrontRow() == 1 && gameBoard.getPlayerTurn() == 2 && gameBoard.getRow1().size() == 5)) {
            objectNode.put("command", "useEnvironmentCard");
            objectNode.put("handIdx", handIdx);
            objectNode.put("affectedRow", affectedRow);
            objectNode.put("error", "Cannot steal enemy card since the player's row is full.");
            output.add(objectNode);
            return 404;
        }

        if (affectedRow == 0) {
            Card card = gameBoard.getRow0().remove(maxHealthCardIdx);
            gameBoard.getRow3().add(card);
        }
        if (affectedRow == 1) {
            Card card = gameBoard.getRow1().remove(maxHealthCardIdx);
            gameBoard.getRow2().add(card);
        }
        if (affectedRow == 2) {
            Card card = gameBoard.getRow2().remove(maxHealthCardIdx);
            gameBoard.getRow1().add(card);
        }
        if (affectedRow == 3) {
            Card card = gameBoard.getRow3().remove(maxHealthCardIdx);
            gameBoard.getRow0().add(card);
        }

        return 0;
    }
}
