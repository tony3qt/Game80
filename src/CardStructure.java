import java.util.ArrayList;
import java.util.Collections;
import java.lang.Comparable;
/**
 * CardStructure extract the structure of an ArrayList of cards;
 * And express the structure in structure_List, which is an ArrayList of StructureNode.
 */

public class CardStructure {

    Card.Suit uniform_Suit;
    private int[] card_Table;
    private int[] card_Table_Renormalized;
    GameInfo gameInfo;
    int card_Number;
    
    private ArrayList<StructureNode> structure_List;
    
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
    private void renormalize() {
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
    
    private void generateStructure() {
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

    public int size() { return structure_List.size(); }

    public int get_Card_Number() { return card_Number; }

    private ArrayList<StructureNode> get_Structure_List() { return structure_List; }
    
    private boolean downcast() {
	if (structure_List.get(0).type<4) return false;
	else {
	    structure_List.get(0).type -= 2;
	    structure_List.add(new StructureNode(structure_List.get(0).start,2));
	    structure_List.get(0).start += 1;
	    Collections.sort(structure_List);
	    return true;
	}
    }
    
    
     /**
      * Reorganize the structureNode of cs, to get the same structure as cs_template;
      * CardStructure cs could be changed.
      * Eg, cs:(4,3), cs_template:(2,12), (2,14), cs will be (2,3),(2,4) after cast is called.
      */
    private static boolean cast (CardStructure cs, CardStructure cs_template) {

	assert cs_template.get_Uniform_Suit() != null;
	assert cs_template.card_Number == cs.card_Number;

	int type1, type2, start1;
	if (cs.get_Uniform_Suit() == null) {
	    return false;
	}	
	else {
	    for (int i=0; i<cs_template.get_Structure_List().size(); i++) {
		type1 = cs.get_Structure_List().get(i).type;
		type2 = cs_template.get_Structure_List().get(i).type;
		start1 = cs.get_Structure_List().get(i).start;
		if (type1 < type2) {
		    return false;
		} 
		while ( type1 > type2 ) {
		    cs.get_Structure_List().get(i).type = type2;
		    cs.get_Structure_List().get(i).start = cs.get_Structure_List().get(i).start + (type1-type2)/2;
		    cs.get_Structure_List().add(new StructureNode(type1-type2,start1));
		    Collections.sort(cs.get_Structure_List());
		    type1 = cs.get_Structure_List().get(i).type;
		    start1 = cs.get_Structure_List().get(i).start;
		}
	    }
	}
	return true;
    }

    public static int compare(CardStructure cs1, CardStructure cs2, GameInfo gameInfo) {
	return compare(cs1, cs2, cs1, gameInfo);
    }
    
    /**
     * Two CardStructure instances are being compared based on the structure of the cs_template.
     * If cs1 is bigger, return -1; smaller, return 1; equal, return 0;s
     */
    public static int compare(CardStructure cs1, CardStructure cs2, CardStructure cs_template, GameInfo gameInfo) {
	assert cs_template.get_Uniform_Suit() != null;
	assert cs1.get_Uniform_Suit() == cs_template.get_Uniform_Suit() || cs1.get_Uniform_Suit() == gameInfo.get_Key_Suit();
	assert CardStructure.cast(cs1, cs_template);

	if (cs2.get_Uniform_Suit() == null || ((cs2.get_Uniform_Suit() != cs_template.get_Uniform_Suit() && cs2.get_Uniform_Suit() != gameInfo.get_Key_Suit())) ) {
	    return -1;
	}
	if (cs2.get_Uniform_Suit() == gameInfo.get_Key_Suit() && cs1.get_Uniform_Suit() != gameInfo.get_Key_Suit() &&
	    CardStructure.cast(cs2, cs_template)) { 
	    return 1;
	}
	if (cs2.get_Uniform_Suit() != gameInfo.get_Key_Suit() && cs1.get_Uniform_Suit() == gameInfo.get_Key_Suit() ) {
	    return -1;
	}
	if (CardStructure.cast(cs2, cs_template)) {
	    return cs1.get_Structure_List().get(0).compareTo(cs2.get_Structure_List().get(0));
	}
	else {
	    return -1;
	}
    }

    
    /**
     * Return a ArrayList of Boolean the same size as structure_List of cs. 
     * Each boolean indicates whether there coulbe be a same structureNode in card_List.
     */
    public static ArrayList<Boolean> structure_Analyze( CardStructure cs, ArrayList<Card> card_List, GameInfo gameInfo) {
	assert cs.get_Uniform_Suit() != null;
	CardStructure cs_test = new CardStructure(gameInfo, card_List);
	assert cs_test.get_Uniform_Suit() == cs.get_Uniform_Suit();
	assert cs.get_Card_Number() <= cs_test.get_Card_Number();

	int type1, type2, start1, start2;
	int size = cs.get_Structure_List().size();
	ArrayList<Boolean> structure_Boolean_List = new ArrayList<Boolean>();
	
	/*
	int j = 0;
	for(int i=0; i<size; i++) {
	    type1 = cs.get_Structure_List().get(i).type;
	    type2 = cs_test.get_Structure_List().get(j).type;
	    if(type1 > type2) {
		structure_Boolean_List.add(false);
	    }
	    else if(type1 == type2) {
		structure_Boolean_List.add(true);
		j++;
	    }
	    else {
		structure_Boolean_List.add(true);
		start2 = cs_test.get_Structure_List().get(j).start;
		cs_test.get_Structure_List().get(j).type = type1;
		cs_test.get_Structure_List().get(j).start = start2 + (type2-type1)/2;
		cs_test.get_Structure_List().remove(j);
		cs_test.get_Structure_List().add(new StructureNode(type2-type1,start2));
		Collections.sort(cs.get_Structure_List());
	    }
	}
	*/
	
	int j = 0;
	for(int i=0; i<cs.size();) {
	    type1 = cs.get_Structure_List().get(i).type;
	    type2 = cs_test.get_Structure_List().get(j).type;
	    if(type1 > type2) {
		structure_Boolean_List.add(false);
		start1 = cs.get_Structure_List().get(i).start;
		cs.get_Structure_List().get(i).type = type2;
		cs.get_Structure_List().get(i).start = start1 + (type1-type2)/2;
		cs.get_Structure_List().remove(i);
		cs_test.get_Structure_List().remove(j);
		cs.get_Structure_List().add(new StructureNode(type1-type2,start1));
		Collections.sort(cs.get_Structure_List());
		
	    }
	    else if(type1 == type2) {
		structure_Boolean_List.add(true);
		cs.get_Structure_List().remove(i);
		cs_test.get_Structure_List().remove(j);
		
	    }
	    else {
		structure_Boolean_List.add(true);
		start2 = cs_test.get_Structure_List().get(j).start;
		cs_test.get_Structure_List().get(j).type = type1;
		cs_test.get_Structure_List().get(j).start = start2 + (type2-type1)/2;
		cs_test.get_Structure_List().remove(j);
		cs.get_Structure_List().remove(i);
		cs_test.get_Structure_List().add(new StructureNode(type2-type1,start2));
		Collections.sort(cs_test.get_Structure_List());
	    }
	}

	cs.generateStructure();
	//assert structure_Boolean_List.size() == size;
	return structure_Boolean_List;
    }

    
    /**
     * If the player(ID=ID) has a structureNode, which is larger than that of the index-th strucutreNode of cs, (cs comes from player),
     * an arrayList of cards containing the index-th structureNode will be returned;
     * Otherwise, return null.
     */
    public static ArrayList<Card> structure_Node_Analyze(CardStructure cs, Player player, int index, int ID, GameInfo gameInfo) {

	assert cs.get_Uniform_Suit() != null;
	assert cs.size() > index;
	
	Card.Suit suit = cs.get_Uniform_Suit();
	CardStructure cs_test;
	ArrayList<Card> returnList = new ArrayList<Card>();
	int type = cs.get_Structure_List().get(index).type;
	int start = cs.get_Structure_List().get(index).start;
	
	if (suit == gameInfo.get_Key_Suit()) {
	    cs_test = new CardStructure(gameInfo, gameInfo.players[ID].get_Manager().get_key_List());
	    Card.Suit key_suit_in_array[] = new Card.Suit[18];
	    for(int i=0; i<12; i++) {
		key_suit_in_array[i] = gameInfo.get_Key_Suit();
	    }
	    key_suit_in_array[12] = Card.Suit.getSuit((gameInfo.get_Key_Suit().getValue()+1)%4);
	    key_suit_in_array[13] = Card.Suit.getSuit((gameInfo.get_Key_Suit().getValue()+2)%4);
	    key_suit_in_array[14] = Card.Suit.getSuit((gameInfo.get_Key_Suit().getValue()+3)%4);
	    key_suit_in_array[15] = gameInfo.get_Key_Suit();
	    key_suit_in_array[16] = Card.Suit.getSuit(4);
	    key_suit_in_array[17] = Card.Suit.getSuit(5);

	    int size = cs_test.get_Structure_List().size();
	    for(int j=0; j<size; ) {
		int type_test = cs_test.get_Structure_List().get(j).type;
		int start_test = cs_test.get_Structure_List().get(j).start;
		if (type > type_test) { return null; }
		else if (type == type_test) {
		    if (cs.get_Structure_List().get(index).compareTo(cs_test.get_Structure_List().get(j)) <=0 ) { return null; }
		    else {
			for (int n=0; n<type; n++) {
			    if(start+n/2+2 < gameInfo.get_Key_Number() && start+n/2 < 12)
				returnList.add(player.contains(key_suit_in_array[start+n/2], start+n/2+2));
			    else if(start+n/2+2 > gameInfo.get_Key_Number() && start+n/2 < 12) {
				returnList.add(player.contains(key_suit_in_array[start+n/2], start+n/2+3));
			    }
			    else if(start+n/2 >= 12 && start+n/2<=15) {
				returnList.add(player.contains(key_suit_in_array[start+n/2], gameInfo.get_Key_Number()));
			    }
			    else { returnList.add(player.contains(key_suit_in_array[start+n/2], 0)); }
			}
			player.get_Manager().deactivate_All();
			return returnList;
		    }
		}
		else {
		    if (start < start_test) {
			for (int n=0; n<type; n++) {
			    if(start+n/2+2 < gameInfo.get_Key_Number())
				returnList.add(player.contains(suit, start+n/2+2));
			    else
				returnList.add(player.contains(suit, start+n/2+3));
			}
			player.get_Manager().deactivate_All();
			return returnList;
		    }
		    else {j++;}
		}
	    }


	    
	}
	else { 
	    cs_test = new CardStructure(gameInfo, gameInfo.players[ID].get_Manager().get_List(suit));
	
	    int size = cs_test.get_Structure_List().size();
	    for(int j=0; j<size; ) {
		int type_test = cs_test.get_Structure_List().get(j).type;
		int start_test = cs_test.get_Structure_List().get(j).start;
		if (type > type_test) { return null; }
		else if (type == type_test) {
		    if (cs.get_Structure_List().get(index).compareTo(cs_test.get_Structure_List().get(j)) <=0 ) { return null; }
		    else {
			for (int n=0; n<type; n++) {
			    if(start+n/2+2 < gameInfo.get_Key_Number())
				returnList.add(player.contains(suit, start+n/2+2));
			    else
				returnList.add(player.contains(suit, start+n/2+3));
			}
			player.get_Manager().deactivate_All();
			return returnList;
		    }
		}
		else {
		    if (start < start_test) {
			for (int n=0; n<type; n++) {
			    if(start+n/2+2 < gameInfo.get_Key_Number())
				returnList.add(player.contains(suit, start+n/2+2));
			    else
				returnList.add(player.contains(suit, start+n/2+3));
			}
			player.get_Manager().deactivate_All();
			return returnList;
		    }
		    else {j++;}
		}
	    }
		
	    
	}
	return null;
    }

     /**
     * Inner class
     */
    static class StructureNode implements Comparable<StructureNode> {

	int type;
	int start;
	public StructureNode(int type, int start) {
	    this.type = type;
	    this.start = start;
	}
	
	@Override
	public int compareTo(StructureNode otherNode) {
	    if (this.type != otherNode.type) {
		return otherNode.type - this.type;
	    }
	    else {
		if((otherNode.start > 11) && (otherNode.start < 15) && (this.start >11) && (this.start < 15)) {
		    //System.out.println("Same");
		    return 0;
		}
		else
		    return otherNode.start - this.start;
	    }
	}
	public void print_StructureNode() {
	System.out.println("( " + type + " , " + start + " )");
	}
    }
}
