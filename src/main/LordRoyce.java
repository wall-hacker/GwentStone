package main;

import fileio.CardInput;

import java.util.ArrayList;

public class LordRoyce extends Hero{

    public LordRoyce(CardInput cardInput) {
        super(cardInput);
    }

    @Override
    public int useHeroAbility(GameBoard gameBoard, int affectedRow, Player player) {
        ArrayList<Card> row = null;
        Card maxAttackCard = null;
        int maxAttack = -1;

        if (affectedRow == 0)
            row = gameBoard.getRow0();
        if (affectedRow == 1)
            row = gameBoard.getRow1();
        if (affectedRow == 2)
            row = gameBoard.getRow2();
        if (affectedRow == 3)
            row = gameBoard.getRow3();

        for(Card card : row) {
            if (card.getAttackDamage() > maxAttack) {
                maxAttack = card.getAttackDamage();
                maxAttackCard = card;
            }
        }
        maxAttackCard.setIsFrozen(1);

        player.setTotalMana(player.getTotalMana() - getMana());

        return 0;
    }
}
