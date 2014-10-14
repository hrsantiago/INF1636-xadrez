package core;

public class Queen extends Piece {
	
	public Queen(int x, int y, Color color) {
		super(x, y, color);
	}
	
	public boolean isQueen() { return true; }
	
	public boolean checkMove(Piece m_pieces[][], int x, int y)
	{
		//TODO
		return true;
	}
}
