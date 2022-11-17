package main.heroes;

import fileio.CardInput;
import main.GameBoard;
import main.Player;
import main.cards.Card;

import java.util.ArrayList;

public class LordRoyce extends Hero {

    public LordRoyce(final CardInput cardInput) {
        super(cardInput);
    }

    /**
     * a method overriding the one in the parent class that freezes the highest attack damage
     * enemy card from the affected row
     * @param gameBoard
     * @param affectedRow
     * @param player
     * @return
     */
    @Override
    public int useHeroAbility(final GameBoard gameBoard, final int affectedRow,
                              final Player player) {
        ArrayList<Card> row = null;
        Card maxAttackCard = null;
        int maxAttack = -1;

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

        for (Card card : row) {
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
