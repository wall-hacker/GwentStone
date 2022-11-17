package main;

import fileio.CardInput;

public class Sentinel extends Card {

    public Sentinel(CardInput cardInput) {
        super(cardInput);
        super.setIsBackRow(1);
    }
}
