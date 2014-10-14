package core;

public class Rook extends Piece {
	
	public Rook(int x, int y, Color color) {
		super(x, y, color);
	}
	
	public boolean isRook() { return true; }
	
	public boolean checkMove(Piece m_pieces[][], int x, int y)
	{
		if(x < 0 || x >= 8 || y < 0 || y >= 8) {
			return false;
		}
		if(getX() == x && getY() != y || getY() == y && getX() != x) {
			return true;
		}
		return false;
	}
}
