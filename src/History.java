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

    private double[] spade_Double_Prob;
    private double[] heart_Double_Prob;
    private double[] diamond_Double_Prob;
    private double[] club_Double_Prob;
    private double[] key_Double_Prob;

    /* Count how many cards have been played out in each suit */
    private int spade_Counts;
    private int heart_Counts;
    private int diamond_Counts;
    private int club_Counts;
    private int key_Counts;
    
    public History(GameInfo gameInfo) {
	this.gameInfo = gameInfo;
	@SuppressWarnings("unchecked")
	ArrayList<CardStructure>[] card_Structure_History = (ArrayList<CardStructure>[]) new ArrayList[4];
	for(int i=0; i<4; i++) {
	    card_Structure_History[i] = new ArrayList<CardStructure>();
	}
	this.card_Structure_History = card_Structure_History;
	starter_ID = new ArrayList<Integer>();
	
	key_Stat = new int[18];
	spade_Stat = new int[12];
	heart_Stat = new int[12];
	diamond_Stat = new int[12];
	club_Stat = new int[12];
	
	spade_Prob = new double[]{1.0, 1.0, 1.0, 1.0};
	heart_Prob = new double[]{1.0, 1.0, 1.0, 1.0};
	diamond_Prob = new double[]{1.0, 1.0, 1.0, 1.0};
	club_Prob = new double[]{1.0, 1.0, 1.0, 1.0};
	key_Prob = new double[]{1.0, 1.0, 1.0, 1.0};

	spade_Double_Prob = new double[]{1.0, 1.0, 1.0, 1.0};
	heart_Double_Prob = new double[]{1.0, 1.0, 1.0, 1.0};
	diamond_Double_Prob = new double[]{1.0, 1.0, 1.0, 1.0};
	club_Double_Prob = new double[]{1.0, 1.0, 1.0, 1.0};
	key_Double_Prob = new double[]{1.0, 1.0, 1.0, 1.0};
	
    }

    public void update_History(int ID, CardStructure cs, int order, int max_ID) {
	card_Structure_History[ID].add(cs);
	int scores = 0;
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
	    if (card.number() == 5) scores += 5;
	    if (card.number() == 10) scores += 10;
	    if (card.number() == 13) scores += 13;
	}
	
	if (order==0) {
	    starter_ID.add(ID);
	}

	else {
	    if (cs.get_Uniform_Suit() != gameInfo.get_Current_Suit()) {
		change_Prob(gameInfo.get_Current_Suit(), ID, 0.0);
		change_Double_Prob(gameInfo.get_Current_Suit(), ID, 0.0);	
	    }
	    else {
		if ((ID-max_ID)%2 != 0 && scores >= 13) { change_Double_Prob(gameInfo.get_Current_Suit(), ID, 0.1); }
		else if ((ID-max_ID)%2 != 0 && scores >= 10) { change_Double_Prob(gameInfo.get_Current_Suit(), ID, 0.3); }
		else if ((ID-max_ID)%2 != 0 && scores >= 5) { change_Double_Prob(gameInfo.get_Current_Suit(), ID, 0.5); }
	    
		if (gameInfo.get_Current_Structure().get_Structure_Node_Type(0) >= 2 && cs.get_Structure_Node_Type(0) == 1) {
		    change_Double_Prob(gameInfo.get_Current_Suit(), ID, 0.0);
		}
		else if (gameInfo.get_Current_Structure().size() >= 2 && gameInfo.get_Current_Structure().get_Structure_Node_Type(1) >= 2
			 && cs.size() >=2 && cs.get_Structure_Node_Type(1) == 1) {
		    change_Double_Prob(gameInfo.get_Current_Suit(), ID, 0.0);
		}
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
	if (suit == gameInfo.get_Key_Suit())
	    key_Prob[ID] = prob;
	else {
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
    
    private void change_Double_Prob(Card.Suit suit, int ID, double prob) {
	if (suit == gameInfo.get_Key_Suit())
	    key_Double_Prob[ID] = prob;
	else {
	    switch(suit) {
	    case SPADE:
		spade_Double_Prob[ID] = prob;
		break;
	    case HEART:
		spade_Double_Prob[ID] = prob;
		break;
	    case DIAMOND:
	    diamond_Double_Prob[ID] = prob;
	    break;
	    case CLUB:
		club_Double_Prob[ID] = prob;
		break;
	    }
	}
    }

    private int get_Counts(Card.Suit suit) {
	if (suit == gameInfo.get_Key_Suit())
	    return key_Counts;
	else {
	    switch(suit) {
	    case SPADE:
		return spade_Counts;
	    case HEART:
		return heart_Counts;
	    case DIAMOND:
		return diamond_Counts;
	    case CLUB:
		return club_Counts;
	    default:
		System.out.println("Error in get_Counts(): History");
		return 0;
	    }
	}
    }

    private double get_Prob_Product(Card.Suit suit) {
	if (suit == gameInfo.get_Key_Suit())
	    return key_Prob[0] * key_Prob[1] * key_Prob[2] * key_Prob[3];
	else {
	    switch(suit) {
	    case SPADE:
		return spade_Prob[0] * spade_Prob[1] * spade_Prob[2] * spade_Prob[3];
	    case HEART:
		return heart_Prob[0] * heart_Prob[1] * heart_Prob[2] * heart_Prob[3];
	    case DIAMOND:
		return diamond_Prob[0] * diamond_Prob[1] * diamond_Prob[2] * diamond_Prob[3];
	    case CLUB:
		return club_Prob[0] * club_Prob[1] * club_Prob[2] * club_Prob[3];
	    default:
		System.out.println("Error in get_Prob_Product(): History");
		return 0;
	    }	    
	}
    }
    
    /**
     * Calculate the probability that (suit, type, start) could win this round, base on history and cards in hand.
     */
    public double win_Prob(Card.Suit suit, int type, int start, ArrayList<Card> myCards) {
	int[] temp_Stat;
	if (suit == gameInfo.get_Key_Suit()) {
	    temp_Stat = new int[18];
	    for(Card card : myCards) {
		assert card.isKey();
		if(card.suit() == Card.Suit.H_JOKER)
		    temp_Stat[17] -= 1;
		else if(card.suit() == Card.Suit.L_JOKER)
		    temp_Stat[16] -= 1;
		else if(card.suit() == gameInfo.get_Key_Suit() && card.number() == gameInfo.get_Key_Number())
		    temp_Stat[15] -= 1;
		else if(card.number() == gameInfo.get_Key_Number())
		    temp_Stat[(card.suit().getValue() - gameInfo.get_Key_Suit().getValue() + 4)%4 + 11] -= 1;
		else if(card.number() < gameInfo.get_Key_Number())
		    temp_Stat[card.number() - 2] -= 1;
		else
		    temp_Stat[card.number() - 3] -= 1;
	    }
	}
	else {
	    temp_Stat = new int[12];
	    for(Card card : myCards) {
		assert card.suit() == suit;
		if(card.number() < gameInfo.get_Key_Number()) 
		    temp_Stat[card.number() - 2] -= 1;
		else
		    temp_Stat[card.number() - 3] -= 1;
	    }
	}
	for(int i=0; i<temp_Stat.length; i++) {
	    temp_Stat[i] = 2 + temp_Stat[i] - get_Stat(suit, i);
	}

	int counts = 0;
	    
	CardStructure virtual_CS = new CardStructure(gameInfo, suit, temp_Stat);
	//virtual_CS.printTable();
	for (int i=0; i<virtual_CS.size(); i++) {

	    if (type >= 2) {
		if (virtual_CS.get_Structure_Node_Type(i) >= type) {
		    for (int j=0; j<virtual_CS.get_Structure_Node_Type(i)/2 - (type/2-1); ) {
			if(virtual_CS.get_Structure_Node_Start(i) + j > start) {
			    counts ++;
			    j += type/2;
			}
			else
			    j ++;
		    }
	    
		}
		else
		    break;
	    }
	    else {
		for(int j=start+1; j<temp_Stat.length; j++) {
		    counts += temp_Stat[j];
		}
	    }
	}

	double prob = 0.0;
	if (type == 1 && counts >0) prob = 0.0;
	else if (type == 1 && counts == 0) prob = 1.0;
	else if (type == 2) prob = 1.0*(6-counts)/6;
	else prob = 1.0;
	return prob;
    }

    private int get_Stat(Card.Suit suit, int index) {
	if (suit == gameInfo.get_Key_Suit())
	    return key_Stat[index];
	else {
	    switch(suit) {
	    case SPADE:
		return spade_Stat[index];
	    case HEART:
		return heart_Stat[index];
	    case DIAMOND:
		return diamond_Stat[index];
	    case CLUB:
		return club_Stat[index];
	    default:
		System.out.println("Error in get_Stat() : History");
		return 0;
	    }
	}
    }
}
