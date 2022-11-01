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
    public Person CurrentTurn;
    public String[] Msg = new String [2];

    //Reads in the game state, calls players to play until they stand or bust. checks if the participant is a player, bot, or dealer before calling logic
   /*  public static int Update(UserEvent U)
    {
        GameState(){
            participants = new Person[2];               //I added this to get a working model
            participants[0] = new Person(0);
            participants[1] = new Person(1);

            Msg = new String[2];
            //Player type is passed to the constructor (see Person class for more info)
            CurrentTurn = Person[0].type;           //Unsure of this
        }

        System.out.println("The user event is " + U.PlayerIdx + "  " + U.Button);

        if ((CurrentTurn == U.PlayerIdx) && (CurrentTurn == PlayerType.OPLAYER || CurrentTurn == PlayerType.XPLAYER)) {
            // Move is legitimate, lets do what was requested

            // Is the button not taken by X or O?
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
            }

            
            // Check for winners, losers, and a draw
            if (CheckBoard(PlayerType.XPLAYER)) {
                Msg[0] = "You Win!";
                Msg[1] = "You Lose!";
                CurrentTurn = PlayerType.NOPLAYER;
            } else if (CheckBoard(PlayerType.OPLAYER)) {
                Msg[1] = "You Win!";
                Msg[0] = "You Lose!";
                CurrentTurn = PlayerType.NOPLAYER;
            } else if (CheckDraw(U.PlayerIdx)) {
                Msg[0] = "Draw";
                Msg[1] = "Draw";
                CurrentTurn = PlayerType.NOPLAYER;
            }
        }
        return 0;
    }*/

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

    public int StartGame(Person participants[])
    {
        //starts the gameloop
        Msg[0] = "You are Player 1. Your turn";
        Msg[1] = "You are Player 2. Your turn";
        //CurrentTurn = PlayerType.XPLAYER;
        return 0;
    }
}
