import java.util.ArrayList;
import java.util.Random;

public class Shuffle {

    private static final int CARD_IN_EACH_TYPE = 13;
    private static final int CARD_IN_EACH_PACKAGE = 54;
    private static final int TYPE_NUM = 4;
    private static final boolean DEBUG = true;
    
    Card[] allCards;
    
    public Shuffle(int Npackage, int Nplayer) {
	allCards =  new Card[Npackage*CARD_IN_EACH_PACKAGE];
	for(int i=0;i<Npackage;i++) {
	    for(int t=0;t<TYPE_NUM;t++) {
		for(int n=0;n<CARD_IN_EACH_TYPE;n++) {
		    allCards[i*CARD_IN_EACH_PACKAGE+t*CARD_IN_EACH_TYPE + n]
			= new Card(Card.Suit.values()[t],n+2);
		   
		    }
		}
	    }
	    allCards[i*CARD_IN_EACH_PACKAGE+TYPE_NUM*CARD_IN_EACH_TYPE]
		= new Card(Card.Suit.values()[4],1);
	   
	    allCards[i*CARD_IN_EACH_PACKAGE+TYPE_NUM*CARD_IN_EACH_TYPE + 1]
		= new Card(Card.Suit.values()[4],2);
	    
	}

	if(DEBUG) {
	    System.out.println("**************************************************");
	    System.out.println("**************************************************");
	}
	Card _tempCard = new Card();
	int _tempIndex;
	Random randomGenerator = new Random();
	for(int i=0;i<Npackage*CARD_IN_EACH_PACKAGE;i++) {
	    _tempCard = allCards[i];
	    _tempIndex = (randomGenerator.nextInt()%(Npackage*CARD_IN_EACH_PACKAGE-i)+Npackage*CARD_IN_EACH_PACKAGE-i)%(Npackage*CARD_IN_EACH_PACKAGE-i);
	    allCards[i] = allCards[i+_tempIndex];
	    allCards[i+_tempIndex] = _tempCard;
	
	    if(DEBUG) {
		System.out.print(allCards[i].getType());
		System.out.println(allCards[i].getNumber());
	    }
	}
    }
}
