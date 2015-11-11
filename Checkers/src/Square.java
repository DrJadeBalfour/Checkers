
public class Square {

	private int Player; 
	private boolean King;
	
	public Square() {
		setPlayer(0);
		setKing(false);
	}

	public int getPlayer() {
		return Player;
	}

	public void setPlayer(int player) {
		Player = player;
	}

	public boolean isKing() {
		return King;
	}

	public void setKing(boolean king) {
		King = king;
	}
}