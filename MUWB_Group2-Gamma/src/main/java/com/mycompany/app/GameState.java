package com.mycompany.app;

import java.util.Vector;

import com.mycompany.app.UserEvent.UserEventType;

public class GameState {
    //manages the game state. 
    Vector <Person> participants = new Vector<Person>(); //a vector of all current players and dealer
    int GameId;//simple id that can be used to make each game unique
    public int CurrentTurn;
    public String[] Msg = new String [2];
    public PlayerType[] Button;         //may be recycled to inform players about the current options for any given turn
    CardBank shoeBox = new CardBank();
    int piggybank=0;

    GameState()
    {                  
        participants.add(0,new Person(16,0,0,0));  //dealer

        
        shoeBox.fillDeck();
        GameId = 1;
        CurrentTurn = -1;
        
        Msg[0] = "DEALER MSG";
        Msg[1] = "PLAYER 1 MSG";
    }

    


    public void StartGame(Vector <Person> participants)
    {
        Msg[0] = "StartGame: Msg 0";
        Msg[1] = "StartGame: Msg 1";
        //starts the gameloop
        //First, deals the appropriate num cards to each player
        //next, collects wagers.
        //Then, allows the update function to take over running the game
        //when the update function determines that all players have gone, it calls a clean-up function that doles winnings and 
        //preps the gamestate for closing/cycling. 

        for(Person P : participants)
        {
            if(P.type == PlayerType.SPECTATOR)
            {
                P.type = PlayerType.PLAYER;
            }
            if(P.type != PlayerType.DEALER)
            {
                P.Deal(P.hand.get(P.currentDepth), this.shoeBox);
            }
            else
            {
                P.Hit(P.hand.get(P.currentDepth), this.shoeBox);
            }
        }
        CurrentTurn = 0;        

    }

    //Reads in the user event, updates accordingly
    public int Update(UserEvent U)
    {
        System.out.println("The User Event is " + U.PlayerId + " " + U.Button);
        //find player object to manipulate
        for(Person P : participants)
        {
            if ((CurrentTurn == U.PlayerId) && (U.PlayerId == P.playerID)) 
            {
                // Move is legitimate, lets do what was requested
                switch(U.Button)
                {
                    case 0: //stand case
                    {
                        if(P.currentDepth <= P.splitDepth)
                        {
                            P.currentDepth++;
                        }
                        else
                        {
                            this.CurrentTurn++;
                        }
                        break;
                    }
                    case 1: //hit
                    {
                        P.Hit(P.hand.get(P.currentDepth), this.shoeBox);
                        break;
                    }
                    case 2: //split
                    {
                        int targetForSplit = P.Split(P.hand.get(P.currentDepth));
                        if(targetForSplit != -2)
                        {
                            P.addSplitdeck(targetForSplit);
                        }
                        break;
                    }
                    case 3: //double
                    {
                        P.wagers.set(P.currentDepth,(P.wagers.get(P.currentDepth))*2);
                        break;
                    }
                    case 99: //cheat hit
                    {
                        P.cheatHit(P.hand.get(P.currentDepth), this.shoeBox);
                        break;
                    }
                }
            
                return 0;
            }
        }
        return -1; //it's not your turn 
    }
}
