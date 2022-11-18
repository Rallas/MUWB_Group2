package com.mycompany.app;

public class Card {         //THIS CLASS IS OBSOLETE AS IT"S BEEN REPLACED BY CARDBANK

    public int value;

    public Card() {
        int lowerbound = 0;               
        int upperbound = 50;
        int temp;
        temp = (int)Math.floor(Math.random()*(upperbound-lowerbound+1) + lowerbound);      //"randomly" assigns a value between (inclusive) 1 & 11
        
        this.value = temp;

    }
}
