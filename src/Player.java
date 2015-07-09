
public class Player {

    protected Card[] cardpack;
    protected int playerID;
    protected int total_Card_Number;
    protected int current_Card_Number;
    protected int NPlayer;
    protected Shuffle shuffleGenerator;
    protected int[] suit_Counts;
    protected int[] keyNumber_Counts;
    protected int queen_Counts;
    protected int king_Counts;
    protected int keyNumber;
    protected int lowerBound = 12;
    protected GameInfo gameInfo;
    protected CardManager manager;
    
    public Player(GameInfo gameInfo,int playerID, Shuffle shuffleGenerator) {
	this.gameInfo = gameInfo;
	this.playerID = playerID;
	this.keyNumber = gameInfo.key_Number;
	this.NPlayer = gameInfo.NPlayer;
	this.total_Card_Number = shuffleGenerator.allCards.length/NPlayer - 2;
	this.cardpack = new Card[total_Card_Number];		
	this.current_Card_Number = 0;
	this.shuffleGenerator = shuffleGenerator;
	this.suit_Counts = new int[4];
	this.keyNumber_Counts = new int[4];
	this.queen_Counts = 0;
	this.king_Counts = 0;
    }
    public void printID() {
	System.out.print("Player " + playerID);
    }

    public void getOneCard() {


    }
    public void sortCard() {	
    	this.manager = new CardManager(cardpack,gameInfo);
	manager.printOutCard_in_Order();
    }
    
}
