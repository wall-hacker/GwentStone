package main.cards;

import fileio.CardInput;

public class Warden extends Card {

    public Warden(final CardInput cardInput) {
        super(cardInput);
        super.setIsFrontRow(1);
        super.setIsTank(1);
    }
}
