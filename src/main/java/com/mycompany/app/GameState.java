package com.mycompany.app;

import java.util.Vector;

import com.mycompany.app.UserEvent.UserEventType;

public class GameState {
    //manages the game state. 
    Vector <Person> participants = new Vector<Person>(); //a vector of all current players and dealer
    int GameId;//simple id that can be used to make each game unique
    //I am anticipating multi-connection online play, with host/client structure.
    //by storing the game state as a single class I can pass around a 'saved game'
    //in json format, with the host using the  ID to manage multiple games with multiple players
    //This means any given game can be represented by a json of this class
    public int CurrentTurn;
    public PlayerType Turn_Cycle;
    public String[] Msg = new String [2];
    public int[] Button;         //may be recycled to inform players about the current options for any given turn

    GameState()
    {                  
        participants.add(new Person(0,0));//dealer
        Button = new int[5];
        for (int i = 0; i < Button.length; i++) {
            Button[i] = 0;
        }
        CardBank shoeBox = new CardBank();
        shoeBox.fillDeck();
        GameId = 1;
        CurrentTurn = 0;
        Turn_Cycle = PlayerType.PLAYER;                     //This needs to be set to Player so they can set their bet
        
        Msg[0] = "You're the dealer. You should be a bot....Huh";
        Msg[1] = "Welcome. The game will begin shortly";
    }

    //Reads in the game state, calls players to play until they stand or bust. checks if the participant is a player, bot, or dealer before calling logic
    /*public static int setHand()
    {
        //collects bets and deals
        return 0;
    }

    public static int count(int[] deck)
    {
    }

    public static int packageAndPrint(Person participants[])
    {
     
    }*/ //must refactor

    public void StartGame(Vector<Person> participants)
    {
        //starts the gameloop
        CurrentTurn = 0;        

    }

    public void addPlayer(PlayerType.PLAYER p){/*These 2 were recycled from Poker sample code. Unknown if still needed */
        participants.add(new Person(10, 15));
    }

    public void removePlayer(int PlayerIdx){
        participants.remove(PlayerIdx - 1);
    }


    public int Update(UserEvent U) //May need to alter game state & check for a winner if it's not implemented elsewhere
    {
        System.out.println("The User Event is " + U.PlayerIdx + " " + U.Button);

        Msg[0] = "The User Event is " + U.PlayerIdx + " " + U.Button;
        Msg[1] = "The User Event is " + U.PlayerIdx + " " + U.Button;

        if (U.Event == UserEventType.BET){

            Msg[0] = "A BET of " + U.Button + " was made by Player " + U.PlayerIdx;
            Msg[1] = "The User Event is " + U.PlayerIdx + " " + U.Button;
        
            return 0;
        }
        
        return -1; //it's not your turn 
    }
}
