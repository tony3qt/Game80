import java.io.*;

public class Playboard {

    public static void main(String[] args) {
	int NPlayer = 4;
	int NPackage = 2;
	int keyNumber = 2;
	GameInfo gameInfo = new GameInfo(NPlayer,NPackage,keyNumber);
	Shuffle shuffleGenerator = new Shuffle(gameInfo);

	ComputerPlayer[] players;
	players = new ComputerPlayer[4]; 
	for(int p=0;p<NPlayer;p++) {
	    players[p] = new ComputerPlayer(gameInfo,p,shuffleGenerator);
	}
	for(int i=0;i<25;i++) {
	    for(int p=0;p<NPlayer;p++) {
		players[p].getOneCard();
	    }
	    System.out.println();
	    
	}
	gameInfo.printOutGameInfo();
	for(int p=0;p<NPlayer;p++) {
	    players[p].sortCard();
	}
    }
}
