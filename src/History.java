import java.util.ArrayList;

/**
 * Save all cards played out in the past rounds.
 */
public class History {

    private ArrayList<CardStructure>[] card_Structure_History;
    private ArrayList<Integer> starter_ID;
    private ArrayList<Double> confidence;
    
    public History() {
	@SuppressWarnings("unchecked")
	ArrayList<CardStructure>[] card_Structure_History = (ArrayList<CardStructure>[]) new ArrayList[4];
	for(int i=0; i<4; i++) {
	    card_Structure_History[i] = new ArrayList<CardStructure>();
	}
	this.card_Structure_History = card_Structure_History;
    }

    public void update_History(int ID, CardStructure cs, boolean starter, double cfd_Value) {
	card_Structure_History[ID].add(cs);
	if(starter) {
	    starter_ID.add(ID);
	    this.confidence.add(cfd_Value);
	}
    }
}
