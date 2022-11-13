package com.mycompany.app;

public class Person extends Actions{
    PlayerType type; //player (nonzero) or dealer (0). int to allow for possibility of multiple player types.
    int playerID;
    CardBank hand = new CardBank(); //stores cards. array to facilitate looping. position indicates value.
    int wager = 0;
    int winnings = 0;
    int agression; //used with bots to determine the sum they stop at

    Person(int ID){
        this.type = PlayerType.values()[1];
        this.playerID = ID;
    }

    Person(int type, int ID){
        this.type = PlayerType.values()[type];
        this.playerID = ID;
    }
    Person(int type, int winnings, int ID){
        this.type = PlayerType.values()[type];
        this.winnings = winnings;
        this.playerID = ID;
    }

    Person(int cutoff, int type, int winnings, int ID){ //bot specific
        this.type = PlayerType.values()[type];
        this.winnings = winnings;
        this.playerID = ID;
        this.agression = cutoff;
    }

    public int getWager() {
        return wager;
    }
    public void setWager(int wager) {
        this.wager = wager;
    }

    public CardBank getHand()
    {
        return this.hand;
    }

    public int TakeTurn() //for letting bots (and dealer) play
    {
        int myTurn = 0;
        while(this.count(this.hand) < this.agression)
        {
            //prio goes dealer, cheater, high, mid, low
            
        }
        return 0;
    }
}
