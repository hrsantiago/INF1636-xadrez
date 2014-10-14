package core;

public class Bishop extends Piece {
	
	public Bishop(int x, int y, Color color) {
		super(x, y, color);
	}

	public boolean isBishop() { return true; }
	public boolean checkMove(Piece m_pieces[][], int x, int y) {
		//TODO: fazer a checagem de movimento do bispo
		
		if(x < 0 || x >= 8 || y > 0 || y >= 8) {
			return false;
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
