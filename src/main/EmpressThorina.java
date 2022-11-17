package main;

import fileio.CardInput;

import java.util.ArrayList;

public class EmpressThorina extends Hero{

    public EmpressThorina(CardInput cardInput) {
        super(cardInput);
    }

    @Override
    public int useHeroAbility(GameBoard gameBoard, int affectedRow, Player player) {
        ArrayList<Card> row = null;
        int maxHealthCardIdx = -1;
        int maxHealth = -1;

        if (affectedRow == 0)
            row = gameBoard.getRow0();
        if (affectedRow == 1)
            row = gameBoard.getRow1();
        if (affectedRow == 2)
            row = gameBoard.getRow2();
        if (affectedRow == 3)
            row = gameBoard.getRow3();

        for(int i = 0; i < row.size(); i++) {
            if (row.get(i).getHealth() > maxHealth) {
                maxHealth = row.get(i).getHealth();
                maxHealthCardIdx = i;
            }
        }

        if (affectedRow == 0)
            gameBoard.getRow0().remove(maxHealthCardIdx);
        if (affectedRow == 1)
            gameBoard.getRow1().remove(maxHealthCardIdx);
        if (affectedRow == 2)
            gameBoard.getRow2().remove(maxHealthCardIdx);
        if (affectedRow == 3)
            gameBoard.getRow3().remove(maxHealthCardIdx);

        player.setTotalMana(player.getTotalMana() - getMana());

        return 0;
    }
}
