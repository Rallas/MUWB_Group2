package com.mycompany.app;

public class Person extends Actions{
    //class that defines the players and dealer. Varient of the type-object pattern
    int type; //player (1) or dealer (0). int to allow for possibility of multiple player types.
    Card hand[]; //stores cards. array to facilitate looping. position indicates value.
    Card splitHand[]; //for split actions
    int wager;

    //THIS IS MARKED FOR REFACTORING AND EXISTS ONLY TO ENSURE MINIMAL WORKING STATE

    //AS PER THE CLASS DIAGRAM, THIS WILL BE REFACTORED INTO A FACTORY METHOD INVOLVING 
    //A NUMBER OF INTERFACES SUCH THAT ACTIONS MAY BE CLEANLY DECOUPLED FROM THE PLAYER TYPE
    //AND TO ENSURE THAT EACH PLAYER DAUGHTER CLASS IMPLEMENTS THE EXACT SAME LOGIC 

}
