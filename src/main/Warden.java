package main;

import fileio.CardInput;

public class Warden extends Card {

    public Warden(CardInput cardInput) {
        super(cardInput);
        super.setIsFrontRow(1);
        super.setIsTank(1);
    }
}
