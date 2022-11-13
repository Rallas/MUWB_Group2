package com.mycompany.app;

import java.util.Vector;

public class GameState {
    //manages the game state. 
    Vector <Person> participants = new Vector<Person>(); //a vector of all current players and dealer
    int GameId;//simple id that can be used to make each game unique
    //I am anticipating multi-connection online play, with host/client structure.
    //by storing the game state as a single class I can pass around a 'saved game'
    //in json format, with the host using the  ID to manage multiple games with multiple players
    //This means any given game can be represented by a json of this class
    public int CurrentTurn;
    public String[] Msg = new String [2];
    public PlayerType[] Button;         //may be recycled to inform players about the current options for any given turn

    GameState()
    {                  
        participants.add(new Person(0,0));//dealer
        Button = new PlayerType[5];
        for (int i = 0; i < Button.length; i++) {
            Button[i] = PlayerType.SPECTATOR;
        }
        CardBank shoeBox = new CardBank();
        shoeBox.fillDeck();
        GameId = 1;
        
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
        int deck_val = 0;    //Needs to count the value of the deck of cards
        for (int i: deck){
            deck_val = 1;           //need to figure out how to hashmap each card value word to its numerical val 
        }
        return 0;
    }

    public static int packageAndPrint(Person participants[])
    {
        //prints the current state of the game and packages it to a json -- equivilent to a save
        //and how the game will pass around information between clients and host
        System.out.println("This is the dealers hand: ");
        System.out.println("Dealer: " + participants[0].getHand());
      
        System.out.println("\nThis is the players hand: ");
        System.out.println("Player: " + participants[1].getHand());
        
        return 0;
    }*/ //must refactor

    public void StartGame(Vector<Person> participants)
    {
        //starts the gameloop
        CurrentTurn = 0;        

    }

    public int Update(UserEvent U)
    {
        System.out.println("The User Event is " + U.PlayerIdx + " " + U.Button);

        if ((CurrentTurn == U.PlayerIdx)) 
        {
            // Move is legitimate, lets do what was requested

            /*// Is the button not taken by X or O?
            if (Button[U.Button] == PlayerType.NOPLAYER) {
                System.out.println("the button was 0, setting it to" + U.PlayerIdx);
                Button[U.Button] = U.PlayerIdx;
                if (U.PlayerIdx == PlayerType.OPLAYER) {
                    CurrentTurn = PlayerType.XPLAYER;
                    Msg[1] = "Other Players Move.";
                    Msg[0] = "Your Move.";
                } else {
                    CurrentTurn = PlayerType.OPLAYER;
                    Msg[0] = "Other Players Move.";
                    Msg[1] = "Your Move.";
                }
            } else {
                Msg[PlayerToIdx(U.PlayerIdx)] = "Not a legal move.";
            }*/

            
            /*// Check for winners, losers, and a draw
            if (CheckBoard(PlayerType.XPLAYER)) {
                Msg[0] = "You Win!";
                Msg[1] = "You Lose!";
                CurrentTurn = PlayerType.NOPLAYER;*/
            return 0;
        }
        return -1; //it's not your turn 
    }
}
