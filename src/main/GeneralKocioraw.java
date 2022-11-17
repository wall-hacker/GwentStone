package main;

import fileio.CardInput;

import java.util.ArrayList;

public class GeneralKocioraw extends Hero{

    public GeneralKocioraw(CardInput cardInput) {
        super(cardInput);
    }

    @Override
    public int useHeroAbility(GameBoard gameBoard, int affectedRow, Player player) {
        ArrayList<Card> row = null;

        if (affectedRow == 0)
            row = gameBoard.getRow0();
        if (affectedRow == 1)
            row = gameBoard.getRow1();
        if (affectedRow == 2)
            row = gameBoard.getRow2();
        if (affectedRow == 3)
            row = gameBoard.getRow3();

        for(int i = 0; i < row.size(); i++)
            row.get(i).setAttackDamage(row.get(i).getAttackDamage() + 1);

        player.setTotalMana(player.getTotalMana() - getMana());

        return 0;
    }
}
