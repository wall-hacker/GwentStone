package main;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.CardInput;

public class Winterfell extends Card {

    @JsonIgnore
    private int attackDamage;
    @JsonIgnore
    private int health;

    public Winterfell(CardInput cardInput) {
        super(cardInput);
        super.setIsEnvironment(1);
    }
    @Override
    public int useEnvironmentCardAbility(GameBoard gameBoard, Player player, int affectedRow, ObjectNode objectNode, int handIdx, ArrayNode output) {

        if (affectedRow == 0)
            for(int i = 0; i < gameBoard.getRow0().size(); i++)
                gameBoard.getRow0().get(i).setIsFrozen(1);
        if (affectedRow == 1)
            for(int i = 0; i < gameBoard.getRow1().size(); i++)
                gameBoard.getRow1().get(i).setIsFrozen(1);
        if (affectedRow == 2)
            for(int i = 0; i < gameBoard.getRow2().size(); i++)
                gameBoard.getRow2().get(i).setIsFrozen(1);
        if (affectedRow == 3)
            for(int i = 0; i < gameBoard.getRow3().size(); i++)
                gameBoard.getRow3().get(i).setIsFrozen(1);
        return 0;
    }
}
