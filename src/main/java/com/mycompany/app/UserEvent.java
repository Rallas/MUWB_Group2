package com.mycompany.app;

public class UserEvent {

    public enum UserEventType{
        DEAL, HIT, SPLIT, STAND, DOUBLE, SURRENDER, BET;
    };

    int GameId; // the game ID on the server
    int PlayerId; // the ID of the player
    int Button; // button number from 0 to 4. May be greater to represent a BET;
    UserEventType Event; 
}

