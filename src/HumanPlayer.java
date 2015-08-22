import java.util.Scanner;
import java.util.ArrayList;
import java.lang.NumberFormatException;
/** 
 * HumanPlayer inherits from Player;
 * Card operations require input from command line; 
 * Method:
 * getOneCard()
 * playCards()
 */

public class HumanPlayer extends Player {

    public HumanPlayer(GameInfo gameInfo, int playerID, Shuffle shuffleGenerator, History history) {
	super(gameInfo, playerID, shuffleGenerator, history);
    }


    /** 
     * Operation getOneCard() read commands from user input;
     * And make corresponding operations based on user command.
     */
    @Override
    public void getOneCard() {

	Scanner scan= new Scanner(System.in);
	scan.useDelimiter("\\n");
	System.out.print("Player ID = " + playerID + " : ");
	String command = scan.nextLine();

	while (!command.equals("")) {
	    
	    if (command.equals("s") && keyNumber_Counts[0] > 0 ) {
		gameInfo.updateKeySuit( Card.Suit.SPADE, 1, playerID);
	    }
	    else if (command.equals("ss") && keyNumber_Counts[0] == 2 ) {
		gameInfo.updateKeySuit( Card.Suit.SPADE, 2, playerID);
	    }
	    else if (command.equals("h") && keyNumber_Counts[1] > 0  ) {
		gameInfo.updateKeySuit( Card.Suit.HEART, 1, playerID);
	    }
	    else if (command.equals("hh") && keyNumber_Counts[1] == 2  ) {
		gameInfo.updateKeySuit( Card.Suit.HEART, 2, playerID);
	    }
	    else if (command.equals("d") && keyNumber_Counts[2] > 0  ) {
		gameInfo.updateKeySuit( Card.Suit.DIAMOND, 1, playerID);
	    }
	    else if (command.equals("dd") && keyNumber_Counts[2] == 2  ) {
		gameInfo.updateKeySuit( Card.Suit.DIAMOND, 2, playerID);
	    }
	    else if (command.equals("c") && keyNumber_Counts[3] > 0  ) {
		gameInfo.updateKeySuit( Card.Suit.CLUB, 1, playerID);
	    }
	    else if (command.equals("cc") && keyNumber_Counts[3] == 2  ) {
		gameInfo.updateKeySuit( Card.Suit.CLUB, 2, playerID);
	    }
	    else if (command.equals("q") && L_Joker_Counts ==2  ) {
		gameInfo.updateKeySuit( Card.Suit.L_JOKER, 3, playerID);
	    }
	    else if (command.equals("k") && H_Joker_Counts == 2  ) {
		gameInfo.updateKeySuit( Card.Suit.H_JOKER, 4, playerID);
	    }
	    else {
		System.out.println("Invalid Input. Type In Correct Command");
	    }
	    command = scan.nextLine();
	}
	
	    
	cardpack[current_Card_Number] =
	    shuffleGenerator.allCards[current_Card_Number*NPlayer + playerID];
	System.out.print("\t\t");
	cardpack[current_Card_Number].printOutCard();
	System.out.println();
	
	/* Record the key Card */
	if (cardpack[current_Card_Number].getNumber() == gameInfo.get_Key_Number()) {
	    keyNumber_Counts[cardpack[current_Card_Number].getSuit().getValue()] ++;
	}
	else if (cardpack[current_Card_Number].getSuit() == Card.Suit.L_JOKER) {
	    L_Joker_Counts ++;
	}
	else if (cardpack[current_Card_Number].getSuit() == Card.Suit.H_JOKER) {
	    H_Joker_Counts ++;
	}
	current_Card_Number ++;
    }


