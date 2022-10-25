package com.mycompany.app;

public class GameState {
    //manages the game state.
    Person participants[]; //a loopable array of all current players and dealer
    Card bank[]; //indicates remaining cards in the bank. position indicates value
    int GameId;//simple id that can be used to make each game unique
    //I am anticipating multi-connection online play, with host/client structure.
    //by storing the game state as a single class I can pass around a 'saved game'
    //in json format, with the host using the  ID to manage multiple games with multiple players
    //This means any given game can be represented by a json of this class

    public static int Update()
    {
        //Reads in the game state, calls players to play until they stand or bust. checks if the participant is a player, bot, or dealer before calling logic
        return 0;
    }

    public static int setHand()
    {
        //collects bets and deals
        return 0;
    }

    public static int count(Card deck[])
    {
        //counts the value of the deck of cards
        return 0;
    }

    public static int packageAndPrint(Person participants[])
    {
        //prints the current state of the game and packages it to a json -- equivilent to a save
        //and how the game will pass around information between clients and host
        return 0;
    }

    public static int start(Person participants[])
    {
        //starts the gameloop


        return 0;
    }
}
