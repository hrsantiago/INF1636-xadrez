package core;

public class Knight extends Piece {
	
	public Knight(int x, int y, Color color) {
		super(x, y, color);
	}
	
	public boolean isKnight() { return true; }
	
	public void checkMove(Piece pieces[][], int x, int y) throws MoveException
	{
		super.checkMove(pieces, x, y);
		
		Piece otherPiece = pieces[y][x];
		if(otherPiece != null && otherPiece.getColor() == m_color)
			throw new MoveException("Invalid move");
		
		if(Math.abs(m_x - x) == 2 && Math.abs(m_y - y) == 1)
			return;
		if(Math.abs(m_x - x) == 1 && Math.abs(m_y - y) == 2)
			return;
		
		throw new MoveException("Invalid move");
	}
}