    /**
     * Read from user's input;
     * Check the validity of the cards;
     * Remve and return true if cards are valid, return false otherwise.
     */
    @Override
    public boolean playCards(boolean starter) {
	
	ArrayList<Card.Suit> play_suit_List = new ArrayList<Card.Suit>();
	ArrayList<Integer> play_number_List = new ArrayList<Integer>();
	
	Scanner scan= new Scanner(System.in);
	scan.useDelimiter("\\n");
	System.out.println("Player ID = " + playerID + " : ");
	
	String command = scan.nextLine();
	char char_suit;
	int number;
	if (command.equals("")) {return false;}
	
	while (!command.equals("")) {
	    char_suit = command.charAt(0);
	    try {
		number = Integer.parseInt(command.substring(1));
	    }
	    catch(NumberFormatException e) {
		return false;
	    }
	    switch(char_suit) {
	    case 's':
		if(number<=14 && number>=2) {
		    play_suit_List.add(Card.Suit.SPADE);
		    play_number_List.add(number);
		}
		else return false;
		break;
	     case 'h':
		if(number<=14 && number>=2) {
		    play_suit_List.add(Card.Suit.HEART);
		    play_number_List.add(number);
		}
		else return false;
		break;
	    case 'd':
		if(number<=14 && number>=2) {
		    play_suit_List.add(Card.Suit.DIAMOND);
		    play_number_List.add(number);
		}
		else return false;
		break;
	    case 'c':
		if(number<=14 && number>=2) {
		    play_suit_List.add(Card.Suit.CLUB);
		    play_number_List.add(number);
		}
		else return false;
		break;
	    case 'q':
		if(number == 0) {
		    play_suit_List.add(Card.Suit.L_JOKER);
		    play_number_List.add(number);
		}
		else return false;
		break;
	    case 'k':
		if(number == 0) {
		    play_suit_List.add(Card.Suit.H_JOKER);
		    play_number_List.add(number);
		}
		else return false;
		break;
	    default: return false;
	    }
	    command = scan.nextLine();
	}

	manager.deactivate_All();
	
	if(starter) {

	    if (GameRules.test(this, play_suit_List, play_number_List, gameInfo)) {
		manager.deactivate_All();
		for (int i=0; i<play_suit_List.size(); i++) {
		    if(play_number_List.get(i) == 5) { gameInfo.addTo_Current_Scores(5); }
		    if(play_number_List.get(i) == 10 || play_number_List.get(i) == 13) { gameInfo.addTo_Current_Scores(10); }
		    remove(play_suit_List.get(i),play_number_List.get(i));
		    System.out.print(play_suit_List.get(i));
		    System.out.print(play_number_List.get(i) + " ");
		    
		}
		System.out.println();
	       	manager.deactivate_All();
		
		gameInfo.update_Current_Counts(play_suit_List.size());
		if(play_suit_List.get(0) == gameInfo.get_Key_Suit() || play_number_List.get(0) == gameInfo.get_Key_Number()
		   || play_suit_List.get(0) == Card.Suit.L_JOKER || play_suit_List.get(0) == Card.Suit.H_JOKER) {
		    gameInfo.update_Current_Suit(gameInfo.get_Key_Suit());
		}
		else {
		    gameInfo.update_Current_Suit(play_suit_List.get(0));
		}

		System.out.println(history.win_Prob(gameInfo.get_Current_Suit(), play_Structure.get_Structure_Node_Type(0),
						    play_Structure.get_Structure_Node_Start(0), my_Card_List(gameInfo.get_Current_Suit())));
		return true;
	    }
	    else { return false; }
	}
	else {
	    int test_Value = GameRules.test(gameInfo.get_Current_Suit(), gameInfo.get_Current_Counts(), this, play_suit_List, play_number_List, gameInfo);
	    manager.deactivate_All();
	    if (test_Value == 0) {
		ArrayList<Card> play_List = new ArrayList<Card>();
	        
		for (int i=0; i<play_suit_List.size(); i++) {
		    play_List.add(contains(play_suit_List.get(i),play_number_List.get(i)));
		}
		if (GameRules.check_Optimal_Rule(gameInfo.get_Current_Structure(), play_List, this, gameInfo)) {
		    manager.deactivate_All();
		    for (int i=0; i<play_suit_List.size(); i++) {
			if(play_number_List.get(i) == 5) { gameInfo.addTo_Current_Scores(5); }
			if(play_number_List.get(i) == 10 || play_number_List.get(i) == 13) { gameInfo.addTo_Current_Scores(10); }
			remove(play_suit_List.get(i),play_number_List.get(i));
			System.out.print(play_suit_List.get(i));
			System.out.print(play_number_List.get(i) + " ");
		    }
		    System.out.println();
		    return true;
		}
		else {
		    return false;
		}
	    }
	    else if (test_Value > 0) {
		for (int i=0; i<play_suit_List.size(); i++) {
		    if(play_number_List.get(i) == 5) { gameInfo.addTo_Current_Scores(5); }
		    if(play_number_List.get(i) == 10 || play_number_List.get(i) == 13) { gameInfo.addTo_Current_Scores(10); }
		    remove(play_suit_List.get(i),play_number_List.get(i));
		}
		return true;
	    }
	    else { return false; }
	}
    }

