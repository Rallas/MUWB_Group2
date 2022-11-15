package com.mycompany.app;

import java.util.*;


public class Person extends Actions{
    PlayerType type; //player (nonzero) or dealer (0). int to allow for possibility of multiple player types.
    int playerID;
    //CardBank hand = new CardBank(); //stores cards. array to facilitate looping. position indicates value.
    Vector <Card> hand = new Vector<Card>();        //temporarily rigged for UI testing. May need to move this direction as JS can't iterate through CardBank as it is
    int wager = 0;
    int winnings = 200;
    int agression; //used with bots to determine the sum they stop at

    Person(int ID){
        this.type = PlayerType.values()[1];
        this.playerID = ID;
        hand.add(new Card());                   //Added for Vector implementation. Remove if you can make CardBank directly iterable
        hand.add(new Card());
    }

    Person(int type, int ID){
        this.type = PlayerType.values()[type];
        this.playerID = ID;
        hand.add(new Card());
        hand.add(new Card());
    }
    Person(int type, int winnings, int ID){
        this.type = PlayerType.values()[type];
        this.winnings = winnings;
        this.playerID = ID;
        hand.add(new Card());
        hand.add(new Card());
    }

    Person(int cutoff, int type, int winnings, int ID){ //bot specific
        this.type = PlayerType.values()[type];
        this.winnings = winnings;
        this.playerID = ID;
        this.agression = cutoff;
        hand.add(new Card());
        hand.add(new Card());
    }

    public int getWager() {
        return wager;
    }
    public void setWager(int wager) {
        this.wager = wager;
    }

    public Vector <Card> getHand()
    {
        return this.hand;
    }

    public int TakeTurn(GameState G) //for letting bots (and dealer) play
    {
        int myTurn = 1;
        UserEvent U = new UserEvent();
        Random rand = new Random();
        int splitsRemaining = 2; //we don't want bots infinitly splitting, as they are likely to destroy themselves doing so
        U.PlayerIdx = this.playerID;
        U.GameId = G.GameId;
        //prio goes dealer, cheater, high, mid, low
        if(this.type == PlayerType.DEALER)
        {
            while(this.count(this.hand) < 16 && myTurn == 1)
            {
                U.Button = 1;
                myTurn = G.Update(U);
            }
            U.Button = 0;
            myTurn = G.Update(U);
        }
        if(this.type == PlayerType.BOTCHEAT)
        {
            U.PlayerIdx = this.playerID;
            U.GameId = G.GameId;
            if(this.count(this.hand)>8 && this.count(this.hand)<12)
            {
                U.Button = 3;
                myTurn = G.Update(U);
            }
            while(this.count(this.hand) < this.agression && myTurn == 1)
            {
                
                if(this.Split(this.hand) > 0 && splitsRemaining > 0)
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
            U.PlayerIdx = this.playerID;
            U.GameId = G.GameId;
            if(this.count(this.hand)>8 && this.count(this.hand)<12)
            {
                U.Button = 3;
                myTurn = G.Update(U);
            }
            while(this.count(this.hand) < this.agression && myTurn == 1)
            {
                
                if(this.Split(this.hand) > 0 && splitsRemaining > 0)
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
            U.PlayerIdx = this.playerID;
            U.GameId = G.GameId;
            while(this.count(this.hand) < this.agression && myTurn == 1)
            {
                
                if(this.Split(this.hand) > 0 && splitsRemaining > 0)
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
            U.PlayerIdx = this.playerID;
            U.GameId = G.GameId;
            while(this.count(this.hand) < this.agression && myTurn == 1)
            {
                U.Button = 1; //regular hit
                myTurn = G.Update(U);
            }
            U.Button = 0;
            myTurn = G.Update(U);
        }
        return 0;
    }
}
