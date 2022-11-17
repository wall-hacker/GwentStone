package main;

import fileio.CardInput;

public class Goliath extends Card {

    public Goliath(CardInput cardInput) {
        super(cardInput);
        super.setIsFrontRow(1);
        super.setIsTank(1);
    }
}
