package main;

import fileio.CardInput;

public class TheCursedOne extends Card {

    public TheCursedOne(final CardInput cardInput) {
        super(cardInput);
        super.setIsBackRow(1);
    }

    /**
     * a method overriding the one in the parent class that switches
     * an enemy card's health and attack damage
     * @param attacked
     */
    @Override
    public void useCardAbility(final CardWithCoords attacked) {
        int hisAttack = attacked.getCard().getAttackDamage();
        int hisHealth = attacked.getCard().getHealth();

        attacked.getCard().setAttackDamage(hisHealth);
        attacked.getCard().setHealth(hisAttack);
    }
}
