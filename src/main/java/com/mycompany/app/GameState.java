package com.mycompany.app;

import java.util.Vector;
import java.util.concurrent.TimeUnit;
import java.util.Random;

import java.util.Iterator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.Collections;

import com.mycompany.app.UserEvent.UserEventType;

public class GameState {
    //manages the game state. 
    Vector <Person> participants = new Vector<Person>(0); //a vector of all current players and dealer
    int GameId;//simple id that can be used to make each game unique
    public int CurrentTurn;
    public String[] Msg = new String [5];
    public PlayerType[] Button;         //may be recycled to inform players about the current options for any given turn
    CardBank shoeBox = new CardBank();
    int piggybank=0;
    Random rand = new Random();
    transient GsonBuilder  builder = new GsonBuilder();
    transient Gson gson = builder.create();

    GameState()
    {                  
        participants.add(0,new Person(16,0,0,5));  //dealer

        
        shoeBox.fillDeck();
        GameId = 1;
        CurrentTurn = -1;
        
        Msg[0] = "STANDBY";
        Msg[1] = "STANDBY";
        Msg[2] = "STANDBY";
        Msg[3] = "STANDBY";
        Msg[4] = "STANDBY";

    }

    public void checkShoe()
    {
        Person P = new Person();
        if(P.count(this.shoeBox) < 150)
        this.shoeBox.fillDeck();
        this.shoeBox.updateCardinality();
        System.out.println("\tshoe contains " + this.shoeBox.cardinality);
    }



