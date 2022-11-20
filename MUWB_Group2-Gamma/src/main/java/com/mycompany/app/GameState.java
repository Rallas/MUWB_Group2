package com.mycompany.app;

import java.util.Vector;
import java.util.Random;

import com.mycompany.app.UserEvent.UserEventType;

public class GameState {
    //manages the game state. 
    Vector <Person> participants = new Vector<Person>(); //a vector of all current players and dealer
    int GameId;//simple id that can be used to make each game unique
    public int CurrentTurn;
    public String[] Msg = new String [2];
    CardBank shoeBox = new CardBank();
    int piggybank=0;
    Random rand = new Random();

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
        //first, pads with bots
        //next, deals the appropriate num cards to each player
        //Then, allows the update function to take over running the game
        //the update function checks for a flag on the dealer (the last player in the vector) and then either facilitates play or collection of wagers
        //when the update function determines that all players have gone, it calls a clean-up function that doles winnings and 
        //preps the gamestate for closing/cycling. 

        if(this.participants.size() < 3)
        {
            this.participants.add(0,new Person( 5+rand.nextInt(14), 2+rand.nextInt(4), 100+rand.nextInt(501), this.participants.size()));
        }
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
        
        
        if(participants.lastElement().hasWagered == 0) //have bets been collected?
        {
            for(Person P : participants)
            {
                if ((CurrentTurn == U.PlayerId) && (U.PlayerId == P.PlayerId) && P.type != PlayerType.SPECTATOR)
                {
                    //match wager to minimum wager depth, toggle flag, then increment turn counter.
                }
            }
            
        }//yes, proceed with play
        //reset turn counter now that all have bet
        CurrentTurn = 0;
        if(participants.lastElement().hasWagered == 1)
        {
            
            //find player object to manipulate
            for(Person P : participants)
            {
                if ((CurrentTurn == U.PlayerId) && (U.PlayerId == P.PlayerId) && P.type != PlayerType.SPECTATOR) 
                {
                    if(P.count(P.hand.get(P.currentDepth)) > 21)//check for bust
                    U.Button = 0;
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
                                P.hasGone = 1;
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
                    P.timeOut = 0;
                    return 0; //turn was taken
                }
                //check to make sure there are players waiting. if not, cleanup
                if(participants.lastElement().hasGone == 1)
                {
                    this.Cleanup();
                }
            }
        }
        return -1; //it's not your turn, or cleanup has concluded and nothing will recieve this
    }

    public void Cleanup()
    {
        int totalHandVal = 0;
        
        for(Person P : participants)
        {
            //iterate through each player, totalling hands and dealing bets.
            totalHandVal = 0;
            for(int i = 0; i < P.hand.size() ; i++)
            {
                totalHandVal = P.count(P.hand.get(i));
                if(totalHandVal < 22 && totalHandVal > participants.lastElement().count(participants.lastElement().hand.get(0)))
                {
                    P.winnings = P.winnings + (int)(P.wagers.get(i) * 1.5);
                }
                else
                {
                    this.piggybank = P.wagers.get(i);
                    P.winnings = P.winnings - P.wagers.get(i);
                }
            }
            //reset flags
            P.hasGone = 0;
            P.hasWagered = 0;
            P.timeOut = 0;
            P.currentDepth = 0;
            P.splitDepth = 0;
            //reset other fields (wagers+cards vectors)
            P.hand.clear();
            P.wagers.clear();
            P.hand.add(new CardBank());
            P.wagers.add(0);
            //eject players with no money 
            if(P.winnings < 1)
            {
                participants.remove(P);
            }
            
        }
        //reset turn counter
        this.CurrentTurn = 0;
    }
}


