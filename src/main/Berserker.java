package main;

import fileio.CardInput;

public class Berserker extends Card {

    public Berserker(final CardInput cardInput) {
        super(cardInput);
        super.setIsBackRow(1);
    }
}
