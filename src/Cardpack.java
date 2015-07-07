
public class Cardpack {

    Card[] cardpack;
    int playerID;
    public Cardpack(int NPlayer,int playerID,Shuffle shuffleGenerator) {
	this.playerID = playerID;
	cardpack = new Card[shuffleGenerator.allCards.length/NPlayer];
    }
}
