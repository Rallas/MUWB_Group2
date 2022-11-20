package com.mycompany.app;
import java.util.Random;


public class Card {         //THIS CLASS IS OBSOLETE AS IT"S BEEN REPLACED BY CARDBANK
    public int value;
    Random rand = new Random();
    
    public Card() { 
        int upperbound = 52;
        
        value = rand.nextInt(upperbound);   //generates a random int from 0 - 51 reflective of a card

    }
}
