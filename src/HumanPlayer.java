import java.util.Scanner;

public class HumanPlayer extends Player {

    public HumanPlayer(GameInfo gameInfo, int playerID, Shuffle shuffleGenerator) {
	super(gameInfo, playerID, shuffleGenerator);
    }

    @Override
    public void getOneCard() {

	Scanner scan= new Scanner(System.in);
	scan.useDelimiter("\\n");
	System.out.print("Player ID = " + playerID + " : ");
	String command = scan.nextLine();

	while (!command.equals("")) {
	    
	    if (command.equals("s") && keyNumber_Counts[0] > 0 ) {
		gameInfo.updateKeySuit( Card.Suit.SPADE, 1, playerID);
	    }
	    else if (command.equals("ss") && keyNumber_Counts[0] == 2 ) {
		gameInfo.updateKeySuit( Card.Suit.SPADE, 2, playerID);
	    }
	    else if (command.equals("h") && keyNumber_Counts[1] > 0  ) {
		gameInfo.updateKeySuit( Card.Suit.HEART, 1, playerID);
	    }
	    else if (command.equals("hh") && keyNumber_Counts[1] == 2  ) {
		gameInfo.updateKeySuit( Card.Suit.HEART, 2, playerID);
	    }
	    else if (command.equals("d") && keyNumber_Counts[2] > 0  ) {
		gameInfo.updateKeySuit( Card.Suit.DIAMOND, 1, playerID);
	    }
	    else if (command.equals("dd") && keyNumber_Counts[2] == 2  ) {
		gameInfo.updateKeySuit( Card.Suit.DIAMOND, 2, playerID);
	    }
	    else if (command.equals("c") && keyNumber_Counts[3] > 0  ) {
		gameInfo.updateKeySuit( Card.Suit.CLUB, 1, playerID);
	    }
	    else if (command.equals("cc") && keyNumber_Counts[3] == 2  ) {
		gameInfo.updateKeySuit( Card.Suit.CLUB, 2, playerID);
	    }
	    else if (command.equals("q") && L_Joker_Counts ==2  ) {
		gameInfo.updateKeySuit( Card.Suit.L_JOKER, 3, playerID);
	    }
	    else if (command.equals("k") && H_Joker_Counts == 2  ) {
		gameInfo.updateKeySuit( Card.Suit.H_JOKER, 4, playerID);
	    }
	    else {
		System.out.println("Invalid input");
	    }
	    command = scan.nextLine();
	}
	
	    
	cardpack[current_Card_Number] =
	    shuffleGenerator.allCards[current_Card_Number*NPlayer + playerID];
	System.out.print("\t\t");
	cardpack[current_Card_Number].printOutCard();
	System.out.println();
	
	/* Record the key Card */
	if (cardpack[current_Card_Number].getNumber() == gameInfo.key_Number) {
	    keyNumber_Counts[cardpack[current_Card_Number].getSuit().getValue()] ++;
	}
	else if (cardpack[current_Card_Number].getSuit() == Card.Suit.L_JOKER) {
	    L_Joker_Counts ++;
	}
	else if (cardpack[current_Card_Number].getSuit() == Card.Suit.H_JOKER) {
	    H_Joker_Counts ++;
	}
	current_Card_Number ++;
    }


    @Override
    public void playCards(boolean starter) {
	Scanner scan= new Scanner(System.in);
	scan.useDelimiter("\\n");
	System.out.print("Player ID = " + playerID + " : ");
	String command = scan.nextLine();
	for(int i=0; i<command.length(); i=i+2) {
	    
	}
    }
    
}
