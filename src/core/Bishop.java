package core;

public class Bishop extends Piece {
	
	public Bishop(int x, int y, Color color) {
		super(x, y, color);
	}

	public boolean isBishop() { return true; }
	public boolean checkMove(Piece pieces[][], int x, int y) {
		if(!super.checkMove(pieces, x, y))
			return false;
		
		Piece otherPiece = pieces[y][x];
		if(otherPiece != null && otherPiece.getColor() == m_color)
			return false;
		
		if(Math.abs(getX()-x) != Math.abs(getY()-y))
			return false;

		return true;
	}
}
