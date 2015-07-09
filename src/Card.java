
public class Card {

    private Suit suit;
    private int number;
    public static enum Suit {
	SPADE, HEART, DIAMOND, CLUB, QUEEN, KING;

	 @Override
	public String toString() {
	    switch(this) {
	    case SPADE: return "\u2660" ;
	    case HEART: return (char)27 + "[31m\u2665" + (char)27 + "[0m";
	    case DIAMOND: return (char)27 + "[33m\u2666" + (char)27 + "[0m";
	    case CLUB: return (char)27 + "[32m\u2663" + (char)27 + "[0m";
	    case QUEEN : return (char)27 +"[34mQ" + (char)27 + "[0m";	
	    case KING : return (char)27 + "[34mK" + (char)27 + "[0m";
	    default: throw new IllegalArgumentException();
	    }
	}
    }

    public Card() { }
    public Card(Suit suit, int number) {
	this.suit = suit;
	this.number = number;
    }
    public Suit getSuit() {
	return this.suit;
    }
    public int getNumber() {
	return this.number;
    }
    public void printOutCard() {
	String _printOut = "";
	switch(suit) {
	case SPADE: _printOut = "\u2660";
	    break;
	case HEART: _printOut = (char)27 + "[31m\u2665";
	    break;
	case DIAMOND: _printOut = (char)27 + "[33m\u2666";
	    break;
	case CLUB: _printOut = (char)27 +"[32m\u2663";
	    break;
	case QUEEN:_printOut = (char)27 +"[34mQ";
	    break;
	case KING: _printOut = (char)27 + "[34mK";
	    break;
	}
	    
	System.out.print(_printOut);
	if(suit!=Suit.KING && suit!=Suit.QUEEN) {
	    System.out.print(number+" ");
	}
	else{
	    System.out.print(" ");
	}	    
	System.out.print((char)27 +"[0m");
    }
}
