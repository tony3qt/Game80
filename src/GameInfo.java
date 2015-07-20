
public class GameInfo {
    public static final int CARD_IN_EACH_PACKAGE = 54;
    public static final int CARD_IN_EACH_SUIT = 13;
    public static final int SUIT_NUMBER = 4;
    public boolean DEBUG;
    public int NPlayer;
    public int NPackage;
    public int key_Number;
    public boolean suit_Decleared;
    public boolean suit_Double_Decleared;
    public boolean suit_Queen_Decleared;
    public boolean suit_King_Decleared;
    public Card.Suit key_Suit;
    public int key_from_playerID;

    public int ironThrone;
    public int ironThrone_temp;

    public GameInfo(int NPlayer, int NPackage, int key_Number,boolean debug) {
	this.NPlayer = NPlayer;
	this.NPackage = NPackage;
	this.key_Number = key_Number;
	this.DEBUG = debug;
	this.ironThrone = -1;
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
}