    /**
     * Read from user's input;
     * Check the player indeed has those cards;
     * Remve and return true if cards are valid, return false otherwise.
     */
    @Override
    public boolean playerSetTableCard() {
	ArrayList<Card.Suit> table_suit_List = new ArrayList<Card.Suit>();
	ArrayList<Integer> table_number_List = new ArrayList<Integer>();
	
	Scanner scan= new Scanner(System.in);
	scan.useDelimiter("\\n");
	System.out.println("Player ID = " + playerID + " : Put 8 cards on table");
	Card card;
	String command = scan.nextLine();
	char char_suit;
	int number;
	
	manager.deactivate_All();
	
	while (!command.equals("")) {
	    char_suit = command.charAt(0);
	    try {
		number = Integer.parseInt(command.substring(1));
	    }
	    catch(NumberFormatException e) {
		return false;
	    }
	    switch(char_suit) {
	    case 's':
		if(number<=14 && number>=2) {
		    card = contains(Card.Suit.SPADE, number);
		    if (card != null) {
			card.activate();
			table_suit_List.add(Card.Suit.SPADE);
			table_number_List.add(number);
		    }
		    else return false;
		}
		else return false;
		break;
	     case 'h':
        	if(number<=14 && number>=2) {
		    card = contains(Card.Suit.HEART, number);
		    if (card != null) {
			card.activate();
	        	table_suit_List.add(Card.Suit.HEART);
			table_number_List.add(number);
		    }
		    else return false;
		}
		else return false;
		break;
	    case 'd':
        	if(number<=14 && number>=2) {
		    card = contains(Card.Suit.DIAMOND, number);
		    if (card != null) {
			card.activate();
	        	table_suit_List.add(Card.Suit.DIAMOND);
			table_number_List.add(number);
		    }
		    else return false;
		}
		else return false;
		break;
	    case 'c':
        	if(number<=14 && number>=2) {
		    card = contains(Card.Suit.CLUB, number);
		    if (card != null) {
			card.activate();
			table_suit_List.add(Card.Suit.CLUB);
			table_number_List.add(number);
		    }
		    else return false;
		}
		else return false;
		break;
	    case 'q':
        	if(number<=14 && number>=2) {
		    card = contains(Card.Suit.L_JOKER, number);
		    if (card != null) {
			card.activate();
			table_suit_List.add(Card.Suit.L_JOKER);
			table_number_List.add(number);
		    }
		    else return false;
		}
		else return false;
		break;
	    case 'k':
        	if(number<=14 && number>=2) {
		    card = contains(Card.Suit.H_JOKER, number);
		    if (card != null) {
			card.activate();
			table_suit_List.add(Card.Suit.H_JOKER);
			table_number_List.add(number);
		    }
		    else return false;
		}
		else return false;
		break;
	    default: return false;
	    }
	    command = scan.nextLine();
	}
	manager.deactivate_All();
	assert table_suit_List.size() == table_number_List.size();
	if (table_number_List.size() != gameInfo.NPackage*4)
	    return false;
	else {
	    for(int i=0; i<gameInfo.NPackage*4; i++) {
		manager.remove(table_suit_List.get(i), table_number_List.get(i));
		if(table_number_List.get(i) == 5) { gameInfo.update_Table_Scores(5); }
		else if(table_number_List.get(i) == 10 || table_number_List.get(i) == 13) { gameInfo.update_Table_Scores(10); }
	    }
	}
	manager.deactivate_All();
	return true;
    }
	
}
