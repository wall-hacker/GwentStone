package main;

import fileio.CardInput;

public class TheCursedOne extends Card {

    public TheCursedOne(CardInput cardInput) {
        super(cardInput);
        super.setIsBackRow(1);
    }
    @Override
    public void useCardAbility(CardWithCoords attacked) {
        int hisAttack = attacked.getCard().getAttackDamage();
        int hisHealth = attacked.getCard().getHealth();

        attacked.getCard().setAttackDamage(hisHealth);
        attacked.getCard().setHealth(hisAttack);
    }
}
