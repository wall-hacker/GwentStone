package main.cards;

import fileio.CardInput;

public class Sentinel extends Card {

    public Sentinel(final CardInput cardInput) {
        super(cardInput);
        super.setIsBackRow(1);
    }
}
