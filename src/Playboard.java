import java.io.*;

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
    public static void get_Table_card() {
	gameInfo.update_IronThrone();
	players[gameInfo.ironThrone].player_get_Table_card();
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
	players[3] = new HumanPlayer(gameInfo, 3, shuffleGenerator);


	
	distribute_Cards();
	get_Table_card();
	
	gameInfo.printOutGameInfo();
	for(int p=0;p<NPlayer;p++) {
	    players[p].sortCard();
	}


	
	/*
	for(int p=0;p<NPlayer;p++) {
	    players[p].remove(Card.Suit.SPADE, 14);
	    players[p].printOutCard_in_Order();
	}
	*/

	while(!players[0].playCards(true));
	players[0].printOutCard_in_Order();
	
	while(!players[1].playCards(false));
	players[1].printOutCard_in_Order();

	while(!players[2].playCards(false));
	players[2].printOutCard_in_Order();

	while(!players[3].playCards(false));
	players[3].printOutCard_in_Order();
	/*

	Card card1 = new Card(Card.Suit.SPADE,11);
	Card card2 = new Card(Card.Suit.SPADE,11);
	Card card3 = new Card(Card.Suit.SPADE,14);
	Card card4 = new Card(Card.Suit.SPADE,14);
	Card card5 = new Card(Card.Suit.SPADE,12);
	Card card11 = new Card(Card.Suit.SPADE,3);
	
	Card card6 = new Card(Card.Suit.HEART,3);
	Card card7 = new Card(Card.Suit.HEART,3);
	Card card8 = new Card(Card.Suit.HEART,4);
	Card card9 = new Card(Card.Suit.HEART,4);
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
	
	CardStructure cs1 = new CardStructure(gameInfo,card1,card2,card3,card4,card5,card11);
	CardStructure cs2 = new CardStructure(gameInfo,card6,card7,card8,card9,card10,card12);
	StructureComparator sc = new StructureComparator(gameInfo);

	cs1.printTable();
	
	if (sc.compare(cs1,cs2) > 0) {
	    System.out.println(">");
	}
	else {
	    System.out.println("<");
	}
	
	cs2.printTable();
	
	*/
    }
}
