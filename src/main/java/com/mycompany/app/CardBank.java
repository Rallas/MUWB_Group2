package com.mycompany.app;

public class CardBank extends Card{
    
    public CardBank(){
        
        int no_of_decks = 6;            //This was made assuming the Bank is the stach of remaining cards. Also I figured a 2D Bank would be easier to manage wrt the max # of cards
        Card CardBank[][] = new Card[no_of_decks][52];
    
        for (int i = 0; i < no_of_decks; i++){
            for (int j = 0; j < Suite.values().length; j++){
                for (int k = 0; k < Value.values().length ; k++){
                    CardBank[i][j + k] = new Card(Suite.values()[j], Value.values()[k]);
                }
            }
        }
    }
}
