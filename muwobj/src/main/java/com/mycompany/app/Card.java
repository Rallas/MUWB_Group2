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
        
        if (temp == 1)
            this.value = Value.ACE; 
        else if (temp == 2)
            this.value = Value.TWO;
        else if (temp == 3)
            this.value = Value.THREE;
        else if (temp == 4)
            this.value = Value.FOUR;
        else if (temp == 5)
            this.value = Value.FIVE;
        else if (temp == 6)
            this.value = Value.SIX;
        else if (temp == 7)
            this.value = Value.SEVEN;
        else if (temp == 8)
            this.value = Value.EIGHT;
        else if (temp == 9)
            this.value = Value.NINE;
        else if (temp == 10)
            this.value = Value.TEN;
        else if (temp == 11)
            this.value = Value.JACK;
        else if (temp == 12)
            this.value = Value.QUEEN;
        else if (temp == 13)
            this.value = Value.KING;    

        int suitpicker = ((int)Math.floor(Math.random()*(4) + 1));
        if (suitpicker == 1)             //"randomly" assigns a suite. In reality there can be no duplicates so there needs to be a mechanism to enforce this
           this.suite = Suite.HEARTS;
        else if (suitpicker == 2)
           this.suite = Suite.CLUBS;
        else if (suitpicker == 3)
           this.suite = Suite.DIAMONDS;
        else
           this.suite = Suite.SPADES;
    }
}
