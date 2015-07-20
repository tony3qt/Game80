public class ComputerPlayer extends Player{

    public ComputerPlayer( GameInfo gameInfo, int playerID, Shuffle shuffleGenerator ) {
	super( gameInfo, playerID, shuffleGenerator );
    }
	@Override
    public void getOneCard() {
	cardpack[current_Card_Number] =
	    shuffleGenerator.allCards[current_Card_Number*NPlayer + playerID];
	if (gameInfo.DEBUG) {
	    System.out.print("\t\t");
	    cardpack[current_Card_Number].printOutCard();
	}
	
	switch (cardpack[current_Card_Number].getSuit()) {
	case SPADE: suit_Counts[0] ++;
	    if (cardpack[current_Card_Number].getNumber() == keyNumber) {
		gameInfo.updateKeySuit( Card.Suit.SPADE, 1, playerID );
		keyNumber_Counts[0] ++;
	    }
	    if (keyNumber_Counts[0]==2) {
		gameInfo.updateKeySuit( Card.Suit.SPADE, 2, playerID );
	    }
	    
	    break;
	case HEART: suit_Counts[1] ++;
	    if (cardpack[current_Card_Number].getNumber() == keyNumber) {
		gameInfo.updateKeySuit( Card.Suit.HEART, 1, playerID );
		keyNumber_Counts[1] ++;
	    }
	    if (keyNumber_Counts[1]==2) {
		gameInfo.updateKeySuit( Card.Suit.HEART, 2, playerID);
	    }
	    break;
	case DIAMOND: suit_Counts[2] ++;
	    if (cardpack[current_Card_Number].getNumber() == keyNumber) {
		gameInfo.updateKeySuit( Card.Suit.DIAMOND, 1, playerID );
		keyNumber_Counts[2] ++;
	    }
	    if (keyNumber_Counts[2]==2) {
		gameInfo.updateKeySuit( Card.Suit.DIAMOND, 2, playerID);
	    }
	    break;
	case CLUB: suit_Counts[3] ++;
	    if (cardpack[current_Card_Number].getNumber() == keyNumber) {
		gameInfo.updateKeySuit(Card.Suit.CLUB, 1, playerID);
		keyNumber_Counts[3] ++;
	    }
	    if (keyNumber_Counts[3]==2) {
		gameInfo.updateKeySuit(Card.Suit.CLUB, 2, playerID);
	    }
	    break;
	case L_JOKER: L_Joker_Counts ++;
	    if (L_Joker_Counts==2) {
		gameInfo.updateKeySuit(Card.Suit.L_JOKER, 3, playerID);
	    }
	    break;
	case H_JOKER: H_Joker_Counts ++;
	    if (H_Joker_Counts==2) {
		gameInfo.updateKeySuit(Card.Suit.H_JOKER, 4, playerID);
	    }
	}
	current_Card_Number++;
    }
   
}
