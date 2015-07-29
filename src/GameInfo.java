
/**
 * GameInfo stores all the information of the poker game.
 */

public class GameInfo {
    public static final int CARD_IN_EACH_PACKAGE = 54;
    public static final int CARD_IN_EACH_SUIT = 13;
    public static final int SUIT_NUMBER = 4;
    public boolean DEBUG;
    public int NPlayer;
    public int NPackage;
    
    private int key_Number;
    
    private boolean suit_Decleared;
    private boolean suit_Double_Decleared;
    private boolean suit_Queen_Decleared;
    private boolean suit_King_Decleared;
    
    private Card.Suit key_Suit;
    private int key_from_playerID;

    private int ironThrone;
    private int ironThrone_temp;

    private Card.Suit current_Suit;
    private int current_Counts;
    private CardStructure current_Structure;

    private int total_Scores;
    private int current_Scores;
    
    public GameInfo(int NPlayer, int NPackage, int key_Number,boolean debug) {
	this.NPlayer = NPlayer;
	this.NPackage = NPackage;
	this.key_Number = key_Number;
	this.key_Suit = null;
	this.current_Suit = null;
	this.DEBUG = debug;
	this.ironThrone = -1;
    }

    public void clear_Current_Scores() { current_Scores = 0; }
    public void addTo_Current_Scores(int s) { current_Scores += s; }

    public void update_Total_Scores() { total_Scores += current_Scores; }
    public int get_Total_Scores() { return total_Scores; }
    
    public void update_Current_Suit(Card.Suit suit) {
	current_Suit = suit;
    }
    public Card.Suit get_Current_Suit() {
	return current_Suit;
    }
    public void update_Current_Counts(int counts) {
	current_Counts = counts;
    }
    public int get_Current_Counts() {
	return current_Counts;
    }

    public void update_Current_Structure(CardStructure cs) {
	current_Structure = cs;
    }
    public CardStructure get_Current_Structure() {
	return current_Structure;
    }
    
    public void updateKeySuit(Card.Suit suit,int one_or_two_or_king,int player_ID) {
	if(!suit_Decleared && one_or_two_or_king==1) {
	    suit_Decleared = true;
	    key_Suit = suit;
	    System.out.print(suit + "ID" + player_ID + " ");
	    if (ironThrone<0) {
		ironThrone_temp = player_ID;
	    }
	}
	else if(!suit_Double_Decleared && one_or_two_or_king==2) {
	    suit_Decleared = true;
	    suit_Double_Decleared = true;
	    key_Suit = suit;
	    System.out.print(suit + "ID" + player_ID + " ");
	    if (ironThrone<0) {
		ironThrone_temp = player_ID;
	    }
	}
	else if(!suit_Queen_Decleared && one_or_two_or_king==3) {
	    suit_Decleared = true;
	    suit_Double_Decleared = true;
	    suit_Queen_Decleared = true;
	    key_Suit = suit;
	    System.out.print(suit + "ID" + player_ID + " ");
	    if (ironThrone<0) {
		ironThrone_temp = player_ID;
	    }
	}
	else if(!suit_King_Decleared && one_or_two_or_king==4) {
	    suit_Decleared = true;
	    suit_Double_Decleared = true;
	    suit_Queen_Decleared = true;
	    suit_King_Decleared = true;
	    key_Suit = suit;
	    System.out.print(suit + "ID" + player_ID + " ");
	    if (ironThrone<0) {
		ironThrone_temp = player_ID;
	    }
	}
    }

    public void update_IronThrone() {
	if(ironThrone_temp!=ironThrone) {
	    ironThrone = ironThrone_temp;
	}
    }
    
    public void printOutGameInfo() {
	System.out.println();
	System.out.println("Number of Players: " + NPlayer);
	System.out.println("Key Number: " + key_Number);
	System.out.print("Key Suit: " + key_Suit);
	if(suit_King_Decleared||suit_Double_Decleared) {
	    System.out.println(" Double");
	}
	else {
	    System.out.println();
	}
	System.out.println("Iron Throne: " + ironThrone);
    }

    public Card.Suit get_Key_Suit() {
	return key_Suit;
    }
    public int get_Key_Number() {
	return key_Number;
    }
    public int get_IronThrone() {
	return ironThrone;
    }
}
