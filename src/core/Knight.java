package core;

public class Knight extends Piece {
	
	public Knight(int x, int y, Color color) {
		super(x, y, color);
	}
	
	public boolean isKnight() { return true; }
	
	public boolean checkMove(Piece pieces[][], int x, int y)
	{
		if(!super.checkMove(pieces, x, y))
			return false;
		
		Piece otherPiece = pieces[y][x];
		if(otherPiece != null && otherPiece.getColor() == m_color)
			return false;
		
		if(Math.abs(m_x - x) == 2 && Math.abs(m_y - y) == 1)
			return true;
		if(Math.abs(m_x - x) == 1 && Math.abs(m_y - y) == 2)
			return true;
		
		return false;
	}
}
