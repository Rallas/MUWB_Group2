package com.mycompany.app;

//quick n dirty solution to cards. represents both hands and the shoebox
public class CardBank {

    public void fillDeck(){
        for(int i = 0; i<52 ; i++){
            this.deck[i] = no_of_decks;
        }
    }



    public void emptyDeck(){
        for(int i = 0; i<52 ; i++){
            this.deck[i] = 0;
        }
    }

    public void updateCardinality()
    {
        this.cardinality = 0;
        for(int i : this.deck)
        {
            this.cardinality = this.cardinality + i;
        }
    }

    public void updateHandTotal()
    {
        this.hand_total = 0;
        for(int i : this.deck)
        {
            if (deck[i] > 0){

                if (i != 0){
                    temp = (int)Math.ceil(i / 4);
                    hand_total = hand_total + temp;
                }
                else{
                    hand_total = hand_total + 1;            //half covers Ace of Clubs
                }
            }

        }
    }
    int hand_total = 0;
    int cardinality;    //represents the number of cards in this deck
    int no_of_decks = 6;     //represents the total number of decks
    int[] deck = new int[52];           //Each index is a count for the number of cards of that type
    int temp = 0;
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
