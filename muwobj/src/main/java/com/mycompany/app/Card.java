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
        int lowerbound = 1;                 //I added this to get a working model - JM
        int upperbound = 13;
        int temp;
        temp = (int)Math.floor(Math.random()*(upperbound-lowerbound+1) + lowerbound);      //"randomly" assigns a value between (inclusive) 1 & 11
        this.value = Value[temp]

        int suitpicker = (int)Math.floor(Math.random()*(4) + 1);
        if (suitpicker == 1)             //"randomly" assigns a suite. In reality there can be no duplicates so there needs to be a mechanism to enforce this
           this.suite = Suite[0];
        else if (suitpicker == 2)
           this.suite = Suite[1];
        else if (suitpicker == 3)
           this.suite = Suite[2];
        else
           this.suite = Suite[3];
    }

}