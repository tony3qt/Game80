
import java.util.Comparator;

public class SingleComparator implements Comparator<Card> {

    GameInfo gameInfo;
    public SingleComparator(GameInfo gameInfo) {
	this.gameInfo = gameInfo;
    }
    public int compare(Card card1, Card card2) {
	assert ( (card1.isKey() && card2.isKey()) || (!card1.isKey() && !card2.isKey() && (card1.getSuit()==card2.getSuit())) );
	
	if(!card1.isKey() && !card2.isKey()) {
	    return card1.getNumber() - card2.getNumber();
	}
	else {
	    if (card1.getSuit() == Card.Suit.H_JOKER && card2.getSuit() == Card.Suit.H_JOKER) return 0;
	    else if (card1.getSuit() == Card.Suit.H_JOKER && card2.getSuit() != Card.Suit.H_JOKER) return 1;
	    else if (card1.getSuit() != Card.Suit.H_JOKER && card2.getSuit() == Card.Suit.H_JOKER) return -1;
	    else if (card1.getSuit() == Card.Suit.L_JOKER && card2.getSuit() == Card.Suit.L_JOKER) return 0;
	    else if (card1.getSuit() == Card.Suit.L_JOKER && card2.getSuit() != Card.Suit.L_JOKER) return 1;
	    else if (card1.getSuit() != Card.Suit.L_JOKER && card2.getSuit() == Card.Suit.L_JOKER) return -1;
	    else if (card1.getSuit() == gameInfo.key_Suit && card1.getNumber() == gameInfo.key_Number &&
		     card2.getSuit() == gameInfo.key_Suit && card2.getNumber() == gameInfo.key_Number)
		return 0;
	    else if (card1.getSuit() == gameInfo.key_Suit && card1.getNumber() == gameInfo.key_Number)
		return 1;
	    else if (card2.getSuit() == gameInfo.key_Suit && card2.getNumber() == gameInfo.key_Number)
		return -1;
	    else if (card1.getNumber() == gameInfo.key_Number && card2.getNumber() == gameInfo.key_Number) 
		return card1.getSuit().getValue() - card2.getSuit().getValue();
		    
	    else if (card1.getNumber() == gameInfo.key_Number)
		return 1;
	    else if (card2.getNumber() == gameInfo.key_Number)
		return -1;
	    else
		return card1.getNumber() - card2.getNumber();
	}
    }
}
