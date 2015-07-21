import java.util.ArrayList;
import java.util.Random;

public class Shuffle {

    private int Npackage;
    
    Card[] allCards;

    /** 
     * Constructor;
     * Generate all the cards in order;
     * Shuffle them to a random order;
     * In debug mode, the shuffled cards will be printed out.
     */
    public Shuffle(GameInfo gameInfo) {
	this.Npackage = gameInfo.NPackage;
	allCards =  new Card[Npackage*GameInfo.CARD_IN_EACH_PACKAGE];
	for(int i=0;i<Npackage;i++) {
	    for(int t=0;t<GameInfo.SUIT_NUMBER;t++) {
		for(int n=0;n<GameInfo.CARD_IN_EACH_SUIT;n++) {
		    allCards[i*GameInfo.CARD_IN_EACH_PACKAGE+t*GameInfo.CARD_IN_EACH_SUIT + n]
			= new Card(Card.Suit.values()[t],n+2);
		   
		    }
		}
	  
	    allCards[i*GameInfo.CARD_IN_EACH_PACKAGE+GameInfo.SUIT_NUMBER*GameInfo.CARD_IN_EACH_SUIT]
		= new Card(Card.Suit.values()[4],0);
	   
	    allCards[i*GameInfo.CARD_IN_EACH_PACKAGE+GameInfo.SUIT_NUMBER*GameInfo.CARD_IN_EACH_SUIT + 1]
		= new Card(Card.Suit.values()[5],0);
	    
	}

	/* Shuffle Process */
	if(gameInfo.DEBUG) {
	    System.out.println("**************************************************");
	    System.out.println("**************************************************");
	}
	Card tempCard = new Card();
	int tempIndex;
	Random randomGenerator = new Random();
	for(int i=0;i<Npackage*GameInfo.CARD_IN_EACH_PACKAGE;i++) {
	    tempCard = allCards[i];
	    tempIndex =
		(randomGenerator.nextInt()%(Npackage*GameInfo.CARD_IN_EACH_PACKAGE-i)+Npackage*GameInfo.CARD_IN_EACH_PACKAGE-i)%(Npackage*GameInfo.CARD_IN_EACH_PACKAGE-i);
	    allCards[i] = allCards[i+tempIndex];
	    allCards[i+tempIndex] = tempCard;
	
	    if(gameInfo.DEBUG) {
		System.out.print(allCards[i].getSuit());
		System.out.println(allCards[i].getNumber());
	    }
	}
	if(gameInfo.DEBUG) {
	    System.out.println("**************************************************");
	    System.out.println("**************************************************");
	}
    }
}
