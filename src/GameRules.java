
public class GameRules {

    GameInfo gameInfo;
    
    public GameRules(GameInfo gameInfo) {
	this.gameInfo = gameInfo;
    }

    public boolean test(Player player, Card... cards) {

	return true;
    }
    
    public boolean test(Card.Suit suit, Player player, Card... cards) {
	return true;
    }
}