    public void StartGame(Vector <Person> participants)
    {
        Msg[0] = "StartGame: Await turn plr. 0";
        Msg[1] = "StartGame: Await turn plr. 1";
        Msg[2] = "StartGame: Await turn plr. 2";
        Msg[3] = "StartGame: Await turn plr. 3";
        Msg[4] = "StartGame: Await turn plr. 4";

        //starts the gameloop
        //first, pads with bots
        //next, deals the appropriate num cards to each player
        //Then, allows the update function to take over running the game
        //the update function checks for a flag on the dealer (the last player in the vector) and then either facilitates play or collection of wagers
        //when the update function determines that all players have gone, it calls a clean-up function that doles winnings and 
        //preps the gamestate for closing/cycling. 

        checkShoe();

        int firstUnoccupied = findFirstUnoccupied(this);

        if(this.participants.size() < 3 && firstUnoccupied != -1)
        {
            this.participants.add(new Person( 5+rand.nextInt(14), 2+rand.nextInt(4), 100+rand.nextInt(501), firstUnoccupied));
        }
        else
        {
            this.participants.add(new Person( 5+rand.nextInt(14), 2+rand.nextInt(4), 100+rand.nextInt(501), this.participants.size()));
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
        this.CurrentTurn = 0;        

    }

    //Reads in the user event, updates accordingly
    public int Update(UserEvent U)
    {
        System.out.println("The User Event is " + U.PlayerId + " " + U.Button);
        int hasWagered = 1;
        int hasGone = 1;
        int phase = 0;
        for(Person P : participants)
        {
            if(P.hasWagered == 0 && P.type != PlayerType.SPECTATOR)
            {
                hasWagered = 0;
            }
        }
        for(Person P : participants)
        {
            if(P.hasGone == 0 && P.type != PlayerType.SPECTATOR)
            {
                hasGone = 0;
            }
        }
        if(hasWagered == 0)
        {
            phase = 0;
        }
        else if(hasGone == 0 && hasWagered == 1)
        {
            phase = 1;
        }
        else if (hasGone == 1 && hasWagered == 1)
        {
            phase = 2;
        }
        
        
        
        if(phase == 0) //have bets been collected?
        {
            Msg[this.CurrentTurn] = "Please Place a Bet";
            for(Person P : participants)
            {
                if ((this.CurrentTurn == U.PlayerId) && (U.PlayerId == P.PlayerId) && P.type != PlayerType.SPECTATOR)
                {
                    //match wager to minimum wager depth, toggle flag, then increment turn counter.
                    System.out.println("wager made by " + P.PlayerId + " of " + U.Button);
                    P.wagers.set(P.currentDepth,U.Button);
                    Msg[this.CurrentTurn] = "Please await play";
                    P.hasWagered = 1;
                    this.CurrentTurn++;
                }
            }
            
        }//yes, proceed with play
        else
        if(phase == 1)
        {
            //reset turn counter now that all have bet, but only once
            if(this.CurrentTurn >= participants.size()-1 && hasGone == 0)
            {
                this.CurrentTurn = 0;
            } 
            if(hasWagered == 1)
            {
                if(Msg[this.CurrentTurn] == "Please await play")
                {
                    Msg[this.CurrentTurn] = "It is your turn to play.";
                }

                //find player object to manipulate
                for(Person P : participants)
                {
                    if ((this.CurrentTurn == U.PlayerId) && (U.PlayerId == P.PlayerId) && P.type != PlayerType.SPECTATOR) 
                    {
                        if(P.count(P.hand.get(P.currentDepth)) > 21)//check for bust
                        {
                            U.Button = 0;
                            Msg[this.CurrentTurn] = "You've gone bust!";
                        }
                        // Move is legitimate, lets do what was requested
                        switch(U.Button)
                        {
                            case -2: //stand case
                            {
                                if(P.currentDepth < P.splitDepth)
                                {
                                    P.currentDepth++;
                                    Msg[this.CurrentTurn] = "Swapping to next hand...";
                                }
                                else
                                {
                                    P.hasGone = 1;
                                    Msg[this.CurrentTurn] = "Please await tally";
                                    this.CurrentTurn++;
                                    
                                }
                                break;
                            }
                            case -3: //hit
                            {
                                System.out.println("\n\nType " + P.type + " id " + P.PlayerId + "attempting hit.\n");
                                System.out.println("\tp hand before hit: " + gson.toJson(P.hand.get(P.currentDepth)));
                                P.Hit(P.hand.get(P.currentDepth), this.shoeBox);
                                System.out.println("\tp hand after hit: " + gson.toJson(P.hand.get(P.currentDepth)) + "\n\n");
                                
                                Msg[this.CurrentTurn] = "Hitting on this hand.";
                                break;
                            }
                            case -4: //split
                            {
                                int targetForSplit = P.Split(P.hand.get(P.currentDepth));
                                if(targetForSplit != -2)
                                {
                                    P.addSplitdeck(targetForSplit);
                                    Msg[this.CurrentTurn] = "Split found and divvied.";
                                }
                                break;
                            }
                            case -5: //double
                            {
                                P.wagers.set(P.currentDepth,(P.wagers.get(P.currentDepth))*2);
                                Msg[this.CurrentTurn] = "Wager on this hand doubled.";
                                break;
                            }
                            case 99: //cheat hit
                            {
                                P.cheatHit(P.hand.get(P.currentDepth), this.shoeBox);
                                Msg[this.CurrentTurn] = "Filthy cheat.";
                                break;
                            }
                        }
                        P.timeOut = 0;
                        
                        return 0; //turn was taken
                    }
                    //check to make sure there are players waiting. if not, cleanup
                    
                    if(phase == 2)
                    {
                        this.Cleanup();
                    }
                    
                }
            }
        }
        return -1; //it's not your turn, or cleanup has concluded and nothing will recieve this
    }

    public synchronized void Cleanup()
    {
        int totalHandVal = 0;
        
        for(Iterator<Person> it = participants.iterator(); it.hasNext();)
        {
            //iterate through each player, totalling hands and dealing bets.
            Person P = it.next();
            totalHandVal = 0;
            for(int i = 0; i < P.hand.size() ; i++)
            {

                totalHandVal = P.count(P.hand.get(i));
                if(totalHandVal < 22 && totalHandVal > participants.firstElement().count(participants.firstElement().hand.get(0)) && P.type != PlayerType.DEALER)
                {
                    P.winnings = P.winnings + (int)(P.wagers.get(i) * 1.5);
                    Msg[P.PlayerId] = "Hand won.";
                }
                else
                {
                    this.piggybank = P.wagers.get(i);
                    P.winnings = P.winnings - P.wagers.get(i);
                    Msg[P.PlayerId] = "Hand lost.";
                }
            }
            try
            {
                wait(7000);
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
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
            if(P.winnings < 1 && P.type != PlayerType.DEALER)
            {

                it.remove();
            }
            
        }
        //reset turn counter
        this.CurrentTurn = 0;
        Msg[0] = "StartGame: Await turn plr. 0";
        Msg[1] = "StartGame: Await turn plr. 1";
        Msg[2] = "StartGame: Await turn plr. 2";
        Msg[3] = "StartGame: Await turn plr. 3";
        Msg[4] = "StartGame: Await turn plr. 4";
        //check the shoe
        checkShoe();

        //redeal and let spectators in 
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
        
    }

    public int findFirstUnoccupied(GameState G)
    {
        int firstID = -1;
        int[] isPresent = new int[6];
        for(int i = 0; i < 5; i++)
        {
            isPresent[i] = 0;
        }
        for(Person P : G.participants)
        {
            isPresent[P.PlayerId] = 1;
        }
        for(int i : isPresent)
        {
            if(firstID == -1 && isPresent[i] == 0)
            {
            firstID = i;
            }
        }
        return firstID;
    }
}


