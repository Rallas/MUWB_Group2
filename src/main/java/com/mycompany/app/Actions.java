package com.mycompany.app;

import java.util.Random;

public class Actions {
    //contains most card-bound logical functionality that can be taken by players
    //factory method abandoned. was a solution looking for a problem and unnecessary 

    public int findCard(CardBank stack)
    {
        Random rand = new Random();
        int currentTarget = -1;
        int acceptedTarget = -1;
        currentTarget = rand.nextInt(52);
        
        while(acceptedTarget == -1)
        {
            if(stack.deck[currentTarget] > 0)
            {
                acceptedTarget = currentTarget;
            }
            else
            {
                currentTarget = rand.nextInt(52);
            }
        }
        System.out.println("\tCurrent random card value of " + currentTarget);
        return acceptedTarget;
    }

    public void Deal(CardBank deck, CardBank bank)
    {

        //takes in a deck (array) of cards and the bank and deals two card to the person from the bank. 
        Hit(deck, bank);
        Hit(deck, bank);
    }

    public void Hit(CardBank deck, CardBank bank)
    {
        //functions like Deal but at reduced rate
        int target = findCard(bank);
        bank.deck[target]--;
        deck.deck[target]++;
        
    }

    public int Split(CardBank deck)
    {
        //takes a players hand and splits it, adjusting the bet as well. players can split on the first duplicated card in hand.

        //THIS METHOD IS TO FIND THE FIRST CARD WHICH HAS MORE THAN 1 OF ITS KIND IN THE DECK, THEN RETURN ONE OF TWO STATUS CODE TYPES:
        // -2 = NO CARD FOUND
        // 0-51 = DUPLICATE CARD FOUND AT INDEX RETURNED

        int targetCard = -2;
        for(int i = 0; i<52;i++)
        {
            if(targetCard == -2 && deck.deck[i]>1)
            {
                targetCard = i;
            }
        }

        return targetCard;
    }

    public int getCardVal(int index, int acesVal)
    {
        int val = 0;
        if(index == 1)
        {
            if(acesVal == 1)
            {
                val = 11;
            }
            else
            {
                val = 1;
            }
        }
        else
        {
            val = (index/4)+1;
            if(val>10)
            {
                val = 10;
            }
        }
        System.out.println("\tvalue requested for " + index + " at aces Val " + acesVal + " for value " + val + "\n");
        return val;
    }

    public int count(CardBank deck)
    {
        int[] stack = deck.deck;
        int count=0;
        int acesHigh = 1;
        int miniStack = 0;
        for(int i =0; i<52;i++)
        {
            if(stack[i] > 0)
            {
                miniStack = stack[i] * getCardVal(i,acesHigh);
                count = count + miniStack;
            }
            
        }
        if(count > 21)
        {
            count = 0;
            acesHigh = 0;
            for(int i =0; i<52;i++)
            {
                if(stack[i] > 0)
                {
                    miniStack = stack[i] * getCardVal(i,acesHigh);
                    count = count + miniStack;
                }
                
            }
        }
        System.out.println("\tCounting passed with val " + count + "\n");
        return count;

    }

    public void cheatHit(CardBank deck, CardBank bank)
    {
        int targetCard = 0;
        int targetSum = 0;
        targetSum = 21 - count(deck);
        for(int i = 0; i < 52; i++)
        {
            if(getCardVal(bank.deck[i],0) <= targetSum && bank.deck[i] > 0)
            {
                targetCard = i;
            }
        }
        deck.deck[targetCard]++;
        bank.deck[targetCard]--;
    }

    
}
