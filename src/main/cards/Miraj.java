package main.cards;

import fileio.CardInput;

public class Miraj extends Card {

    public Miraj(final CardInput cardInput) {
        super(cardInput);
        super.setIsFrontRow(1);
    }

    /**
     * a method overriding the one in the parent class that switches an ally and enemy card healths
     * @param attacked
     */
    @Override
    public void useCardAbility(final CardWithCoords attacked) {
        int myHealth = super.getHealth();
        int hisHealth = attacked.getCard().getHealth();

        super.setHealth(hisHealth);
        attacked.getCard().setHealth(myHealth);
    }
}
