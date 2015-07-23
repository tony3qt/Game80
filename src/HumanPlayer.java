import java.util.Scanner;
import java.util.ArrayList;
/** 
 * HumanPlayer inherits from Player;
 * Card operations require input from command line; 
 * Method:
 * getOneCard()
 * playCards()
 */

public class HumanPlayer extends Player {

    public HumanPlayer(GameInfo gameInfo, int playerID, Shuffle shuffleGenerator) {
	super(gameInfo, playerID, shuffleGenerator);
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
	    number = Integer.parseInt(command.substring(1));
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
	
	if(starter) {
	    if (GameRules.test(this, play_suit_List, play_number_List, gameInfo)) {
		for (int i=0; i<play_suit_List.size(); i++) {
		    remove(play_suit_List.get(i),play_number_List.get(i));
		}
	       	  
		gameInfo.update_Current_Counts(play_suit_List.size());
		if(play_suit_List.get(0) == gameInfo.get_Key_Suit() || play_number_List.get(0) == gameInfo.get_Key_Number()
		   || play_suit_List.get(0) == Card.Suit.L_JOKER || play_suit_List.get(0) == Card.Suit.H_JOKER) {
		    gameInfo.update_Current_Suit(gameInfo.get_Key_Suit());
		}
		else {
		    gameInfo.update_Current_Suit(play_suit_List.get(0));
		}
		return true;
	    }
	    else { return false; }
	}
	else {
	    if (GameRules.test(gameInfo.get_Current_Suit(), gameInfo.get_Current_Counts(), this, play_suit_List, play_number_List, gameInfo)) {
		ArrayList<Card> play_List = new ArrayList<Card>();
		for (int i=0; i<play_suit_List.size(); i++) {
		    play_List.add(contains(play_suit_List.get(i),play_number_List.get(i)));
		}
		if (GameRules.check_Optimal_Rule(gameInfo.get_Current_Structure(), play_List, this, gameInfo)) {
		    for (int i=0; i<play_suit_List.size(); i++) {
			remove(play_suit_List.get(i),play_number_List.get(i));
		    }
		    return true;
		}
		else {
		    return false;
		}
	    }
	    else { return false; }
	}
    }
    
	
}