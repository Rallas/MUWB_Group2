package com.mycompany.app;

import java.util.Vector;

import com.mycompany.app.UserEvent.UserEventType;

public class GameState {
    //manages the game state. 
    Vector <Person> participants = new Vector<Person>(); // a vector of all current players and dealer
    //Person participants[];
    int GameId;//simple id that can be used to make each game unique
    //I am anticipating multi-connection online play, with host/client structure.
    //by storing the game state as a single class I can pass around a 'saved game'
    //in json format, with the host using the  ID to manage multiple games with multiple players
    //This means any given game can be represented by a json of this class
    public int CurrentTurn = 0;
    public PlayerType Turn_Cycle;
    public String[] Msg = new String [2];
    public int Button[] = new int [5];    //may be recycled to inform players about the current options for any given turn
    CardBank shoeBox = new CardBank();

    GameState()
    {                  
        participants.add(new Person(0,0));  //dealer

        shoeBox.fillDeck();
        GameId = 1;
        CurrentTurn = 0;
        Turn_Cycle = PlayerType.PLAYER;                     //This needs to be set to Player so they can set their bet
        for (int i: Button){
           Button[i] = 0;
        }
        Msg[0] = "DEALER MSG";
        Msg[1] = "PLAYER 1 MSG";
    }

    //Reads in the game state, calls players to play until they stand or bust. checks if the participant is a player, bot, or dealer before calling logic
    public int setHand(UserEvent U)
    {
        //collects bets and deals
        for (Person i: participants){
            if (i.PlayerId == U.PlayerId){
                i.winnings = i.winnings - U.Button;
            }
        }
        
        participants.elementAt(0).hand.addCard(20);
        participants.elementAt(0).hand.addCard(2);
        participants.elementAt(1).hand.addCard(3);
        participants.elementAt(1).hand.addCard(3);

        //Button[0] = 1;
        //Button[1] = 1;
      //  CheckForOptions();
        CurrentTurn++;
        Turn_Cycle = PlayerType.PLAYER;
        return 0;
    }

   /*  public int CheckForOptions(){
        int temp = -1; 

        for (Person i: participants){           //Check for a split
            for (Card j: i.getHand()){
                for (int k: j){
                    if (k == j.value){
                        
                    }
        }
    }*/

    /*public static int count(int[] deck)
    {
    }

    public static int packageAndPrint(Person participants[])
    {
     
    }*/

    public void StartGame(Vector <Person> participants)
    {
        Msg[0] = "StartGame: Msg 0";
        Msg[1] = "StartGame: Msg 1";
        //starts the gameloop
        CurrentTurn = 0;        

    }

    public void addPlayer(PlayerType p){  //These 2 were recycled from Poker sample code. Unknown if still needed 
        participants.add(new Person(10, 15));
    }

    public void removePlayer(int PlayerId){
        participants.remove(PlayerId - 1);
    }


    public int Update(UserEvent U) //May need to alter game state & check for a winner if it's not implemented elsewhere
    {
       // int count = 0;
        System.out.println("The User Event is: " + U.Event + " Player: " + U.PlayerId + " Sent Button: " + U.Button);

        Msg[0] = "The User Event is " + U.PlayerId + " " + U.Button;
        Msg[1] = "The User Event is " + U.PlayerId + " " + U.Button;

        if (U.Event.equals(UserEventType.BET)){
            
            Msg[0] = "A BET of " + U.Button + " was made by Player " + U.PlayerId;
            Msg[1] = "A BET of " + U.Button + " was made by Player " + U.PlayerId;
            
            setHand(U);
            return 0;
        }
        else if (U.Event.equals(UserEventType.STAND)){

            Msg[0] = "A STAND was made by Player " + U.PlayerId;
            Msg[1] = "A STAND was made by Player " + U.PlayerId;
            
            return 0;
        }
        else if (U.Event.equals(UserEventType.HIT)){

            Msg[0] = "A HIT was done by Player " + U.PlayerId;
            Msg[1] = "A HIT was done by Player " + U.PlayerId;
            
            return 0;
        }
        else if (U.Event.equals(UserEventType.SPLIT)){

            Msg[0] = "A SPLIT was done by Player " + U.PlayerId;
            Msg[1] = "A SPLIT was done by Player " + U.PlayerId;
            
            return 0;
        }
        else if (U.Event.equals(UserEventType.DOUBLE)){

            Msg[0] = "A DOUBLE DOWN was made by Player " + U.PlayerId;
            Msg[1] = "A DOUBLE DOWN was made by Player " + U.PlayerId;
            
            return 0;
        }
        return -1; //it's not your turn 
    }

    /*public int Update(UserEvent U)
    {
        System.out.println("The User Event is " + U.PlayerId + " " + U.Button);
        //find player object to manipulate
        for(Person P : participants)
        {
            if ((CurrentTurn == U.PlayerId) && (U.PlayerId == P.PlayerId)) 
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
                        P.addSplitdeck(targetForSplit);
                        break;
                    }
                    case 3: //double
                    {

                    }
                    case 99: //cheat hit
                    {

                    }
                }

                return 0;
            }
        }
        return -1; //it's not your turn /* */
    //}
}
