import java.util.ArrayList;
import java.util.Collections;

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
    protected History history;
    
    /** 
     * Player gets cards from shuffleGenerator, an instance of Shuffle class,
     * which has a sequence of shuffled cards;
     * Each Player has its own ID.
     */
    public Player(GameInfo gameInfo, int playerID, Shuffle shuffleGenerator, History history) {
	this.gameInfo = gameInfo;
	this.history = history;
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
    
    public abstract boolean playCards(int starter) ;

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

    protected ArrayList<Card> my_Card_List(Card.Suit suit) {
	return manager.get_List(suit);
    }

    public void give_Suggestion(int starter, int winner_ID) {
	CardStructure suit_cs;
	Card.Suit suit;
	int start;
	int type;
	if (starter==0) {
	    for(int i=0; i<4; i++) {
		suit = Card.Suit.getSuit(i);
		if (suit != gameInfo.get_Key_Suit()) {
		    suit_cs = new CardStructure(gameInfo, my_Card_List(suit));
		    for(int j=0; j<suit_cs.size(); j++) {
			start = suit_cs.get_Structure_Node_Start(j);
			type = suit_cs.get_Structure_Node_Type(j);
			if (history.win_Prob(suit, type, start, my_Card_List(suit))>0.99) {
			    System.out.println("Suggestion: " + suit + "type: " + type + "start:" + start);
			}
		    }
		}
	    }
	    for(int i=0; i<4; i++) {
		suit = Card.Suit.getSuit(i);
		if (suit != gameInfo.get_Key_Suit()) {
		    suit_cs = new CardStructure(gameInfo, my_Card_List(suit));
		    for(int j=0; j<suit_cs.size(); j++) {
			start = suit_cs.get_Structure_Node_Start(j);
			type = suit_cs.get_Structure_Node_Type(j);
			if (type>=2 && history.win_Prob(suit, type, start, my_Card_List(suit))<0.99 &&
			   history.win_Prob(suit, type, start, my_Card_List(suit))>0.6) {
			    System.out.println("Suggestion: " + suit + " type: " + type + " start:" + start);
			}
		    }
		}
	    }
	}
	if (starter==3) {
	    suit = gameInfo.get_Current_Suit();
	    suit_cs = new CardStructure(gameInfo, my_Card_List(suit));
	    int counts;
	    int index1;
	    int index2;
	    boolean pass = false;
	    if (gameInfo.get_Current_Counts() >= my_Card_List(suit).size()) {
		System.out.println("Suggestion: all in " + suit);
	    }
	    if (gameInfo.get_Current_Counts() < my_Card_List(suit).size()) {
		if ((winner_ID-playerID)%2==0) { // && gameInfo.get_Current_Structure().get_Structure_Node_Type(0)<=2) {

		    
		    for(int i=0; i<gameInfo.get_Current_Structure().size(); i++) {
			pass = false;
			type = gameInfo.get_Current_Structure().get_Structure_Node_Type(i);
			start = gameInfo.get_Current_Structure().get_Structure_Node_Start(i);
			counts = suit_cs.get_Type_Counts(type);
			index1 = 0;
			index2 = 0;

			if (type>=4) {
			    if(counts>0) {
				while(index1 < suit_cs.size() && suit_cs.get_Structure_Node_Type(index1)>type) { index1++; }
				while(index2 < suit_cs.size() && suit_cs.get_Structure_Node_Type(index2)>=type) { index2++; }
				suit_cs.get_Structure_List().remove(index2-1);
				System.out.println("Suggestion: type" + type + " " + suit + " start " + suit_cs.get_Structure_Node_Start(index2-1));
				continue;
			    }
			    if(counts<0 && suit_cs.get_Structure_Node_Type(0)<type) {
				gameInfo.get_Current_Structure().change_Node(i, type-2, start+1);
				gameInfo.get_Current_Structure().add_Node(2,start);
				Collections.sort(gameInfo.get_Current_Structure().get_Structure_List());
				i--;
				continue;
			    }
			    if(counts<0 && suit_cs.get_Structure_Node_Type(0)>type) {
				suit_cs.change_Node(0,suit_cs.get_Structure_Node_Type(0)-type, suit_cs.get_Structure_Node_Start(0)+type/2);
				Collections.sort(suit_cs.get_Structure_List());
				continue;
			    }
			}

			
			if (counts>0) {
			    ///******************************  Single 10, single 13  **************************************///
			    while(index1 < suit_cs.size() && suit_cs.get_Structure_Node_Type(index1)>type) { index1++; }
			    while(index2 < suit_cs.size() && suit_cs.get_Structure_Node_Type(index2)>=type) { index2++; }
			
			    for (int k=index2-1; k>=index1; k--) {
				if (index_to_number(suit_cs.get_Structure_Node_Start(k))==10) {
				    System.out.println("Suggestion: type" + type + " " + suit + 10);
				    pass = true;
				    suit_cs.get_Structure_List().remove(k);
				    break;
				}
				if (index_to_number(suit_cs.get_Structure_Node_Start(k))==13) {
				    System.out.println("Suggestion: type" + type + " " + suit + 13);
				    pass = true;
				    suit_cs.get_Structure_List().remove(k);
				    break;
				}
			    }
			    ///************************************************************************************************///
			    ///****************************  Double 10, double 13 & double 5  ********************************///
			    if(!pass && type==1 && i<gameInfo.get_Current_Structure().size()-1 && suit_cs.get_Type_Counts(2)>0) {
				index1 = 0;
				index2 = 0;
				while(index1 < suit_cs.size() && suit_cs.get_Structure_Node_Type(index1)>2) { index1++; }
				while(index2 < suit_cs.size() && suit_cs.get_Structure_Node_Type(index2)>=2) { index2++; }
				for(int d=index2-1; d>=index1; d--) {
				    if(index_to_number(suit_cs.get_Structure_Node_Start(d))==10) {
				    System.out.println("Suggestion: type2 " + suit + 10);
				    pass = true;
				    suit_cs.get_Structure_List().remove(d);
				    i++;
				    break;
				    }
				    if(index_to_number(suit_cs.get_Structure_Node_Start(d))==13) {
					System.out.println("Suggestion: type2 " + suit + 13);
					pass = true;
					i++;
					suit_cs.get_Structure_List().remove(d);
					break;
				    }
				}
				if(!pass) {
				    for(int d=index2-1; d>=index1; d--) {
					if(index_to_number(suit_cs.get_Structure_Node_Start(d))==5) {
					    System.out.println("Suggestion: type2 " + suit + 5);
					    pass = true;
					    suit_cs.get_Structure_List().remove(d);
					    i++;
					    break;
					}
				    }
				}
			    }
			    ///************************************************************************************************///
			    ///*************************************  Single 5  **********************************************///
			    if (!pass) {
				for(int k=index1; k<index2; k++) {
				    if (index_to_number(suit_cs.get_Structure_Node_Start(k))==5) {
					System.out.println("Suggestion: type" + type + " " + suit + 5);
					pass = true;
					suit_cs.get_Structure_List().remove(k);
					break;
				    }
				}
			    }
			    ///************************************************************************************************///
			    if (!pass) {
				System.out.println("Suggestion: type" + type + " " + suit + index_to_number(suit_cs.get_Structure_Node_Start(index2-1)));
				suit_cs.get_Structure_List().remove(index2-1);
			    }
			    ///************************************************************************************************///
			}
			else if (!pass && suit_cs.get_Structure_Node_Type(0) < type) {
			    int target=0;
			    ///******************************  Single 10, single 13  **************************************///
			    for(int k=suit_cs.size()-1; k>=0; k--) {
				if (index_to_number(suit_cs.get_Structure_Node_Start(k))==10) {
				    System.out.println("Suggestion: type1" + " " + suit + 10);
				    pass = true;
				    target++;
				    suit_cs.get_Structure_List().remove(k);
				    //break; There in no break, because we need two cards, expect target to reach 2.
				}
				if (index_to_number(suit_cs.get_Structure_Node_Start(k))==13) {
				    System.out.println("Suggestion: type1" + " " + suit + 13);
				    pass = true;
				    target++;
				    suit_cs.get_Structure_List().remove(k);
				    break;
				}
			    }
			    ///************************************************************************************************///
			    ///*************************************  Single 5  **********************************************///
			    if (target<2) {
				for(int k=0; k<suit_cs.size(); k++) {
				    if (index_to_number(suit_cs.get_Structure_Node_Start(k))==5) {
					System.out.println("Suggestion: type1" + " " + suit + 5);
					pass = true;
					target++;
					suit_cs.get_Structure_List().remove(k);
					break;
				    }
				}
			    }
			    ///************************************************************************************************///
			    if (target<2) {
				System.out.println("Suggestion: type1" + " " + suit + index_to_number(suit_cs.get_Structure_Node_Start(suit_cs.size()-1)));
				target++;
				suit_cs.get_Structure_List().remove(suit_cs.size()-1);
			    }
			    if (target<2) {
				System.out.println("Suggestion: type1"  + " " + suit + index_to_number(suit_cs.get_Structure_Node_Start(suit_cs.size()-1)));
				target++;
				suit_cs.get_Structure_List().remove(suit_cs.size()-1);
			    }
			    ///************************************************************************************************///
			}
			else if (!pass && suit_cs.get_Structure_Node_Type(0) > type) {
			    if (type==2) {
				if (index_to_number(suit_cs.get_Structure_Node_Start(0))==10
				   || index_to_number(suit_cs.get_Structure_Node_Start(0))==13
				   || index_to_number(suit_cs.get_Structure_Node_Start(0))==5) {
				    System.out.println("Suggestion: type2 " + suit + index_to_number(suit_cs.get_Structure_Node_Start(0)));
				    suit_cs.change_Node(0,suit_cs.get_Structure_Node_Type(0)-2,suit_cs.get_Structure_Node_Start(0)+1);
				    pass = true;
				    Collections.sort(suit_cs.get_Structure_List());
				}
				if (!pass && (index_to_number(suit_cs.get_Structure_Node_Start(0)+suit_cs.get_Structure_Node_Type(0)/2-1)==10
				   || index_to_number(suit_cs.get_Structure_Node_Start(0)+suit_cs.get_Structure_Node_Type(0)/2-1)==13
						   || index_to_number(suit_cs.get_Structure_Node_Start(0)+suit_cs.get_Structure_Node_Type(0)/2-1)==5)) {
				    System.out.println("Suggestion: type2 " + suit +
						       index_to_number(suit_cs.get_Structure_Node_Start(0)+suit_cs.get_Structure_Node_Type(0)/2-1));
				    pass = true;
				    suit_cs.change_Node(0,suit_cs.get_Structure_Node_Type(0)-2,suit_cs.get_Structure_Node_Start(0));
				    Collections.sort(suit_cs.get_Structure_List());
				}
				if (!pass) {
				    System.out.println("Suggestion: type2 " + suit + index_to_number(suit_cs.get_Structure_Node_Start(0)));
				    pass = true;
				    suit_cs.change_Node(0,suit_cs.get_Structure_Node_Type(0)-2,suit_cs.get_Structure_Node_Start(0)+1);
				    Collections.sort(suit_cs.get_Structure_List());
				}
			    }
			    else if (type==1) {
				if (suit_cs.get_Type_Counts(2)>0) {
				    while(index1 < suit_cs.size() && suit_cs.get_Structure_Node_Type(index1)>2) { index1++; }
				    while(index2 < suit_cs.size() && suit_cs.get_Structure_Node_Type(index2)>=2) { index2++; }
				    if (i<gameInfo.get_Current_Structure().size()-1) {
					for (int k=index2-1; k>=index1; k--) {
					    if (index_to_number(suit_cs.get_Structure_Node_Start(k))==10) {
						System.out.println("Suggestion: type1 " + suit + 10);
						pass = true;
						suit_cs.change_Node(k,1,suit_cs.get_Structure_Node_Start(k));
						Collections.sort(suit_cs.get_Structure_List());
						break;
					    }
					    if (index_to_number(suit_cs.get_Structure_Node_Start(k))==13) {
						System.out.println("Suggestion: type1 " + suit + 13);
						pass = true;
						suit_cs.change_Node(k,1,suit_cs.get_Structure_Node_Start(k));
						Collections.sort(suit_cs.get_Structure_List());
						break;
					    }
					}
					if (!pass) {
					    for (int k=index1; k<index2; k++) {
						if (index_to_number(suit_cs.get_Structure_Node_Start(k))==5) {
						    System.out.println("Suggestion: type1 " + suit + 5);
						    pass = true;
						    suit_cs.change_Node(k,1,suit_cs.get_Structure_Node_Start(k));
						    Collections.sort(suit_cs.get_Structure_List());
						    break;
						}
					    }
					}
					if (!pass) {
					    System.out.println("Suggestion: type1 " + suit + index_to_number(suit_cs.get_Structure_Node_Start(index2-1)));
					    pass = true;
					    suit_cs.change_Node(index2-1,1,suit_cs.get_Structure_Node_Start(index2-1));
					    Collections.sort(suit_cs.get_Structure_List());
					}
				    }
				    else {
					for (int k=index2-1; k>=index1; k--) {
					    if (index_to_number(suit_cs.get_Structure_Node_Start(k))!=10
						&& index_to_number(suit_cs.get_Structure_Node_Start(k))!=13
						&& index_to_number(suit_cs.get_Structure_Node_Start(k))!=5) {
						System.out.println("Suggestion: type1 " + suit + index_to_number(suit_cs.get_Structure_Node_Start(k)));
						pass = true;
						suit_cs.change_Node(k,1,suit_cs.get_Structure_Node_Start(k));
						Collections.sort(suit_cs.get_Structure_List());
						break;
					    }
					}
					if(!pass) {
					    System.out.println("Suggestion: type1 " + suit + index_to_number(suit_cs.get_Structure_Node_Start(index2-1)));
					    pass = true;
					    suit_cs.change_Node(index2-1,1,suit_cs.get_Structure_Node_Start(index2-1));
					    Collections.sort(suit_cs.get_Structure_List());
					}
				    }
				}
				else {
				    //////////////////****** Something added here *****/////////////////
				    int type0 = suit_cs.get_Structure_Node_Type(0);
				    int start0 = suit_cs.get_Structure_Node_Start(0);
				    suit_cs.get_Structure_List().remove(0);
				    for(int s=0; s<type0/2; s++) {
					suit_cs.add_Node(2,start0+s);
				    }
				    Collections.sort(suit_cs.get_Structure_List());
				    i--;
				}
			    }
			} 
		    }
		}
		else if ((winner_ID-playerID)%2!=0) {
		    if (gameInfo.get_Current_Structure().size()==1 && gameInfo.players[winner_ID].get_Play_Structure().get_Uniform_Suit()!=gameInfo.get_Key_Suit()) {
			/* Possible to win */
		        type = gameInfo.players[winner_ID].get_Play_Structure().get_Structure_Node_Type(0);
			start = gameInfo.players[winner_ID].get_Play_Structure().get_Structure_Node_Start(0);
			counts = suit_cs.get_Type_Counts(type);
			index1 = 0;
			index2 = 0;
			pass = false;

			if (type>=4) {
			    if(counts>0) {
				while(index1 < suit_cs.size() && suit_cs.get_Structure_Node_Type(index1)>type) { index1++; }
				while(index2 < suit_cs.size() && suit_cs.get_Structure_Node_Type(index2)>=type) { index2++; }
				suit_cs.get_Structure_List().remove(index2-1);
				System.out.println("Suggestion: type" + type + " " + suit + " start " + suit_cs.get_Structure_Node_Start(index2-1));
				pass = true;
			    }
			    if(counts==0 && suit_cs.get_Structure_Node_Type(0)<type) {
				gameInfo.get_Current_Structure().change_Node(0, type-2, start+1);
				gameInfo.get_Current_Structure().add_Node(2,start);
				Collections.sort(gameInfo.get_Current_Structure().get_Structure_List());
				pass = true;
				
			    }
			    if(counts==0 && suit_cs.get_Structure_Node_Type(0)>type) {
				suit_cs.change_Node(0,suit_cs.get_Structure_Node_Type(0)-type, suit_cs.get_Structure_Node_Start(0)+type/2);
				Collections.sort(suit_cs.get_Structure_List());
				pass = true;
			    }
			}

			
			
			if (!pass && counts>0) {
			    /* Has the possibility to win in this case, need to check key suit */ /* key suit case has been included in the previous one */
			    ///*********************** index1 to index2 is the range to win this round, it could be empty ***********************///
			    while(index1 < suit_cs.size() && suit_cs.get_Structure_Node_Type(index1)>type) { index1++; }
			    while(index2 < suit_cs.size() && (suit_cs.get_Structure_Node_Type(index2)>type ||
							      (suit_cs.get_Structure_Node_Type(index2)==type
							       && suit_cs.get_Structure_Node_Start(index2)>start))) { index2++; }

			    ///*********************** choose 10, 13, 5 first if it exists in range index1 to index2 ****************************///
			    for (int k=index2-1; k>=index1; k--) {
				if (index_to_number(suit_cs.get_Structure_Node_Start(k))==10) {
				    System.out.println("Suggestion: type" + type + " " + suit + 10);
				    pass = true;
				    suit_cs.get_Structure_List().remove(k);
				    break;
				}
				if (index_to_number(suit_cs.get_Structure_Node_Start(k))==13) {
				    System.out.println("Suggestion: type" + type + " " + suit + 13);
				    pass = true;
				    suit_cs.get_Structure_List().remove(k);
				    break;
				}
			    }
			    if (!pass) {
				for(int k=index1; k<index2; k++) {
				    if (index_to_number(suit_cs.get_Structure_Node_Start(k))==5) {
					System.out.println("Suggestion: type" + type + " " + suit + 5);
					pass = true;
					suit_cs.get_Structure_List().remove(k);
					break;
				    }
				}
			    }
			    /// ************************* if not pass && range is not empty, choose the smallest one in the range *****************///
			    if (!pass && index2-index1>0) {
				System.out.println("Suggestion: type" + type + " " + suit + index_to_number(suit_cs.get_Structure_Node_Start(index2-1)));
				suit_cs.get_Structure_List().remove(index2-1);
				pass = true;
			    }
			    /// ************************************  index1 to index2 is empty here **********************************************///
			    if (!pass) {
				while(index2 < suit_cs.size() && suit_cs.get_Structure_Node_Type(index2)>=type) { index2++; }
				System.out.println("Suggestion: type" + type + " " + suit + index_to_number(suit_cs.get_Structure_Node_Start(index2-1)));
				suit_cs.get_Structure_List().remove(index2-1);
			    }
 			}
			else if (!pass && suit_cs.get_Structure_Node_Type(0) < type) {
			    /* suit_cs.get_Structure_Node_Type(0)==1 */
			    /* Impossible to win in this case */
			    int target=0;
			    for(int k=suit_cs.size()-1; k>=0; k--) {
				if (target<2
				    && index_to_number(suit_cs.get_Structure_Node_Start(k))!=10
				    && index_to_number(suit_cs.get_Structure_Node_Start(k))!=13
				    && index_to_number(suit_cs.get_Structure_Node_Start(k))!=5) {
				    System.out.println("Suggestion: type1" + " " + suit + index_to_number(suit_cs.get_Structure_Node_Start(k)));
				    target++;
				    suit_cs.get_Structure_List().remove(k);
				}
			    }
			    //=============================== This part can be replaced with a previous ection =======================================//
			    if (target<2) {
				for(int k=0; k<suit_cs.size(); k++) {
				    if (index_to_number(suit_cs.get_Structure_Node_Start(k))==5) {
					System.out.println("Suggestion: type1" + " " + suit + 5);
					pass = true;
					target++;
					suit_cs.get_Structure_List().remove(k);
					break;
				    }
				}
			    }
			    if (target<2) {
				for(int k=0; k<suit_cs.size(); k++) {
				    if (index_to_number(suit_cs.get_Structure_Node_Start(k))==10) {
					System.out.println("Suggestion: type1" + " " + suit + 10);
					pass = true;
					target++;
					suit_cs.get_Structure_List().remove(k);
					break;
				    }
				}
			    }
			    if (target<2) {
				for(int k=0; k<suit_cs.size(); k++) {
				    if (index_to_number(suit_cs.get_Structure_Node_Start(k))==13) {
					System.out.println("Suggestion: type1" + " " + suit + 13);
					pass = true;
					target++;
					suit_cs.get_Structure_List().remove(k);
					break;
				    }
				}
			    }
			    //========================================================================================================================//
			}
			else if (!pass && suit_cs.get_Structure_Node_Type(0) > type) {
			    if (type==2) {
				if (index_to_number(suit_cs.get_Structure_Node_Start(0))!=10
				   && index_to_number(suit_cs.get_Structure_Node_Start(0))!=13
				   && index_to_number(suit_cs.get_Structure_Node_Start(0))!=5) {
				    System.out.println("Suggestion: type2 " + suit + index_to_number(suit_cs.get_Structure_Node_Start(0)));
				    suit_cs.change_Node(0,suit_cs.get_Structure_Node_Type(0)-2,suit_cs.get_Structure_Node_Start(0)+1);
				    pass = true;
				    Collections.sort(suit_cs.get_Structure_List());
				}
				if (!pass && (index_to_number(suit_cs.get_Structure_Node_Start(0)+suit_cs.get_Structure_Node_Type(0)/2-1)!=10
					      && index_to_number(suit_cs.get_Structure_Node_Start(0)+suit_cs.get_Structure_Node_Type(0)/2-1)!=13
					      && index_to_number(suit_cs.get_Structure_Node_Start(0)+suit_cs.get_Structure_Node_Type(0)/2-1)!=5)) {
				    System.out.println("Suggestion: type2 " + suit +
						       index_to_number(suit_cs.get_Structure_Node_Start(0)+suit_cs.get_Structure_Node_Type(0)/2-1));
				    pass = true;
				    suit_cs.change_Node(0,suit_cs.get_Structure_Node_Type(0)-2,suit_cs.get_Structure_Node_Start(0));
				    Collections.sort(suit_cs.get_Structure_List());
				}
			    }
			    else if (type==1) {
				if (suit_cs.get_Type_Counts(2)==0) {
				    //////////////////****** Something added here *****/////////////////
				    int type0 = suit_cs.get_Structure_Node_Type(0);
				    int start0 = suit_cs.get_Structure_Node_Start(0);
				    suit_cs.get_Structure_List().remove(0);
				    for(int s=0; s<type0/2; s++) {
					suit_cs.add_Node(2,start0+s);
				    }
				    Collections.sort(suit_cs.get_Structure_List());
				}
				if (suit_cs.get_Type_Counts(2)>0) {
				    while(index1 < suit_cs.size() && suit_cs.get_Structure_Node_Type(index1)>2) { index1++; }
				    while(index2 < suit_cs.size() && suit_cs.get_Structure_Node_Type(index2)>=2) { index2++; }
			        
				    for (int k=index2-1; k>=index1; k--) {
					if (index_to_number(suit_cs.get_Structure_Node_Start(k))!=10
					    && index_to_number(suit_cs.get_Structure_Node_Start(k))!=13
					    && index_to_number(suit_cs.get_Structure_Node_Start(k))!=5) {
					    System.out.println("Suggestion: type1 " + suit + index_to_number(suit_cs.get_Structure_Node_Start(k)));
					    pass = true;
					    suit_cs.change_Node(k,1,suit_cs.get_Structure_Node_Start(k));
					    Collections.sort(suit_cs.get_Structure_List());
					    break;
					}
				    }
				    if (!pass) {
					System.out.println("Suggestion: type1 " + suit + index_to_number(suit_cs.get_Structure_Node_Start(index2-1)));
					pass = true;
					suit_cs.change_Node(index2-1,1,suit_cs.get_Structure_Node_Start(index2-1));
					Collections.sort(suit_cs.get_Structure_List());
				    }
				}
		        	
			    }
			} 
			
		    }

		    if(gameInfo.get_Current_Structure().size()>1 || gameInfo.players[winner_ID].get_Play_Structure().get_Uniform_Suit()==gameInfo.get_Key_Suit()) {
			/* Impossible to win in this case */
			for(int i=0; i<gameInfo.get_Current_Structure().size(); i++) {
			    pass = false;
			    type = gameInfo.get_Current_Structure().get_Structure_Node_Type(i);
			    start = gameInfo.get_Current_Structure().get_Structure_Node_Start(i);
			    counts = suit_cs.get_Type_Counts(type);
			    index1 = 0;
			    index2 = 0;
			    
			    if (type>=4) {
				if(counts>0) {
				    while(index1 < suit_cs.size() && suit_cs.get_Structure_Node_Type(index1)>type) { index1++; }
				    while(index2 < suit_cs.size() && suit_cs.get_Structure_Node_Type(index2)>=type) { index2++; }
				    suit_cs.get_Structure_List().remove(index2-1);
				    System.out.println("Suggestion: type" + type + " " + suit + " start " + suit_cs.get_Structure_Node_Start(index2-1));
				    continue;
				}
				if(counts==0 && suit_cs.get_Structure_Node_Type(0)<type) {
				    System.out.println("Here1");
				    gameInfo.get_Current_Structure().change_Node(i, type-2, start+1);
				    gameInfo.get_Current_Structure().add_Node(2,start);
				    Collections.sort(gameInfo.get_Current_Structure().get_Structure_List());
				    i--;
				    continue;
				}
				if(counts==0 && suit_cs.get_Structure_Node_Type(0)>type) {
				    suit_cs.change_Node(0,suit_cs.get_Structure_Node_Type(0)-type, suit_cs.get_Structure_Node_Start(0)+type/2);
				    Collections.sort(suit_cs.get_Structure_List());
				    continue;
				}
			    }
			    
			    if (counts>0) {
				while(index1 < suit_cs.size() && suit_cs.get_Structure_Node_Type(index1)>type) { index1++; }
				while(index2 < suit_cs.size() && suit_cs.get_Structure_Node_Type(index2)>=type) { index2++; }
				/// ********************** Find one card node which is not 10, 13 or 5 ******************************///
				for (int k=index2-1; k>=index1; k--) {
				    if (index_to_number(suit_cs.get_Structure_Node_Start(k))!=10
					&& index_to_number(suit_cs.get_Structure_Node_Start(k))!=13
					&& index_to_number(suit_cs.get_Structure_Node_Start(k))!=5) {
					System.out.println("Suggestion: type" + type + " " + suit + index_to_number(suit_cs.get_Structure_Node_Start(k)));
					pass = true;
					suit_cs.get_Structure_List().remove(k);
					break;
				    }
				}
				/// ***********************************************************************************************///
				if (!pass) {
				System.out.println("Suggestion: type" + type + " " + suit + index_to_number(suit_cs.get_Structure_Node_Start(index2-1)));
				suit_cs.get_Structure_List().remove(index2-1);
				}
				
			    }
			    
			    else if (suit_cs.get_Structure_Node_Type(0) < type) {
				int target=0;
				/// ********************** Find two cards which are not 10, 13 or 5 ********************************///
				for(int k=suit_cs.size()-1; k>=0; k--) {
				    if (index_to_number(suit_cs.get_Structure_Node_Start(k))!=10
					&& index_to_number(suit_cs.get_Structure_Node_Start(k))!=13
					&& index_to_number(suit_cs.get_Structure_Node_Start(k))!=5) {
					System.out.println("Suggestion: type1" + " " + suit + index_to_number(suit_cs.get_Structure_Node_Start(k)));
					pass = true;
					target++;
					suit_cs.get_Structure_List().remove(k);
					if(target==2) break;
				    }
				}
				/// ***********************************************************************************************///
				if (target<2) {
				    System.out.println("Suggestion: type1" + " " + suit + index_to_number(suit_cs.get_Structure_Node_Start(suit_cs.size()-1)));
				    target++;
				    suit_cs.get_Structure_List().remove(suit_cs.size()-1);
				}
				if (target<2) {
				    System.out.println("Suggestion: type1"  + " " + suit + index_to_number(suit_cs.get_Structure_Node_Start(suit_cs.size()-1)));
				    target++;
				    suit_cs.get_Structure_List().remove(suit_cs.size()-1);
				}
			    }
			    else if (suit_cs.get_Structure_Node_Type(0) > type) {
				if (type==2) {
				    if (index_to_number(suit_cs.get_Structure_Node_Start(0))!=10
					&& index_to_number(suit_cs.get_Structure_Node_Start(0))!=13
					&& index_to_number(suit_cs.get_Structure_Node_Start(0))!=5) {
					System.out.println("Suggestion: type2 " + suit + index_to_number(suit_cs.get_Structure_Node_Start(0)));
					suit_cs.change_Node(0,suit_cs.get_Structure_Node_Type(0)-2,suit_cs.get_Structure_Node_Start(0)+1);
					pass = true;
					Collections.sort(suit_cs.get_Structure_List());
				    }
				    if (!pass && (index_to_number(suit_cs.get_Structure_Node_Start(0)+suit_cs.get_Structure_Node_Type(0)/2-1)!=10
						  && index_to_number(suit_cs.get_Structure_Node_Start(0)+suit_cs.get_Structure_Node_Type(0)/2-1)!=13
						  && index_to_number(suit_cs.get_Structure_Node_Start(0)+suit_cs.get_Structure_Node_Type(0)/2-1)!=5)) {
					System.out.println("Suggestion: type2 " + suit +
							   index_to_number(suit_cs.get_Structure_Node_Start(0)+suit_cs.get_Structure_Node_Type(0)/2-1));
					pass = true;
					suit_cs.change_Node(0,suit_cs.get_Structure_Node_Type(0)-2,suit_cs.get_Structure_Node_Start(0));
					Collections.sort(suit_cs.get_Structure_List());
				    }
				}
				else if (type==1) {
				    if (suit_cs.get_Type_Counts(2)>0) {
					while(index1 < suit_cs.size() && suit_cs.get_Structure_Node_Type(index1)>2) { index1++; }
					while(index2 < suit_cs.size() && suit_cs.get_Structure_Node_Type(index2)>=2) { index2++; }
			       
					for (int k=index2-1; k>=index1; k--) {
					    if (index_to_number(suit_cs.get_Structure_Node_Start(k))!=10
						&& index_to_number(suit_cs.get_Structure_Node_Start(k))!=13
						&& index_to_number(suit_cs.get_Structure_Node_Start(k))!=5) {
						System.out.println("Suggestion: type1 " + suit + index_to_number(suit_cs.get_Structure_Node_Start(k)));
						pass = true;
						suit_cs.change_Node(k,1,suit_cs.get_Structure_Node_Start(k));
						Collections.sort(suit_cs.get_Structure_List());
						break;
					    }
					}
					if (!pass) {
					    System.out.println("Suggestion: type1 " + suit + index_to_number(suit_cs.get_Structure_Node_Start(index2-1)));
					    pass = true;
					    suit_cs.change_Node(index2-1,1,suit_cs.get_Structure_Node_Start(index2-1));
					    Collections.sort(suit_cs.get_Structure_List());
					}
				    }
				    else {
					//////////////////****** Something added here *****/////////////////
					int type0 = suit_cs.get_Structure_Node_Type(0);
					int start0 = suit_cs.get_Structure_Node_Start(0);
					suit_cs.get_Structure_List().remove(0);
					for(int s=0; s<type0/2; s++) {
					    suit_cs.add_Node(2,start0+s);
					}
					Collections.sort(suit_cs.get_Structure_List());
					i--;
				    }
				}
			    } 
			}
		    }
		}
	    }
	    gameInfo.get_Current_Structure().generateStructure();
	}
    }

    public int index_to_number(int index) {
	if (index+3 > gameInfo.get_Key_Number())
	    return index + 3;
	else
	    return index + 2;
    }
    public int number_to_index(int number) {
	if (number > gameInfo.get_Key_Number())
	    return number - 3;
	else
	    return number - 2;
    }
    
}
