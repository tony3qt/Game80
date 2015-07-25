import java.lang.Comparable;

public class StructureNode implements Comparable<StructureNode> {

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
		System.out.println("Same");
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
