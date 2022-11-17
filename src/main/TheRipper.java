package main;

import fileio.CardInput;

public class TheRipper extends Card {

    public TheRipper(CardInput cardInput) {
        super(cardInput);
        super.setIsFrontRow(1);
    }
    @Override
    public void useCardAbility(CardWithCoords attacked) {
        if (attacked.getCard().getAttackDamage() !=0)
            attacked.getCard().setAttackDamage(attacked.getCard().getAttackDamage() - 2);
    }
}
