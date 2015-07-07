
public class Card {

    private Suit type;
    private int number;
    public static enum Suit {
	SPADE, HEART, DIAMOND, CLUB, KING
    }

    public Card() { }
    public Card(Suit type, int number) {
	this.type = type;
	this.number = number;
    }
    public Suit getType() {
	return this.type;
    }
    public int getNumber() {
	return this.number;
    }
}
