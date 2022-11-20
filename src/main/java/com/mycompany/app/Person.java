package com.mycompany.app;


import java.util.Random;
import java.util.Vector;

public class Person extends Actions{
    PlayerType type; //player (nonzero) or dealer (0). int to allow for possibility of multiple player types.
    int PlayerId;
    //CardBank hand = new CardBank(); //stores cards. array to facilitate looping. position indicates value.
    int wager = 0;
    int winnings = 200;
    int agression; //used with bots to determine the sum they stop at
    int splitDepth=0; //exists to track the total depth of hands thathave been split to.
    int currentDepth=0; //exists to track the current hand beingplayed with.
    //Vector <CardBank> hand = new Vector<CardBank>(); // represents both the initial hand and all splits
    CardBank hand = new CardBank(); 
    //public int Button[] = new int [5];

    Person(){ //empty constructor for creating  scanners
        this.type = PlayerType.values()[1];
        /*for (int i: Button){
            Button[i] = 0;
        }*/
    }

    Person(int ID){
        this.type = PlayerType.values()[1];
        this.PlayerId = ID;
        /*for (int i: Button){
            Button[i] = 0;
        }*/
    }

    Person(int type, int ID){
        this.type = PlayerType.values()[type];
        this.PlayerId = ID;
        /*for (int i: Button){
            Button[i] = 0;
        }*/
    }
    Person(int type, int winnings, int ID){
        this.type = PlayerType.values()[type];
        this.winnings = winnings;
        this.PlayerId = ID;
       /*  for (int i: Button){
            Button[i] = 0;
        }*/
    }

    Person(int cutoff, int type, int winnings, int ID){ //bot specific
        this.type = PlayerType.values()[type];
        this.winnings = winnings;
        this.PlayerId = ID;
        this.agression = cutoff;
        /*for (int i: Button){
            Button[i] = 0;
        }*/
    }

    public int getWager() {
        return wager;
    }
    public void setWager(int wager) {
        this.wager = wager;
    }

    /*public CardBank getHand(int depth)
    {
        return this.hand.get(depth);
    }

    public void addSplitdeck(int cardToSeed)
    {

        CardBank newHand = new CardBank();
        newHand.emptyDeck();
        newHand.deck[cardToSeed]++;
        this.splitDepth++;
        this.hand.add(newHand);
    }

    public int TakeTurn(GameState G) //for letting bots (and dealer) play
    {
        int myTurn = 1;
        UserEvent U = new UserEvent();
        Random rand = new Random();
        int splitsRemaining = 2; //we don't want bots infinitly splitting, as they are likely to destroy themselves doing so
        U.PlayerId = this.PlayerId;
        U.GameId = G.GameId;

        //prio goes dealer, cheater, high, mid, low
        if(this.type == PlayerType.DEALER)
        {
            while(this.count(this.hand.get(currentDepth)) < 16 && myTurn == 1)
            {
                U.Button = 1;
                myTurn = G.Update(U);
            }
            U.Button = 0;
            myTurn = G.Update(U);
        }
        if(this.type == PlayerType.BOTCHEAT)
        {
            U.PlayerId = this.PlayerId;
            U.GameId = G.GameId;
            if(this.count(this.hand.get(currentDepth))>8 && this.count(this.hand.get(currentDepth))<12)
            {
                U.Button = 3;
                myTurn = G.Update(U);
            }
            while(this.count(this.hand.get(currentDepth)) < this.agression && myTurn == 1)
            {

                if(this.Split(this.hand.get(currentDepth)) > 0 && splitsRemaining > 0)
                {
                    splitsRemaining--;
                    U.Button = 2;
                    myTurn = G.Update(U);
                }
                if(rand.nextInt(3)<3) //even cheaters aren't perfect- 75% chance to 'cheat' a draw
                {
                    U.Button = 99; //special button code that corresponds to a 'cheat hit' --not present on the UI
                    myTurn = G.Update(U);
                }
                else
                {
                    U.Button = 1; //regular hit
                    myTurn = G.Update(U);
                }
            }
            U.Button = 0;
            myTurn = G.Update(U);
        }
        if(this.type == PlayerType.BOTHIGH)
        {
            U.PlayerId = this.PlayerId;
            U.GameId = G.GameId;
            if(this.count(this.hand.get(currentDepth))>8 && this.count(this.hand.get(currentDepth))<12)
            {
                U.Button = 3;
                myTurn = G.Update(U);
            }
            while(this.count(this.hand.get(currentDepth)) < this.agression && myTurn == 1)
            {

                if(this.Split(this.hand.get(currentDepth)) > 0 && splitsRemaining > 0)
                {
                    splitsRemaining--;
                    U.Button = 2;
                    myTurn = G.Update(U);
                }
                U.Button = 1; //regular hit
                myTurn = G.Update(U);
            }
            U.Button = 0;
            myTurn = G.Update(U);
        }
        if(this.type == PlayerType.BOTMID)
        {
            U.PlayerId = this.PlayerId;
            U.GameId = G.GameId;
            while(this.count(this.hand.get(currentDepth)) < this.agression && myTurn == 1)
            {

                if(this.Split(this.hand.get(currentDepth)) > 0 && splitsRemaining > 0)
                {
                    splitsRemaining--;
                    U.Button = 2;
                    myTurn = G.Update(U);
                }
                U.Button = 1; //regular hit
                myTurn = G.Update(U);
            }
            U.Button = 0;
            myTurn = G.Update(U);
        }
        if(this.type == PlayerType.BOTLOW)
        {
            U.PlayerId = this.PlayerId;
            U.GameId = G.GameId;
            while(this.count(this.hand.get(currentDepth)) < this.agression && myTurn == 1)
            {
                U.Button = 1; //regular hit
                myTurn = G.Update(U);
            }
            U.Button = 0;
            myTurn = G.Update(U);
        }
        return 0;
    } */
}