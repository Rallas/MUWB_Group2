package com.mycompany.app;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.junit.jupiter.api.Assertions;

import javax.swing.*;
import java.util.Vector;

public class AppTest extends TestCase{

    public void testCardBankCreation()
    {
        //This just tests if we are creating the card bank and putting the right number into each value of the for loop
        //It's  a simple test but its important that we have 6 copies of every card in the deck
        int arr[] = new int[52];
        for(int i = 0; i<52 ; i++){
            arr[i] = 6;
        }
        CardBank bank = new CardBank();
        bank.fillDeck();
        bank.updateCardinality();
        Assertions.assertArrayEquals(arr, bank.deck);
    }

    public void testCorrectCardAmount()
    {
        //With a deck of 6 we have 52*6 = 312 cards
        //So our exptected value when calling checkshoe is 312
        GameState g = new GameState();
        g.checkShoe();
        assertEquals(312, g.shoeBox.cardinality);
    }

    /*public void testFirstUnoccupied()
    {
        //tests if the game is star
        Person p = new Person(1, 0);
        Person p2 = new Person(1, 1);
        Vector<Person> v = new Vector<Person>();
        //v.add(p);
        //v.add(p2);
        GameState g = new GameState();
        //g.participants.add(p);
        //g.participants.add(p2);
        g.StartGame(v);
        //assertEquals(1, );
    }*/

    public void testDeal()
    {
        CardBank bank = new CardBank();
        CardBank deck = new CardBank();
        bank.fillDeck();
        deck.fillDeck();
        bank.updateCardinality();
        deck.updateCardinality();

        Actions a = new Actions();
        a.Deal(deck, bank);
        assertEquals(314, deck.cardinality);
        assertEquals(310, bank.cardinality);
    }

    public void testHit()
    {
        CardBank bank = new CardBank();
        CardBank deck = new CardBank();
        bank.fillDeck();
        deck.fillDeck();
        bank.updateCardinality();
        deck.updateCardinality();

        Actions a = new Actions();
        a.Hit(deck, bank);
        assertEquals(313, deck.cardinality);
        assertEquals(311, bank.cardinality);
    }

    public void testCount()
    {
        CardBank deck = new CardBank();
        deck.fillDeck();
        deck.updateCardinality();

        Actions a = new Actions();
        a.count(deck);
        assertEquals(2040, a.count(deck));
    }

    public void testGetCardValue() {
        Actions a = new Actions();

        assertEquals(11, a.getCardVal(1, 1));
        assertEquals(1, a.getCardVal(1, 0));
        assertEquals(2, a.getCardVal(4, 0));
        assertEquals(10, a.getCardVal(40, 0));
    }

    public void testPhaseIncrementation()
    {
        GameState g = new GameState();
        UserEvent u = new UserEvent();

        g.CurrentTurn = 7;
        g.Update(u);
        assertEquals(1, g.phase);
        assertEquals(1, g.CurrentTurn);
    }

    public void testStand()
    {
        Person p = new Person(1,0);
        GameState g = new GameState();
        UserEvent u = new UserEvent();
        Vector<Person> v = new Vector<>(0);
        v.add(p);
        g.participants.add(p);
        g.phase = 1;
        g.CurrentTurn = 0;

        u.Button = -2;
        u.PlayerId = 0;

        g.StartGame(v);
        g.Update(u);
    }
}
