package core;

public class Knight extends Piece {
	
	public Knight(int x, int y, Color color) {
		super(x, y, color);
	}
	
	public boolean isKnight() { return true; }
	
	public boolean checkMove(Piece m_pieces[][], int x, int y)
	{
		if(x < 0 || x >= 8 || y < 0 || y >= 8) {
			return false;
		}
		
		if(getX()+2 == x && (getY()+1 == y || getY()-1 == y)) { //direita 1
			return true;
		}
		if(getX()+1 == x && (getY()+2 == y || getY()-2 == y)) { //direita 2
			return true;
		}
		if(getX()-2 == x && (getY()+1 == y || getY()-1 == y)) { //esquerda 1
			return true;
		}
		if(getX()-1 == x && (getY()+2 == y || getY()-2 == y)) { //esquerda 2
			return true;
		}
		
		return false;
	}
}
