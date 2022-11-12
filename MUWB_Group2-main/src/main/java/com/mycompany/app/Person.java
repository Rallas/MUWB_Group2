package com.mycompany.app;

public class Person extends Actions{
    PlayerType type; //player (nonzero) or dealer (0). int to allow for possibility of multiple player types.
    int playerID;
    CardBank hand = new CardBank(); //stores cards. array to facilitate looping. position indicates value.
    int wager = 0;

    Person(int ID){
        this.type = PlayerType.values()[1];
        this.playerID = ID;
    }

    Person(int type, int ID){
        this.type = PlayerType.values()[type];
        this.playerID = ID;
    }
    Person(int type, int wager, int ID){
        this.type = PlayerType.values()[type];
        this.wager = wager;
        this.playerID = ID;
    }

    public int getWager() {
        return wager;
    }
    public void setWager(int wager) {
        this.wager = wager;
    }

    public CardBank getHand()
    {
        return this.hand;
    }
    
    //THIS IS MARKED FOR REFACTORING AND EXISTS ONLY TO ENSURE MINIMAL WORKING STATE

    //AS PER THE CLASS DIAGRAM, THIS WILL BE REFACTORED INTO A FACTORY METHOD INVOLVING 
    //A NUMBER OF INTERFACES SUCH THAT ACTIONS MAY BE CLEANLY DECOUPLED FROM THE PLAYER TYPE
    //AND TO ENSURE THAT EACH PLAYER DAUGHTER CLASS IMPLEMENTS THE EXACT SAME LOGIC 

}
