package main;

import fileio.CardInput;

public class Disciple extends Card {

    public Disciple(final CardInput cardInput) {
        super(cardInput);
        super.setIsBackRow(1);
    }

    /**
     * a method overriding the one in the parent class that adds 2 health to an ally card
     * @param attacked
     */
    @Override
    public void useCardAbility(final CardWithCoords attacked) {
        attacked.getCard().setHealth(attacked.getCard().getHealth() + 2);
    }
}
