import java.util.ArrayList;
import java.util.Collections;

/**
 * CardStructure extract the structure of an ArrayList of cards;
 * And express the structure in structure_List, which is an ArrayList of StructureNode.
 */

public class CardStructure {

    Card.Suit uniform_Suit;
    int[] card_Table;
    int[] card_Table_Renormalized;
    GameInfo gameInfo;
    int card_Number;
    
    ArrayList<StructureNode> structure_List;

    /**
     * Construct a table(card_Table) of size equals the size of cards'suit, each element is 0, 1 or 2;
     * renomalize() groups cards if a double of a truck exists;
     * generateStructure() constructe the structure_List;
     */
    public CardStructure(GameInfo gameInfo, ArrayList<Card> args) {
	this.gameInfo = gameInfo;
	uniform_Suit = null;
	card_Table = new int[0];
	if (testSuit(args)) {
	    if (uniform_Suit == gameInfo.get_Key_Suit()) {
		if(gameInfo.get_Key_Suit() != Card.Suit.H_JOKER && gameInfo.get_Key_Suit() != Card.Suit.L_JOKER ) {
		    card_Table = new int[gameInfo.CARD_IN_EACH_SUIT-1 + 4 + 2];
		    for (Card card : args) {
			card_Number ++;
			if (card.getNumber() < gameInfo.get_Key_Number() && card.getSuit() != Card.Suit.H_JOKER && card.getSuit() != Card.Suit.L_JOKER)
			    card_Table[card.getNumber()-2] ++;
			else if (card.getNumber() > gameInfo.get_Key_Number() && card.getSuit() != Card.Suit.H_JOKER && card.getSuit() != Card.Suit.L_JOKER)
			    card_Table[card.getNumber()-3] ++;
			else if (card.getNumber() == gameInfo.get_Key_Number() && card.getSuit() != gameInfo.get_Key_Suit()) 
			    card_Table[(card.getSuit().getValue() - gameInfo.get_Key_Suit().getValue()+4)%4 + gameInfo.CARD_IN_EACH_SUIT-2] ++ ;

			else if (card.getNumber() == gameInfo.get_Key_Number() && card.getSuit() == gameInfo.get_Key_Suit())
			    card_Table[gameInfo.CARD_IN_EACH_SUIT+2] ++ ;
			else if (card.getSuit() == Card.Suit.L_JOKER)
			    card_Table[gameInfo.CARD_IN_EACH_SUIT+3] ++ ;
			else if (card.getSuit() == Card.Suit.H_JOKER)
			    card_Table[gameInfo.CARD_IN_EACH_SUIT+4] ++ ;
		    }
		}
		else {
		    card_Table = new int[6];
		    for (Card card: args) {
			card_Number ++;
			if (card.getNumber() == gameInfo.get_Key_Number()) 
			    card_Table[card.getSuit().getValue()] ++ ;
			else if (card.getSuit() == Card.Suit.L_JOKER)
			    card_Table[4] ++ ;
			else if (card.getSuit() == Card.Suit.H_JOKER)
			    card_Table[5] ++ ;
		    }
		}
		
	    }
	    else {
		card_Table = new int[gameInfo.CARD_IN_EACH_SUIT-1];
		for (Card card : args) {
		    if(card.getNumber() < gameInfo.get_Key_Number())
			card_Table[card.getNumber()-2] ++;
		    else
			card_Table[card.getNumber()-3] ++;
		}
	    }
	    renormalize();
	    generateStructure();
	}
    
	else {
	    uniform_Suit = null;
	    card_Table_Renormalized = new int[0];
	    structure_List = new ArrayList<StructureNode>();
	    for (Card card : args) {
		card_Number ++ ;
	    }
	}	
	     
    }

    /**
     * Test whether the cards have the same suit;
     */
    public boolean testSuit(ArrayList<Card> args) {

	if(args.get(0).isKey()) {
	    uniform_Suit = gameInfo.get_Key_Suit();
	    for (Card card : args) {
		if (!card.isKey()) {
		    //System.out.println("Error: Suit is not uniform!");
		    return false;
		}
	    }
	    return true;
	}

	else {
	    uniform_Suit = args.get(0).getSuit();
	    for (Card card : args) {
		if (uniform_Suit != card.getSuit() || card.isKey()) {
		    //System.out.println("Error: Suit is not uniform!");
		    return false;
		}
	    }
	    return true;
	}
    }

    public void printTable() {
	System.out.print(uniform_Suit);
	for (int i=0;i<structure_List.size();i++) {
	    structure_List.get(i).print_StructureNode();
	}
	for (int i=0;i<card_Table_Renormalized.length;i++) {
	    System.out.print(card_Table_Renormalized[i] + " ");
	}
    }
    public void renormalize() {
	card_Table_Renormalized = new int[card_Table.length];
	// Copy card_Table to card_Table_Renormalized;
	for(int i=0; i<card_Table.length; i++) {
	    card_Table_Renormalized[i] = card_Table[i];
	}
	// Renormalizing process;
	int temp = 0;
	for(int i=0; i<card_Table.length; i++) {
	    if (i<=11 || i>=15) {
		if(card_Table[i] == 2) {
		    if (temp != i) {
			card_Table_Renormalized[temp] += 2;
			card_Table_Renormalized[i] = 0;
		    }
		}
		else {
		    temp = i+1;
		}
	    }
	    else {
		// i == 12, 13, 14;
		if (i==12 || i==13) {
		    if (card_Table[i] == 2) {
			if (temp != i) {
			    card_Table_Renormalized[temp] += 2;
			    card_Table_Renormalized[i] = 0;
			}
			i = 14;
		    }
		    else {
			if (temp != i) {}
			else { temp = temp+1; }
		        
		    }
		}
		else if (i==14) {
		    if (card_Table[i] == 2) {
			if (temp != i) {
			    card_Table_Renormalized[temp] += 2;
			    card_Table_Renormalized[i] = 0;
			}
		    }
		    else {
			temp = 15;
		    }
		}
	    }
	    
	}
	
    }
    public void generateStructure() {
	structure_List = new ArrayList<StructureNode> ();
	for (int i=0;i<card_Table_Renormalized.length;i++) {
	    if(card_Table_Renormalized[i]!=0)
		{
		    structure_List.add(new StructureNode(card_Table_Renormalized[i],i));
		}
	}
	Collections.sort(structure_List);

    }

    public Card.Suit get_Uniform_Suit() { return uniform_Suit; }

    public ArrayList<StructureNode> get_Structure_List() { return structure_List; }

    public int get_Card_Number() { return card_Number; }
}
