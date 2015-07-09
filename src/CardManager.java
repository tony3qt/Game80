public class CardManager {

    /* Cound four suits and key cards */
    int[] spade_Cards;
    int[] heart_Cards;
    int[] diamond_Cards;
    int[] club_Cards;
    int[] key_Cards;
    GameInfo gameInfo;
    
    /** Constructor;
     *  Input: Array containing all the card of a player;
     */
    public CardManager(Card[] cardpack,GameInfo gameInfo) {
	spade_Cards = new int[13];
	heart_Cards = new int[13];
	diamond_Cards = new int[13];
	club_Cards = new int[13];
	key_Cards = new int[6];
	this.gameInfo = gameInfo;
	
	for(Card card:cardpack) {
	    if(card.getNumber()==gameInfo.key_Number) {
		switch(card.getSuit()) {
		case SPADE:
		    key_Cards[0]++;
		    break;
		case HEART:
		    key_Cards[1]++;
		    break;
		case DIAMOND:
		    key_Cards[2]++;
		    break;
		case CLUB:
		    key_Cards[3]++;
		    break;
		}
	    }
	    else {
		switch(card.getSuit()) {
		case SPADE:
		spade_Cards[card.getNumber()-2]++;
		break;
		case HEART:
		    heart_Cards[card.getNumber()-2]++;
		    break;
		case DIAMOND:
		    diamond_Cards[card.getNumber()-2]++;
		    break;
		case CLUB:
		    club_Cards[card.getNumber()-2]++;
		    break;
		case QUEEN:
		    key_Cards[4]++;
		    break;
		case KING:
		    key_Cards[5]++;
		}
	    }
	}
    }
    public void printOutCard_in_Order() {
	for(int i=0;i<spade_Cards.length;i++) {
	    for(int j=0;j<spade_Cards[i];j++) {
		new Card(Card.Suit.SPADE,i+2).printOutCard();
	    }
	}
	for(int i=0;i<heart_Cards.length;i++) {
	    for(int j=0;j<heart_Cards[i];j++) {
		new Card(Card.Suit.HEART,i+2).printOutCard();
	    }
	}
	for(int i=0;i<diamond_Cards.length;i++) {
	    for(int j=0;j<diamond_Cards[i];j++) {
		new Card(Card.Suit.DIAMOND,i+2).printOutCard();
	    }
	}
	for(int i=0;i<club_Cards.length;i++) {
	    for(int j=0;j<club_Cards[i];j++) {
		new Card(Card.Suit.CLUB,i+2).printOutCard();
	    }
	}
	for(int i=0;i<6;i++) {
	    for(int j=0;j<key_Cards[i];j++) {
		new Card(Card.Suit.values()[i],gameInfo.key_Number).printOutCard();
	    }
	}
	System.out.println();
    }
	
}
