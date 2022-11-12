package com.mycompany.app;

public class Person extends Actions{
    //class that defines the players and dealer. Varient of the type-object pattern
    PlayerType type; //player (nonzero) or dealer (0). int to allow for possibility of multiple player types.
    Card hand[] = new Card[5]; //stores cards. array to facilitate looping. position indicates value.
    Card splitHand[]; //for split actions
    int wager = 0;

    Person(){
        this.type = PlayerType.values()[1];
        this.hand[0] = new Card();
        this.hand[1] = new Card();
    }

    Person(int type){
        this.type = PlayerType.values()[type];
        this.hand[0] = new Card();
        this.hand[1] = new Card();
    }
    Person(int type, int wager){
        this.type = PlayerType.values()[type];
        this.wager = wager;
        this.hand[0] = new Card();
        this.hand[1] = new Card();
    }

    public int getWager() {
        return wager;
    }
    public void setWager(int wager) {
        this.wager = wager;
    }
    public String getHand() {           //Modified from Card for testing purposes
        return ("Card 1: " + hand[0].getValue() + " " + hand[0].getSuite() + " & Card 2: " + hand[1].getValue() + " " + hand[1].getSuite());
    }
    public Card[] getSplitHand() {              //How should we implement this?
        return splitHand;
    }
    //THIS IS MARKED FOR REFACTORING AND EXISTS ONLY TO ENSURE MINIMAL WORKING STATE

    //AS PER THE CLASS DIAGRAM, THIS WILL BE REFACTORED INTO A FACTORY METHOD INVOLVING 
    //A NUMBER OF INTERFACES SUCH THAT ACTIONS MAY BE CLEANLY DECOUPLED FROM THE PLAYER TYPE
    //AND TO ENSURE THAT EACH PLAYER DAUGHTER CLASS IMPLEMENTS THE EXACT SAME LOGIC 

}
