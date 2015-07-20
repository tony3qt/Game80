import java.util.Comparator;
import java.util.Collections;

public class StructureComparator implements Comparator<CardStructure> {

    GameInfo gameInfo;
    public StructureComparator(GameInfo gameInfo) {
	this.gameInfo = gameInfo;
    }

    @Override
    public int compare(CardStructure cs1, CardStructure cs2) {
	return compare(cs1, cs2, cs1);
    }

    public int compare(CardStructure cs1, CardStructure cs2, CardStructure cs_template) {
	assert cs_template.uniform_Suit != null;
	assert cs1.uniform_Suit == cs_template.uniform_Suit || cs1.uniform_Suit == gameInfo.key_Suit;
	assert cast(cs1, cs_template);

	if (cs2.uniform_Suit == null || (cs2.uniform_Suit != cs_template.uniform_Suit && cs2.uniform_Suit != gameInfo.key_Suit) ) {
	    return 1;
	}
	if (cast(cs2, cs_template)) {
	    return cs1.structureList.get(0).compareTo(cs_template.structureList.get(0));
	}
	else {
	    return 1;
	}
    }

    public boolean cast (CardStructure cs, CardStructure cs_template) {

	assert cs_template.uniform_Suit != null;
	assert cs_template.card_Number == cs.card_Number;

	int type1, type2, start1;
	if (cs.uniform_Suit == null) {
	    return false;
	}	
	else {
	    for (int i=0; i<cs_template.structureList.size(); i++) {
		type1 = cs.structureList.get(i).type;
		type2 = cs_template.structureList.get(i).type;
		start1 = cs.structureList.get(i).start;
		if (type1 < type2) {
		    return false;
		} 
		while ( type1 > type2 ) {
		    cs.structureList.get(i).type = type2;
		    cs.structureList.get(i).start = cs.structureList.get(i).start + (type1-type2)/2;
		    cs.structureList.add(new StructureNode(type1-type2,start1));
		    Collections.sort(cs.structureList);
		    type1 = cs.structureList.get(i).type;
		    start1 = cs.structureList.get(i).start;
		}
	    }
	}
	return true;
    }
}
