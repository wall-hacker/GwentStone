package main;

import fileio.CardInput;

public class TheRipper extends Card {

    public TheRipper(final CardInput cardInput) {
        super(cardInput);
        super.setIsFrontRow(1);
    }

    /**
     * a method overriding the one in the parent class that subtracts 2 from
     * the attacked card's attack damage
     * @param attacked
     */
    @Override
    public void useCardAbility(final CardWithCoords attacked) {
        if (attacked.getCard().getAttackDamage() != 0) {
            attacked.getCard().setAttackDamage(attacked.getCard().getAttackDamage() - 2);
        }
    }
}
