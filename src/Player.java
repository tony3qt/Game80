
/** 
 * Player controls its onwn cards and makes decision on how to play in the game; 
 * HumanPlayer and ComputerPlayer inherits from Player
 * Player has a manager, and instance of CardManager, all the card operation is done through manager.
 */
public abstract class Player {

    protected Card[] cardpack;
    protected Card[] table_Cards;
    
    protected int playerID;
    protected CardManager manager;
    protected Shuffle shuffleGenerator;

    protected int[] suit_Counts;
    protected int[] keyNumber_Counts;
    protected int L_Joker_Counts;
    protected int H_Joker_Counts;

    protected GameInfo gameInfo;
    protected int keyNumber;
    protected int NPlayer;
    protected int total_Card_Number;
    protected int current_Card_Number;

    protected CardStructure play_Structure;

    /** 
     * Player gets cards from shuffleGenerator, an instance of Shuffle class,
     * which has a sequence of shuffled cards;
     * Each Player has its own ID.
     */
    public Player(GameInfo gameInfo, int playerID, Shuffle shuffleGenerator) {
	this.gameInfo = gameInfo;
	this.playerID = playerID;
	this.keyNumber = gameInfo.get_Key_Number();
	this.NPlayer = gameInfo.NPlayer;
	this.total_Card_Number = shuffleGenerator.allCards.length/NPlayer - 2;
	this.cardpack = new Card[total_Card_Number];		
	this.current_Card_Number = 0;
	this.shuffleGenerator = shuffleGenerator;
	this.suit_Counts = new int[4];
	this.keyNumber_Counts = new int[4];
	this.L_Joker_Counts = 0;
	this.H_Joker_Counts = 0;
    }
    
    public void printID() {
	System.out.print("Player " + playerID);
    }

    /** 
     * In card distribution process, getOneCard() is called on each player to get cards from shuffleGenerator.
     */
    public void getOneCard() {

    }

    /** 
     * Create CardManager to manage the cards in player's hand;
     * Sorting work is done by calling this function;
     */
    public void sortCard() {	
    	this.manager = new CardManager(cardpack, table_Cards, gameInfo);
	System.out.println("Player ID = " + playerID + " : ");
	manager.printOutCard_in_Order();
    }

    /** 
     * If the player takes the IronThrone for this round, 
     * then player_get_Table_card() will be called to collect the left eight cards on table.
     */
    public void playerGetTableCard() {
	
	this.table_Cards = new Card[2*NPlayer];
	for(int i=0;i<2*NPlayer;i++) {
	    table_Cards[i] = shuffleGenerator.allCards[total_Card_Number*NPlayer+i];
	}
    }

    public abstract boolean playerSetTableCard();
    
    public abstract boolean playCards(boolean starter) ;

    /** 
     * Test whether player has a card;
     * If yes, return the card, otherwise return null.
     */  
    public Card contains(Card.Suit suit, int number) {
	return manager.contains(suit, number);
    }

    /**
     * Remove a card from player if exists,
     * Do nothing otherwise.
     */
    public void remove(Card.Suit suit, int number) {
	manager.remove(suit, number);
    }

    public void printOutCard_in_Order() {
	System.out.println("Player ID = " + playerID + " : ");
	manager.printOutCard_in_Order();
    }

    public int get_ID() {
	return playerID;
    }

    public CardManager get_Manager() {
	return manager;
    }

    public void update_Play_Structure(CardStructure cs) {
	play_Structure = cs;
    }
    public CardStructure get_Play_Structure() {
	return play_Structure;
    }
}
