package Cards;

import java.util.*;

public class CardPack extends Stack<Card>
{
    public static final int CARDS_IN_PACK = 52;
    

    public CardPack()
    {
        super();
        
        final String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        
        int cardCode = 1;
        
        for (String suit : suits)
        {
            for (int i = 1; i < 14; i++)
            {
                this.push(new Card(new Face(i), new Suit(suit), cardCode));
                cardCode++;
            }
        } 
    }
}