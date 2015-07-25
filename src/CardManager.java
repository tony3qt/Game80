import java.util.ArrayList;
import java.util.Collections;


/** 
 * Each Player has a CardManager to do tasks, such as sorting cards, removing cards; 
 * CardManager has five ArrayList to contian the player's card in hand;
 * Cards are sorted in CardManager constructor.
 */
public class CardManager {

    /* Counts four suits and key cards, not used now */
    int[] spade_Cards;
    int[] heart_Cards;
    int[] diamond_Cards;
    int[] club_Cards;
    int[] key_Cards;

    /* Classify all cards into five groups, it's very likely that one of them is empty */
    ArrayList<Card> spade_List;
    ArrayList<Card> heart_List;
    ArrayList<Card> diamond_List;
    ArrayList<Card> club_List;
    ArrayList<Card> key_List;

    GameInfo gameInfo;
    
    /** 
     * Constructor;
     * Input: Array containing all the card of a player;
     * If the player owns the table cards, then table_Cards is not empty, otherwise table_Cards is empty.
     */
    public CardManager(Card[] cardpack,Card[] table_Cards, GameInfo gameInfo) {
	spade_Cards = new int[13];
	heart_Cards = new int[13];
	diamond_Cards = new int[13];
	club_Cards = new int[13];
	key_Cards = new int[6];

	this.gameInfo = gameInfo;

	spade_List = new ArrayList<Card>();
	heart_List = new ArrayList<Card>();
	diamond_List = new ArrayList<Card>();
	club_List = new ArrayList<Card>();
	key_List = new ArrayList<Card>();
	
	for (Card card : cardpack) {
	    if(card.setKey(gameInfo)) {
		key_List.add(card);
	    }
	    else {
		switch(card.getSuit()) {
		case SPADE:
		    spade_List.add(card);
		    break;
		case HEART:
		    heart_List.add(card);
		    break;
		case DIAMOND:
		    diamond_List.add(card);
		    break;
		case CLUB:
		    club_List.add(card);
		    break;
		}
	    }	
	}

	if (table_Cards != null) {
	    for (Card card : table_Cards) {
		if(card.setKey(gameInfo)) {
		    key_List.add(card);
		}
	    
		else {
		    switch(card.getSuit()) {
		    case SPADE:
			spade_List.add(card);
			break;
		    case HEART:
			heart_List.add(card);
			break;
		    case DIAMOND:
			diamond_List.add(card);
			break;
		    case CLUB:
			club_List.add(card);
			break;
		    }
		}	
	    }
	}

	Collections.sort(spade_List, new SingleComparator(gameInfo));
	Collections.sort(heart_List, new SingleComparator(gameInfo));
	Collections.sort(diamond_List, new SingleComparator(gameInfo));
	Collections.sort(club_List, new SingleComparator(gameInfo));
	Collections.sort(key_List, new SingleComparator(gameInfo));
    }

    /** 
     * Print Out Cards in Order.
     */
    public void printOutCard_in_Order() {

	for (int i=0; i<spade_List.size(); i++) {
	    spade_List.get(i).printOutCard();
	}
	for (int i=0; i<heart_List.size(); i++) {
	    heart_List.get(i).printOutCard();
	}
	for (int i=0; i<diamond_List.size(); i++) {
	    diamond_List.get(i).printOutCard();
	}
	for (int i=0; i<club_List.size(); i++) {
	    club_List.get(i).printOutCard();
	}
	System.out.println();
	for (int i=0; i<key_List.size(); i++) {
	    key_List.get(i).printOutCard();
	}
	System.out.println();
    }

    public ArrayList<Card> get_List(Card.Suit suit) {
	switch(suit) {
	case SPADE: return spade_List;
	case HEART: return heart_List;
	case DIAMOND: return diamond_List;
	case CLUB: return club_List;
	default: return null;
	}
    }
    public ArrayList<Card> get_key_List() { return key_List; }
    
    public Card contains(Card.Suit suit, int number) {
	Card card;
	/* Key suit */
	if(suit == gameInfo.key_Suit || number == gameInfo.key_Number || suit == Card.Suit.L_JOKER || suit == Card.Suit.H_JOKER) {
	    for (int i=0; i<key_List.size(); i++) {
		card = key_List.get(i);
		if(card.getSuit() == suit && card.getNumber() == number  && !card.isActive()) {
		    return card;
		}
	    }
	    return null;
	}
	
	/* Suits other than key suits */
	/* Switch process looks ugly as always */
	else {
	    switch(suit) {
	    case SPADE:
		for (int i=0; i<spade_List.size(); i++) {
		card = spade_List.get(i);
		if(card.getSuit() == suit && card.getNumber() == number && !card.isActive()) {
			return card;
		    }
		}
		return null;
	    
	    case HEART:
		for (int i=0; i<heart_List.size(); i++) {
		card = heart_List.get(i);
		if(card.getSuit() == suit && card.getNumber() == number && !card.isActive()) {
			return card;
		    }
		}
		return null;

	    case DIAMOND:
		for (int i=0; i<diamond_List.size(); i++) {
		card = diamond_List.get(i);
		if(card.getSuit() == suit && card.getNumber() == number && !card.isActive()) {
			return card;
		    }
		}
		return null;

	    case CLUB:
		for (int i=0; i<club_List.size(); i++) {
		    card = club_List.get(i);
		    if(card.getSuit() == suit && card.getNumber() == number && !card.isActive()) {
			return card;
		    }
		}
		return null;
	    default:
		return null;
	    }
	}
    }
    
    /** Remove one card if the player has that card in hand;
     *  Search from one of the five ArrayList, depending on its suit;
     */
    public void remove(Card.Suit suit, int number) {

	Card card;
	
	card = contains(suit, number);
	if (card != null) {
	    if (card.isKey()) {
		key_List.remove(card);
	    }
	    else {
		switch(card.getSuit()) {
		case SPADE:
		    spade_List.remove(card);
		    break;
		case HEART:
		    heart_List.remove(card);
		    break;
		case DIAMOND:
		    diamond_List.remove(card);
		    break;
		case CLUB:
		    club_List.remove(card);
		    break;
		}
	    }
	}
	else {
	    System.out.println("remove failed");
	}
    }

    public void deactivate_All() {
	for(Card card : key_List) {
	    card.deactivate();
	}

	for(Card card : spade_List) {
	    card.deactivate();
	}

	for(Card card : heart_List) {
	    card.deactivate();
	}

	for(Card card : club_List) {
	    card.deactivate();
	}

	for(Card card : diamond_List) {
	    card.deactivate();
	}
    }
	
}
