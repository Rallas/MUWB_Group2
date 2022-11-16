package com.mycompany.app;

//quick n dirty solution to cards. represents both hands and the shoebox
public class CardBank {

    public void fillDeck(){
        for(int i : this.deck){
            this.deck[i] = no_of_decks;
        }
    }

    public void emptyDeck(){
        for(int i : this.deck){
            this.deck[i] = 0;
        }
    }

    int no_of_decks = 6;     //represents the total number of decks
    int count = 0;
    int[] deck = new int[52]; 
    
    /*CardBank(){                   //If 0 is an ACE then why not use -1
        for(int i : this.deck){
            this.deck[i] = -1;
        }
    }*/
    
    public void add(Card One) {
        deck [One.value] = One.value;
        count++; 
    }

    /*
    Kind and Suite are represented by index.

    kind is as follows:
    0-3 Aces
    4-39 Counting numbers (Twos, Threes, ect)
    40-43 Jacks
    44-47 Queens
    48-51 Kings

    suite is as follows:
    0/4/8 (4*n) is Clubs
    1/5/9 (4*n +1) is Hearts
    2/6/10 (4*n +2) is Diamonds
    3/7/11 (4*n +3) is Spades 

    For example: 
    position deck[4]
    represents the number of Twos of Clubs currently in the shoe/hand
    deck[41] represent the number of Jacks of Hearts
    */
}
