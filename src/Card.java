
import java.util.Comparator;



public class Card {

    private Suit suit;
    private int number;
    private boolean isKey;
    public static enum Suit {
	SPADE(0), HEART(1), DIAMOND(2), CLUB(3), L_JOKER(4), H_JOKER(5);
	private int value;
	private Suit(int value) {
	    this.value = value;
	}
	public int getValue() {
	    return this.value;
	}


	 @Override
	public String toString() {
	    switch(this) {
	    case SPADE: return "\u2660" ;
	    case HEART: return (char)27 + "[31m\u2665" + (char)27 + "[0m";
	    case DIAMOND: return (char)27 + "[33m\u2666" + (char)27 + "[0m";
	    case CLUB: return (char)27 + "[32m\u2663" + (char)27 + "[0m";
	    case L_JOKER : return (char)27 +"[34mL" + (char)27 + "[0m";	
	    case H_JOKER : return (char)27 + "[34mH" + (char)27 + "[0m";
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
	case L_JOKER:_printOut = (char)27 +"[34mL";
	    break;
	case H_JOKER: _printOut = (char)27 + "[34mH";
	    break;
	}
	    
	System.out.print(_printOut);
	if (suit!=Suit.H_JOKER && suit!=Suit.L_JOKER) {
	    if (number == 11) 
		System.out.print("J ");
	    else if (number == 12)
		System.out.print("Q ");
	    else if (number == 13)
		System.out.print("K ");
	    else if (number == 14)
		System.out.print("A ");
	    else
		System.out.print(number+" ");
	}
	else{
	    System.out.print(" ");
	}	    
	System.out.print((char)27 +"[0m");
    }
    public boolean isKey() {
	return isKey;
    }
    public boolean setKey(GameInfo gameInfo) {
	if(this.number == gameInfo.key_Number || this.suit == gameInfo.key_Suit || this.suit == Card.Suit.H_JOKER || this.suit == Card.Suit.L_JOKER ) {
	    isKey = true;
	}
	return isKey;
    }

	
}
