package main.cards;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.CardInput;
import main.GameBoard;
import main.Player;

public class Firestorm extends Card {

    @JsonIgnore
    private int attackDamage;
    @JsonIgnore
    private int health;

    public Firestorm(final CardInput cardInput) {
        super(cardInput);
        super.setIsEnvironment(1);
    }

    /**
     * a method overriding the one in the parent class that deals 1 damage to all enemy cards in
     * the affected row
     * @param gameBoard
     * @param player
     * @param affectedRow
     * @param objectNode
     * @param handIdx
     * @param output
     * @return
     */
    @Override
    public int useEnvironmentCardAbility(final GameBoard gameBoard, final Player player,
                                         final int affectedRow, final ObjectNode objectNode,
                                         final int handIdx, final ArrayNode output) {
        if (affectedRow == 0) {
            for (int i = 0; i < gameBoard.getRow0().size(); i++) {
                gameBoard.getRow0().get(i).setHealth(gameBoard.getRow0().get(i).getHealth() - 1);
            }
        }
        if (affectedRow == 1) {
            for (int i = 0; i < gameBoard.getRow1().size(); i++) {
                gameBoard.getRow1().get(i).setHealth(gameBoard.getRow1().get(i).getHealth() - 1);
            }
        }
        if (affectedRow == 2) {
            for (int i = 0; i < gameBoard.getRow2().size(); i++) {
                gameBoard.getRow2().get(i).setHealth(gameBoard.getRow2().get(i).getHealth() - 1);
            }
        }
        if (affectedRow == (1 + 2)) {
            for (int i = 0; i < gameBoard.getRow3().size(); i++) {
                gameBoard.getRow3().get(i).setHealth(gameBoard.getRow3().get(i).getHealth() - 1);
            }
        }
        return 0;
    }
}
