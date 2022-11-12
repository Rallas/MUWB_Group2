package com.mycompany.app;

public class Actions {
    //contains all logical functionality that can be taken by players
    //this is very loosely based on a variation of the command pattern
    //This functionality could be baked into the person class but I wanted to seperate it out
    //in the future, if i need to set up different players with different types of action
    //I can change this to an interface and extend a more complex interface from it

    //actions are functions which are inhereted by players so that I can give more advanced AI players more capabilities
    //by the use of interfaces as mentioned above. Originally was going to use multiple inheretance with each action as a class
    //to exactly mimic the structure defined by Bob Nystrom, but java doesn't support that :( 
    //(Watch his talk 'is there more to game architecture than ecs?' it's really good)

    public static int Deal(Card deck[], Card bank[])
    {

        //takes in a deck (array) of cards and the bank and deals two card to the person from the bank. returns status code
        return 0;
    }

    public static int Hit(Card deck[], Card bank[])
    {
        //functions like Deal but at reduced rate
        return 0;
    }

    public static int Split(Card deck[], Card splitDeck[])
    {
        //takes a players hand and splits it, adjusting the bet as well. players can only split if their first two cards are
        //the same. double aces must be only be hit once after a split
        return 0;
    }

    public static int Stand()
    {
        //signals the gamestate that the player is done with their turn
        return 0;
    }

    public static int Double()
    {
        //used when the first to cards sum between 10 and 13 to double the wager
        return 0;
    }

    public static int Bet(int wager)
    {
        //used to increase the player's bet before the deal
        //Person.setWager(50);
        return 0; 
    }


    //THIS IS MARKED FOR REMOVAL AND SEPERATION OF LOGIC. AS PER THE CLASS DIAGRAM, THIS WILL BE DIVIDED INTO A NUMBER OF EASILY EXTENSIBLE CLASSES
    //SUCH THAT AN DAUGHTERS OF THE PLAYER CLASS IMPLEMENT A COLLECTION OF THESE ACTIONS. THIS EXISTS PURELY TO ENSURE MINIMUM WORKING STATE
    //AND WILL BE ENTIRE REMOVED AT A LATER DATE
}
