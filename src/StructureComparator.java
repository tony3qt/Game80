import java.util.Collections;
import java.util.ArrayList;

public class StructureComparator {

    /**
     * Private constructor to prevent instantiation.
     */
    private StructureComparator() { }

    
    public static int compare(CardStructure cs1, CardStructure cs2, GameInfo gameInfo) {
	return compare(cs1, cs2, cs1, gameInfo);
    }

    public static int compare(CardStructure cs1, CardStructure cs2, CardStructure cs_template, GameInfo gameInfo) {
	assert cs_template.get_Uniform_Suit() != null;
	assert cs1.get_Uniform_Suit() == cs_template.get_Uniform_Suit() || cs1.get_Uniform_Suit() == gameInfo.get_Key_Suit();
	assert cast(cs1, cs_template);

	if (cs2.get_Uniform_Suit() == null || (cs2.get_Uniform_Suit() != cs_template.get_Uniform_Suit() && cs2.get_Uniform_Suit() != gameInfo.get_Key_Suit()) ) {
	    return -1;
	}
	if (cast(cs2, cs_template)) {
	    return cs1.get_Structure_List().get(0).compareTo(cs2.get_Structure_List().get(0));
	}
	else {
	    return -1;
	}
    }

    public static boolean cast (CardStructure cs, CardStructure cs_template) {

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

    public static ArrayList<Boolean> structure_Analyze( CardStructure cs, ArrayList<Card> card_List, GameInfo gameInfo) {
	assert cs.get_Uniform_Suit() != null;
	CardStructure cs_test = new CardStructure(gameInfo, card_List);
	assert cs_test.get_Uniform_Suit() == cs.get_Uniform_Suit();
	assert cs.get_Card_Number() <= cs.get_Card_Number();

	int type1, type2, start2;
	int size = cs.get_Structure_List().size();
	ArrayList<Boolean> structure_Boolean_List = new ArrayList<Boolean>();
	int j = 0;
	for(int i=0;i<size; i++) {
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
	
	assert structure_Boolean_List.size() == size;
	return structure_Boolean_List;
    }
}
