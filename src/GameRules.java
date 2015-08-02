import java.util.ArrayList;

/**
 * GameRules class has a whole serial of static methods which test whether the cards are valid or not;
 * Those methods are called in Player.playCards();
 * In order to make sure the cards a player choose to play obey the game's rules.
 */
public class GameRules {

    /**
     * Private constructor prevent instantiation;
     * GameRules provide 
     */
    private GameRules() { }

    /**
     * Suit is not specified yet;
     * But all the cards must have uniform suit;
     * The player must have those cards in hand.
     * play_suit_List and play_number_List may be modified;
     */
    public static boolean test(Player player, ArrayList<Card.Suit> play_suit_List, ArrayList<Integer> play_number_List, GameInfo gameInfo) {

	assert play_suit_List.size() == play_number_List.size();
	Card card;
	ArrayList<Card> play_list = new ArrayList<Card>();
	for (int i=0; i<play_suit_List.size(); i++) {
	    card = player.contains(play_suit_List.get(i), play_number_List.get(i));
	    if (card == null ) {
		System.out.println("Doesn't not contain cards");
		return false;
	    }
	    
	    else {
		card.activate();
		play_list.add(card);
	    }
	}
	player.get_Manager().deactivate_All();
	
	CardStructure cs = new CardStructure(gameInfo, play_list);

	for (int t=0; t<cs.size(); t++) {
	    for (int i=1; i<4; i++) {
		int ID = (player.get_ID() + i)%4;
		ArrayList<Card> return_Cards = CardStructure.structure_Node_Analyze(cs, player, t, ID, gameInfo) ;
		if( return_Cards!=null) {
		    cs = new CardStructure(gameInfo, return_Cards);
		    play_suit_List.clear();
		    play_number_List.clear();
		    for (Card c : return_Cards) {
			play_suit_List.add(c.suit());
			play_number_List.add(c.number());
		    }
		    break;
		}
	    }
	}
	
	if (cs.get_Uniform_Suit() != null) {
	    gameInfo.update_Current_Structure(cs);
	    player.update_Play_Structure(cs);
	    return true;
	}
	else {
	    System.out.println("Cards suit not uniform");
	    return false;
	}

    }

    /**
     * Suit is specified, number of cards is also specified;
     * Cards of other suit can only be played out if there's not enough cards in the specified suit;
     * The player must have those cards in hand.
     */
    public static int test(Card.Suit suit, int total_Number, Player player, ArrayList<Card.Suit> play_suit_List, ArrayList<Integer> play_number_List, GameInfo gameInfo) {
	
	assert play_suit_List.size() == play_number_List.size();
	int max_num_other_suit = 0;
	int num_other_suit = 0;
	Card card;
	ArrayList<Card> play_list = new ArrayList<Card>();
	
	if (play_suit_List.size() != total_Number) {
	    System.out.println("Incorrect card number");
	    return -1;
	}
	
	for (int i=0; i<play_suit_List.size(); i++) {
	    card = player.contains(play_suit_List.get(i), play_number_List.get(i));
	    if (card == null ) {
		System.out.println("Doesn't not contain cards");
		return -1;
	    }
	    else {
		card.activate();
		play_list.add(card);
	    }
	}
	
	if ( suit == gameInfo.get_Key_Suit()) {
	    max_num_other_suit = - (player.get_Manager().get_key_List().size() - total_Number);
	    if (max_num_other_suit < 0) max_num_other_suit = 0;
	    for (int i=0; i<play_suit_List.size(); i++) {
		if (play_suit_List.get(i) != Card.Suit.L_JOKER && play_suit_List.get(i) != Card.Suit.H_JOKER
		    && play_suit_List.get(i) != gameInfo.get_Key_Suit() && play_number_List.get(i) != gameInfo.get_Key_Number()) {
		    num_other_suit ++ ;
		}
		if (num_other_suit > max_num_other_suit) {
		    System.out.println("Cards suit illegal");
		    return -1;
		}
	    }
	    CardStructure cs = new CardStructure(gameInfo, play_list);
	    player.update_Play_Structure(cs);
	    return max_num_other_suit;
	}
	else {
	     max_num_other_suit = - (player.get_Manager().get_List(suit).size() - total_Number);
	    if (max_num_other_suit < 0) max_num_other_suit = 0;
	    for (int i=0; i<play_suit_List.size(); i++) {
		if (play_suit_List.get(i) != suit) {
		    num_other_suit ++ ;
		}
		if (num_other_suit > max_num_other_suit) {
		    System.out.println("Cards suit illegal");
		    return -1;
		}
	    }
	    CardStructure cs = new CardStructure(gameInfo, play_list);
	    player.update_Play_Structure(cs);
	    return max_num_other_suit;
	}
    }
    public static boolean check_Optimal_Rule(CardStructure cs, ArrayList<Card> card_List, Player player, GameInfo gameInfo) {
	ArrayList<Boolean> total_in_hand;
	ArrayList<Boolean> to_play;
	assert cs.get_Uniform_Suit() != null;

	to_play = CardStructure.structure_Analyze(cs, card_List, gameInfo);
	    
	if(cs.get_Uniform_Suit()==gameInfo.get_Key_Suit())
	    total_in_hand = CardStructure.structure_Analyze(cs, player.get_Manager().get_key_List(), gameInfo);
	else
	    total_in_hand = CardStructure.structure_Analyze(cs, player.get_Manager().get_List(cs.get_Uniform_Suit()), gameInfo);

	if (to_play.size() != total_in_hand.size()) {
	    System.out.println("Doesn't obey optimal rule");
	    return false;
	}
	    
	for(int i=0; i<to_play.size(); i++) {
	    if (to_play.get(i) != total_in_hand.get(i)) {
		System.out.println("Doesn't obey optimal rule");
		return false;
	    }
	}
	return true;
	
    }
}
