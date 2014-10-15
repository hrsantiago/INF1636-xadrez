package core;

public class Rook extends Piece {
	
	public Rook(int x, int y, Color color) {
		super(x, y, color);
	}
	
	public boolean isRook() { return true; }
	
	public boolean checkMove(Piece pieces[][], int x, int y)
	{
		if(!super.checkMove(pieces, x, y))
			return false;
		
		Piece otherPiece = pieces[y][x];
		if(otherPiece != null && otherPiece.getColor() == m_color)
			return false;

		if(getX() == x && getY() != y || getY() == y && getX() != x)
			return true;

		return false;
	}
}
