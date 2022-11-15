package com.mycompany.app;
import java.util.Random;

public class Card {         //THIS CLASS MAY BE OBSOLETE AS IT"S BEEN REPLACED BY CARDBANK. I edited for testing the UI - JM

    int value = 0;

    public Card() {
        int upperbound = 52;
        Random rand = new Random();

        value = rand.nextInt(upperbound);      
    }
    public int getValue() {
        return value;
    }
}
