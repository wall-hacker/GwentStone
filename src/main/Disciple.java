package main;

import fileio.CardInput;

public class Disciple extends Card {

    public Disciple(CardInput cardInput) {
        super(cardInput);
        super.setIsBackRow(1);
    }

    @Override
    public void useCardAbility(CardWithCoords attacked) {
        attacked.getCard().setHealth(attacked.getCard().getHealth() + 2);
    }
}
