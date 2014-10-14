package core;

public class Queen extends Piece {
	
	public Queen(int x, int y, Color color) {
		super(x, y, color);
	}
	
	public boolean isQueen() { return true; }
	
	public boolean checkMove(Piece m_pieces[][], int x, int y)
	{
		if(x < 0 || x >= 8 || y > 0 || y >= 8) {
			return false;
		}
		if(getX() == x && getY() != y || getY() == y && getX() != x) {
			return true;
		}
		if(getX()==x||getY()==y)
		{
			return false;
		}
		if(Math.abs(getX()-x)==Math.abs(getY()-y))
		{
		return true;
		}
		return false;
	}
}
