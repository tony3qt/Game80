import java.util.ArrayList;
import java.util.Collections;

public class CardStructure {

    Card.Suit uniform_Suit;
    int[] card_Table;
    int[] card_Table_Renormalized;
    GameInfo gameInfo;
    int card_Number;
    
    ArrayList<StructureNode> structureList;
    
    public CardStructure(GameInfo gameInfo, Card... args) {
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
	    structureList = new ArrayList<StructureNode>();
	    for (Card card : args) {
		card_Number ++ ;
	    }
	}	
	     
    }

    public boolean testSuit(Card... args) {

	if(args[0].isKey()) {
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
	    uniform_Suit = args[0].getSuit();
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
	for (int i=0;i<structureList.size();i++) {
	    structureList.get(i).print_StructureNode();
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
	structureList = new ArrayList<StructureNode> ();
	for (int i=0;i<card_Table_Renormalized.length;i++) {
	    if(card_Table_Renormalized[i]!=0) {
		structureList.add(new StructureNode(card_Table_Renormalized[i],i));
	    }
	}
	Collections.sort(structureList);

    }

    
    
}
