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
	    if (uniform_Suit == gameInfo.key_Suit) {
		if(gameInfo.key_Suit != Card.Suit.H_JOKER && gameInfo.key_Suit != Card.Suit.L_JOKER ) {
		    card_Table = new int[gameInfo.CARD_IN_EACH_SUIT-1 + 4 + 2];
		    for (Card card : args) {
			card_Number ++;
			if (card.getNumber() < gameInfo.key_Number && card.getSuit() != Card.Suit.H_JOKER && card.getSuit() != Card.Suit.L_JOKER)
			    card_Table[card.getNumber()-2] ++;
			else if (card.getNumber() > gameInfo.key_Number && card.getSuit() != Card.Suit.H_JOKER && card.getSuit() != Card.Suit.L_JOKER)
			    card_Table[card.getNumber()-3] ++;
			else if (card.getNumber() == gameInfo.key_Number && card.getSuit() != gameInfo.key_Suit) 
			    card_Table[(card.getSuit().getValue() - gameInfo.key_Suit.getValue()+4)%4-1 + gameInfo.CARD_IN_EACH_SUIT-2] ++ ;

			else if (card.getNumber() == gameInfo.key_Number && card.getSuit() == gameInfo.key_Suit)
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
			if (card.getNumber() == gameInfo.key_Number) 
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
		    if(card.getNumber() < gameInfo.key_Number)
			card_Table[card.getNumber()-2] ++;
		    else
			card_Table[card.getNumber()-3] ++;
		}
	    }
	    renomalize();
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
	    uniform_Suit = gameInfo.key_Suit;
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
    public void renomalize() {
	card_Table_Renormalized = new int[card_Table.length];
	int temp = 0;
	for(int i=0;i<card_Table.length;i++) {
	    if(card_Table[i] == 2) {
		card_Table_Renormalized[temp] += 2;
	    }
	    else {
		card_Table_Renormalized[i] = card_Table[i];
		temp = i+1;
	    }
	}
	
    }
    public void generateStructure() {
	structure_List = new ArrayList<StructureNode> ();
	for (int i=0;i<card_Table_Renormalized.length;i++) {
	    if(card_Table_Renormalized[i]!=0) {
		structure_List.add(new StructureNode(card_Table_Renormalized[i],i));
	    }
	}
	Collections.sort(structure_List);

    }

    public Card.Suit get_Uniform_Suit() { return uniform_Suit; }

    public ArrayList<StructureNode> get_Structure_List() { return structure_List; }

    public int get_Card_Number() { return card_Number; }
}
