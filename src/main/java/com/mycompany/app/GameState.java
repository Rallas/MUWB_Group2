package com.mycompany.app;

public class GameState {
    //manages the game state.
    Person participants[]; //a loopable array of all current players and dealer
    Card CardBank[][]; //indicates remaining cards in the bank. position indicates value
    int GameId;//simple id that can be used to make each game unique
    //I am anticipating multi-connection online play, with host/client structure.
    //by storing the game state as a single class I can pass around a 'saved game'
    //in json format, with the host using the  ID to manage multiple games with multiple players
    //This means any given game can be represented by a json of this class
    public int CurrentTurn;
    public String[] Msg = new String [2];
    public PlayerType[] Button;         //may be recycled to inform players about the current options for any given turn

    GameState(){
        participants = new Person[2];               //Not sure if this line is needed - JM
        participants[0] = new Person(0);        //dealer
        participants[1] = new Person(1);         //player 1

        Button = new PlayerType[5];
        for (int i = 0; i < Button.length; i++) {
            Button[i] = PlayerType.SPECTATOR;
        }
        //Card CardBank = new CardBank();
        GameId = 1;
        //CurrentTurn = participants[1].type;
        Msg[0] = "You're the dealer. You should be a bot....Huh";
        Msg[1] = "Welcome. The game will begin shortly";
    }

    //Reads in the game state, calls players to play until they stand or bust. checks if the participant is a player, bot, or dealer before calling logic
    public static int setHand()
    {
        //collects bets and deals
        return 0;
    }

    public static int count(Card deck[])
    {
        int deck_val = 0;    //Needs to count the value of the deck of cards
        for (Card i: deck){
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
    }

    public void StartGame(Person participants[])
    {
        //starts the gameloop
        Msg[0] = "StartGame: You are the dealer. Huh....";
        Msg[1] = "StartGame: You are Player 1. Your turn";
        CurrentTurn = 1;                
    }

    public static int Update(UserEvent U)
    {
        System.out.println("The User Event is " + U.PlayerIdx + " " + U.Button);

        

        return 0;
    }
}
