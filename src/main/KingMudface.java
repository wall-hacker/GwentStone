package main;

import fileio.CardInput;

import java.util.ArrayList;

public class KingMudface extends Hero {

    public KingMudface(final CardInput cardInput) {
        super(cardInput);
    }

    /**
     * a method overriding the one in the parent class that add 1 health to all ally cards on
     * the affected row
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
            row.get(i).setHealth(row.get(i).getHealth() + 1);
        }

        player.setTotalMana(player.getTotalMana() - getMana());
        return 0;
    }
}
