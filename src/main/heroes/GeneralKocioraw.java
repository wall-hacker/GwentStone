package main.heroes;

import fileio.CardInput;
import main.GameBoard;
import main.Player;
import main.cards.Card;

import java.util.ArrayList;

public class GeneralKocioraw extends Hero {

    public GeneralKocioraw(final CardInput cardInput) {
        super(cardInput);
    }

    /**
     * a method overriding the one in the parent class that 1 damage to all
     * ally cards in the affected row
     * @param gameBoard
     * @param affectedRow
     * @param player
     * @return
     */
    @Override
    public int useHeroAbility(final GameBoard gameBoard, final int affectedRow,
                              final Player player) {
        ArrayList<Card> row = null;

        if (affectedRow == 0) {
            row = gameBoard.getRow0();
        }
        if (affectedRow == 1) {
            row = gameBoard.getRow1();
        }
        if (affectedRow == 2) {
            row = gameBoard.getRow2();
        }
        if (affectedRow == (1 + 2)) {
            row = gameBoard.getRow3();
        }

        for (int i = 0; i < row.size(); i++) {
            row.get(i).setAttackDamage(row.get(i).getAttackDamage() + 1);
        }

        player.setTotalMana(player.getTotalMana() - getMana());

        return 0;
    }
}
