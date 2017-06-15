package Cards;

public class Card
{
 
    private Suit suit;
    private Face face;
    private int code;
    

    public Card(Face face, Suit suit, int code)
    {
        setFace(face);
        setSuit(suit);
        setCode(code);
    }
    

    private void setFace(Face face)
    {
        this.face = face;
    }
    

    public Face getFace()
    {
        return this.face;
    }
    

    private void setSuit(Suit suit)
    {
        this.suit = suit;
    }
    

    public Suit getSuit()
    {
        return this.suit;
    }
    

    private void setCode(int code)
    {
        this.code = code;
    }
    

    public int getCode()
    {
        return code;
    }
    

    public int getValue()
    {
        return this.getFace().getValue();
    }
    

    public String toString()
    {
        return getFace() + " of " + getSuit();
    }
}