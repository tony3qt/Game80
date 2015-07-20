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
	    return otherNode.start - this.start;
	}
    }
    public void print_StructureNode() {
	System.out.println("( " + type + " , " + start + " )");
    }
}
