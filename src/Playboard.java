import java.io.*;
import java.util.ArrayList;

public class Playboard {

    public static Player[] players;
    public static GameInfo gameInfo;
    public static Shuffle shuffleGenerator;


    public static void shuffle_Cards() {
	shuffleGenerator = new Shuffle(gameInfo);
    }
    
    public static void distribute_Cards() {
	
	for(int i=0;i<25;i++) {
	    for(int p=0;p<gameInfo.NPlayer;p++) {
		players[p].getOneCard();
	    }
	    if(gameInfo.DEBUG) {
		System.out.println();
	    }
	    
	}
    }
    public static void get_Table_Card() {
	gameInfo.update_IronThrone();
	players[gameInfo.get_IronThrone()].player_get_Table_Card();
    }

    public static void set_Table_Card() {
	while(!players[gameInfo.get_IronThrone()].player_set_Table_Card()) {}
	players[gameInfo.get_IronThrone()].printOutCard_in_Order();
	
    }
    
    public static void main(String[] args) {

	boolean debug = false;
	for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-debug")) {
		debug = true;
	    }
	}
	int NPlayer = 4;
	int NPackage = 2;
	int keyNumber = 2;
	gameInfo = new GameInfo(NPlayer,NPackage,keyNumber,debug);
	players = new Player[NPlayer];
	

	shuffle_Cards();

	
	for(int p=0;p<gameInfo.NPlayer;p++) {
	    players[p] = new HumanPlayer(gameInfo, p, shuffleGenerator);
	}

	
	distribute_Cards();
	get_Table_Card();
	
	
	gameInfo.printOutGameInfo();
	for(int p=0;p<NPlayer;p++) {
	    players[p].sortCard();
	}
	set_Table_Card();

	int starter_ID = gameInfo.get_IronThrone();
	int max_ID;

	for (int i=0;i<=1;i++) {

	    gameInfo.clear_Current_Scores();
	    
	    while(!players[starter_ID].playCards(true));
	    players[starter_ID].printOutCard_in_Order();
	    
	    while(!players[(starter_ID+1)%4].playCards(false));
	    players[(starter_ID+1)%4].printOutCard_in_Order();
	    
	    if (CardStructure.compare(players[starter_ID].get_Play_Structure(), players[(starter_ID+1)%4].get_Play_Structure(), gameInfo) > 0) {
		max_ID = (starter_ID + 1)%4; System.out.println(max_ID); }
	    else { max_ID = (starter_ID); System.out.println(max_ID); }
	    
	    while(!players[(starter_ID+2)%4].playCards(false));
	    players[(starter_ID+2)%4].printOutCard_in_Order();
	    
	    if (CardStructure.compare(players[max_ID].get_Play_Structure(),
					    players[(starter_ID+2)%4].get_Play_Structure(), gameInfo.get_Current_Structure(), gameInfo) > 0) {
	    max_ID = (starter_ID + 2)%4; System.out.println(max_ID); 
	    }
	    
	    while(!players[(starter_ID+3)%4].playCards(false));
	    players[(starter_ID+3)%4].printOutCard_in_Order();
	    
	    if (CardStructure.compare(players[max_ID].get_Play_Structure(),
					    players[(starter_ID+3)%4].get_Play_Structure(), gameInfo.get_Current_Structure(), gameInfo) > 0) {
		max_ID = (starter_ID + 3)%4; System.out.println(max_ID); 
	    }

	    if (max_ID == (gameInfo.get_IronThrone()+1)%4 || max_ID == (gameInfo.get_IronThrone()+3)%4 ) {
		gameInfo.update_Total_Scores();
	    }
	    starter_ID = max_ID;
	    System.out.println("Scores : " + gameInfo.get_Total_Scores());
	}
	
	Card card1 = new Card(Card.Suit.SPADE,2);
	Card card2 = new Card(Card.Suit.SPADE,2);
	Card card3 = new Card(Card.Suit.CLUB,2);
	Card card4 = new Card(Card.Suit.CLUB,2);
	Card card5 = new Card(Card.Suit.SPADE,12);
	Card card11 = new Card(Card.Suit.SPADE,3);
	
	Card card6 = new Card(Card.Suit.HEART,2);
	Card card7 = new Card(Card.Suit.HEART,2);
	Card card8 = new Card(Card.Suit.L_JOKER,0);
	Card card9 = new Card(Card.Suit.L_JOKER,0);
	Card card10 = new Card(Card.Suit.HEART,5);
	Card card12 = new Card(Card.Suit.HEART,5);
	
	card1.setKey(gameInfo);
	card2.setKey(gameInfo);
	card3.setKey(gameInfo);
	card4.setKey(gameInfo);
	card5.setKey(gameInfo);
	card6.setKey(gameInfo);
	card7.setKey(gameInfo);
	card8.setKey(gameInfo);
	card9.setKey(gameInfo);
	card10.setKey(gameInfo);
	card11.setKey(gameInfo);
	card12.setKey(gameInfo);

	ArrayList<Card> a1 = new ArrayList<Card>();
	ArrayList<Card> a2 = new ArrayList<Card>();
	a1.add(card1);
	a1.add(card2);
	//a1.add(card3);
	//a1.add(card4);
	//a2.add(card6);
	//a2.add(card7);
	a2.add(card8);
	a2.add(card9);
	
	CardStructure cs1 = new CardStructure(gameInfo,a1);
	CardStructure cs2 = new CardStructure(gameInfo,a2);
	cs2.printTable();
	cs1.printTable();
	
	if (CardStructure.compare(cs1,cs2,gameInfo) > 0) {
	    System.out.println("<");
	}
	else if (CardStructure.compare(cs1,cs2,gameInfo) < 0) {
	    System.out.println(">");
	}
	else
	    System.out.println("=");
	
	
	cs2.printTable();
	
	
    }
}
