package core;

public class Bishop extends Piece {
	
	public Bishop(int x, int y, Color color) {
		super(x, y, color);
	}

	public boolean isBishop() { return true; }
	public boolean checkMove(Piece m_pieces[][], int x, int y) {
		return true;
	}
}
