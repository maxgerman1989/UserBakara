package Cards;

import java.util.*;

public class Deck extends Stack<Card>
{
   
    private int numberOfPacks;
    
   
    public Deck()
    {
        super();
        
        setNumberOfPacks(1);
        
        this.addAll(new CardPack());
        
        shuffle();
    }
    
    public Deck(int numberOfPacks)
    {
        super();
        
        setNumberOfPacks(numberOfPacks);
        
        for (int i = 0; i < numberOfPacks; i++)
        {
            this.addAll(new CardPack());
        }
        
        shuffle();
    }
    

    private void setNumberOfPacks(int number)
    {
        this.numberOfPacks = number;
    }
    

    public int getNumberOfPacks()
    {
        return this.numberOfPacks;
    }
    
  
    public void shuffle()
    {
        Collections.shuffle(this);
    }
    
  
    public Card deal()
    {
        if (this.empty())
        {
            System.out.println("Run out of cards. New Deck.");
            for (int i = 0; i < numberOfPacks; i++)
            {
                this.addAll(new CardPack());
            }

            shuffle();
        }
        
        return this.pop();
    }
}