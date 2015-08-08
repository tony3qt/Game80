
import java.util.Comparator;

/**
 * SingleComparator is only used for the purpose to put all the cards in order based on a universal rule.
 * It is not involved in card comparison.
 * The two cards being compared either have the same suit, or both are key suit.
 */
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
	    else if (card1.getSuit() == gameInfo.get_Key_Suit() && card1.getNumber() == gameInfo.get_Key_Number() &&
		     card2.getSuit() == gameInfo.get_Key_Suit() && card2.getNumber() == gameInfo.get_Key_Number())
		return 0;
	    else if (card1.getSuit() == gameInfo.get_Key_Suit() && card1.getNumber() == gameInfo.get_Key_Number())
		return 1;
	    else if (card2.getSuit() == gameInfo.get_Key_Suit() && card2.getNumber() == gameInfo.get_Key_Number())
		return -1;
	    else if (card1.getNumber() == gameInfo.get_Key_Number() && card2.getNumber() == gameInfo.get_Key_Number()) 
		return card1.getSuit().getValue() - card2.getSuit().getValue();
		    
	    else if (card1.getNumber() == gameInfo.get_Key_Number())
		return 1;
	    else if (card2.getNumber() == gameInfo.get_Key_Number())
		return -1;
	    else
		return card1.getNumber() - card2.getNumber();
	}
    }
}
