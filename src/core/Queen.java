package core;

public class Queen extends Piece {
	
	public Queen(int x, int y, Color color) {
		super(x, y, color);
	}
	
	public boolean isQueen() { return true; }
	
	public boolean checkMove(Piece pieces[][], int x, int y)
	{
		if(!super.checkMove(pieces, x, y))
			return false;
		
		Piece otherPiece = pieces[y][x];
		if(otherPiece != null && otherPiece.getColor() == m_color)
			return false;

		if((getX() == x && getY() != y) || (getY() == y && getX() != x))
			return true;

		if(Math.abs(getX()-x) == Math.abs(getY()-y))
			return true;
		
		return false;
	}
}
