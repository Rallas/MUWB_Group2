package com.mycompany.app;

public class Card {

    public enum Suite {
        HEARTS, CLUBS, DIAMONDS, SPADES
    }

    public enum Value {
        ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE,
        TEN, JACK, QUEEN, KING
    }

    public Suite suite;
    public Value value;

    public Card() {
        int lowerbound = 1;                 //Was originally a constructor but could be used to dole out cards & then remove them from the CardBank -JM
        int upperbound = 13;
        int temp;
        temp = (int)Math.floor(Math.random()*(upperbound-lowerbound+1) + lowerbound);      //"randomly" assigns a value between (inclusive) 1 & 11
        
        this.value = Value.values()[temp];

        temp = ((int)Math.floor(Math.random()*(4) + 1));
        this.suite = Suite.values()[temp];          //"randomly" assigns a suite
    }

    public Card(Suite suite, Value value) {
        this.suite = suite;
        this.value = value;
    }

    public String getSuite() {
        return suite.name();
    }
    public String getValue() {
        return value.name();
    }
}
