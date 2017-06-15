package Cards;

public class DealerCardHand extends CardHand
{

    public DealerCardHand()
    {
        super();
    }
    

    public boolean add(Card card)
    {
        boolean cardAdded = false;
        
        if (!isBust() && !hasBlackjack())
        {            
            cardAdded = super.add(card);
        }
        
        return (cardAdded) ? true : false;
    }
}