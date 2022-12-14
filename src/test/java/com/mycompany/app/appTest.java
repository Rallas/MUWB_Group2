package com.mycompany.app;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.util.Vector;

public class appTest extends TestCase{

    @Test
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

    @Test
    public void testCorrectCardAmount()
    {
        //With a deck of 6 we have 52*6 = 312 cards
        //So our exptected value when calling checkshoe is 312
        GameState g = new GameState();
        g.checkShoe();
        assertEquals(312, g.shoeBox.cardinality);
    }


    @Test
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

    @Test
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

    @Test
    public void testCount()
    {
        CardBank deck = new CardBank();
        deck.fillDeck();
        deck.updateCardinality();

        Actions a = new Actions();
        a.count(deck);
        assertEquals(2040, a.count(deck));
    }

    @Test
    public void testGetCardValue() {
        Actions a = new Actions();

        assertEquals(11, a.getCardVal(1, 1));
        assertEquals(1, a.getCardVal(1, 0));
        assertEquals(2, a.getCardVal(4, 0));
        assertEquals(10, a.getCardVal(40, 0));
    }

    /*@Test
    public void testPhaseIncrementation()
    {
        GameState g = new GameState();
        UserEvent u = new UserEvent();
        u.Button = 0;
        u.PlayerId = 0;
        u.GameId = 1;

        g.CurrentTurn = 7;
        g.Update(u);
        assertEquals(1, g.phase);
        assertEquals(1, g.CurrentTurn);
    }

    /*
    public void testStand()
    {
        Person p = new Person(1,0);
        p.hasWagered = 0;
        GameState g = new GameState();
        UserEvent u = new UserEvent();
        Vector<Person> v = new Vector<>(0);
        v.add(p);
        g.phase = 1;
        u.Button = -2;
        p.currentDepth = 0;
        p.splitDepth = 10;
        g.CurrentTurn = 0;
        g.StartGame(v);
        g.Update(u);
        assertEquals("Swapping to next hand...", g.Msg[g.CurrentTurn]);
    }

    public void testDouble()
    {
        GameState g = new GameState();
        g.CurrentTurn = 0;
        UserEvent u = new UserEvent();
        u.Button = 100;
        u.PlayerId = 0;
        Vector <Person> v = new Vector<>(0);
        Person p = new Person(1,0);
        //p.wagers.add(100);
        p.hasDoubled = 0;
        p.currentDepth = 0;
        g.participants.add(p);
        g.phase = 0;
        g.StartGame(v);
        g.Update(u);
        u.Button = -5;
        g.phase = 1;
        g.CurrentTurn = 0;
        u.PlayerId = 0;
        p.PlayerId = 0;
        g.Update(u);


        assertEquals("Wager on this hand doubled.", g.Msg[p.PlayerId]);
    }*/

    @Test
    public void testEmptyDeck()
    {
        CardBank c = new CardBank();
        c.fillDeck();
        c.emptyDeck();
        int arr[] = new int[52];
        for (int i = 0; i < 52; i++)
        {
            arr[i] = 0;
        }
        Assertions.assertArrayEquals(arr, c.deck);
    }

    @Test
    public void testStartGame()
    {
        Person p = new Person();
        Vector <Person> v = new Vector<>(0);
        v.add(p);
        GameState g = new GameState();
        g.StartGame(v);
        assertEquals(0, g.CurrentTurn);
    }

    @Test
    public void testCheatHit()
    {
        Actions a = new Actions();
        CardBank bank = new CardBank();
        CardBank deck = new CardBank();
        bank.fillDeck();
        deck.fillDeck();
        a.cheatHit(deck, bank);
        assertEquals(313, deck.cardinality);
        assertEquals(311, bank.cardinality);
    }

    @Test
    public void testFirstUnoccupied()
    {
        Vector<Person> v = new Vector<>(0);
        Person p = new Person(1,0);
        p.PlayerId = 0;
        v.add(p);
        GameState g = new GameState();
        g.StartGame(v);
        assertEquals(2, g.findFirstUnoccupied(g));
        //It is 2 because the dealer is added I'm pretty sure
    }
}
