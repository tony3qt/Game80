import java.util.ArrayList;

/**
 * Save all cards played out in the past rounds.
 */
public class History {

    private ArrayList<CardStructure>[] card_Structure_History;
    private ArrayList<Integer> starter_ID;
    private ArrayList<Double> confidence;
    private GameInfo gameInfo;

    /* Label the cards that have been played out */
    private int[] spade_Stat;
    private int[] heart_Stat;
    private int[] diamond_Stat;
    private int[] club_Stat;
    private int[] key_Stat;

    /* Probability that one player still have cards of certain suit */
    private double[] spade_Prob;
    private double[] heart_Prob;
    private double[] diamond_Prob;
    private double[] club_Prob;
    private double[] key_Prob;

    /* Count how many cards have been played out in each suit */
    private int spade_Counts;
    private int heart_Counts;
    private int diamond_Counts;
    private int club_Counts;
    private int key_Counts;
    
    public History() {
	@SuppressWarnings("unchecked")
	ArrayList<CardStructure>[] card_Structure_History = (ArrayList<CardStructure>[]) new ArrayList[4];
	for(int i=0; i<4; i++) {
	    card_Structure_History[i] = new ArrayList<CardStructure>();
	}
	this.card_Structure_History = card_Structure_History;

	spade_Prob = new double[]{1.0, 1.0, 1.0, 1.0};
	heart_Prob = new double[]{1.0, 1.0, 1.0, 1.0};
	diamond_Prob = new double[]{1.0, 1.0, 1.0, 1.0};
	club_Prob = new double[]{1.0, 1.0, 1.0, 1.0};
	key_Prob = new double[]{1.0, 1.0, 1.0, 1.0};
	
    }

    public void update_History(int ID, CardStructure cs, boolean starter, double cfd_Value) {
	card_Structure_History[ID].add(cs);
	if (starter) {
	    starter_ID.add(ID);
	    this.confidence.add(cfd_Value);
	}

	else {
	    if (cs.get_Uniform_Suit() != gameInfo.get_Current_Suit()) {
		if (gameInfo.get_Current_Suit() == gameInfo.get_Key_Suit()) {
		    key_Prob[ID] = 0.0;
		}
		else {
		    change_Prob(gameInfo.get_Current_Suit(), ID, 0.0);
		}
	    }
	}
	
	key_Stat = new int[18];
	spade_Stat = new int[12];
	heart_Stat = new int[12];
	diamond_Stat = new int[12];
	club_Stat = new int[12];
	
	for(Card card : cs.get_Cards()) {
	    if (card.isKey()) {

		key_Counts ++;
		
		if (card.getNumber() < gameInfo.get_Key_Number() && card.getSuit() != Card.Suit.H_JOKER && card.getSuit() != Card.Suit.L_JOKER)
		    key_Stat[card.getNumber()-2] ++;
		else if (card.getNumber() > gameInfo.get_Key_Number() && card.getSuit() != Card.Suit.H_JOKER && card.getSuit() != Card.Suit.L_JOKER) 
		    key_Stat[card.getNumber()-3] ++;
		else if (card.getNumber() == gameInfo.get_Key_Number() && card.getSuit() != gameInfo.get_Key_Suit()) 
		    key_Stat[(card.getSuit().getValue() - gameInfo.get_Key_Suit().getValue()+4)%4 + gameInfo.CARD_IN_EACH_SUIT-2] ++ ;
		
		else if (card.getNumber() == gameInfo.get_Key_Number() && card.getSuit() == gameInfo.get_Key_Suit())
		    key_Stat[gameInfo.CARD_IN_EACH_SUIT+2] ++ ;
		else if (card.getSuit() == Card.Suit.L_JOKER)
		    key_Stat[gameInfo.CARD_IN_EACH_SUIT+3] ++ ;
		else if (card.getSuit() == Card.Suit.H_JOKER)
		    key_Stat[gameInfo.CARD_IN_EACH_SUIT+4] ++ ;
		
	    }
	    else {
		if (card.getNumber() < gameInfo.get_Key_Number())
		    add_To_Stat(card.suit(),card.getNumber()-2);
		else
		    add_To_Stat(card.suit(),card.getNumber()-3);
		    
	    }
	}
    }

    private void add_To_Stat(Card.Suit suit, int position) {
	switch(suit) {
	case SPADE:
	    spade_Stat[position] ++;
	    spade_Counts ++;
	    break;
	case HEART:
	    heart_Stat[position] ++;
	    heart_Counts ++;
	    break;
	case DIAMOND:
	    diamond_Stat[position] ++;
	    diamond_Counts ++;
	    break;
	case CLUB:
	    club_Stat[position] ++;
	    club_Counts ++;
	    break;
	}	
    }

    private void change_Prob(Card.Suit suit, int ID, double prob) {
	switch(suit) {
	case SPADE:
	    spade_Prob[ID] = prob;
	    break;
	case HEART:
	    spade_Prob[ID] = prob;
	    break;
	case DIAMOND:
	    diamond_Prob[ID] = prob;
	    break;
	case CLUB:
	    club_Prob[ID] = prob;
	    break;
	}
    }
}
