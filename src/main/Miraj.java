package main;

import fileio.CardInput;

public class Miraj extends Card {

    public Miraj(CardInput cardInput) {
        super(cardInput);
        super.setIsFrontRow(1);
    }

    @Override
    public void useCardAbility(CardWithCoords attacked) {
        int myHealth = super.getHealth();
        int hisHealth = attacked.getCard().getHealth();

        super.setHealth(hisHealth);
        attacked.getCard().setHealth(myHealth);
    }
}
