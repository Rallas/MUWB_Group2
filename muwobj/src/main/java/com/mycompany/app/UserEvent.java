package com.mycompany.app;

public class UserEvent {
    int GameId; // the game ID on the server
    Person PlayerIdx; // either an Player # or the Dealer
    int Button; // button number from 0 to 8; Needs to be updated to a hand or card
}

//THIS CLASS IS POORLY UNDERSTOOD AND MAY BE REFACTORED OR REMOVED LATER TO MATCH THE CLASS DIAGRAM AND SPECIFICATIONS. 
//IT IS ONLY PRESENT TO ENSURE MINIMAL WORKING STATE.