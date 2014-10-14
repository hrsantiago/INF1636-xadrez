package core;

public class King extends Piece {
	
	public King(int x, int y, Color color) {
		super(x, y, color);
	}
	
	public boolean isKing() { return true; }
	
	public boolean checkMove(Piece m_pieces[][],int x,int y) {
		if(x < 0 || x >= 8 || y > 0 || y >= 8) {
			return false;
		}
		if(getX()+1 < x || getX()-1 > x) {
			return false;
		}
		if(getY()+1 < y || getY()-1 > y) {
			return false;
		}
		
		if(getX() == x && (getY()+1 < y || getY()-1 > y)) { //Ultrapassa Lados?
			return false;
		}
		if(getY() == y && (getX()+1 < x || getX()-1 > x)) { //Ultrapassa  Cima e baixo?
			return false;
		}
		if(getX()+1 == y && (getY()+1 < y || getY()-1 > y)) { //Ultrapassa inclinados direita?
			return false;
		}
		if(getX()-1 == y && (getY()+1 < y || getY()-1 > y)) { //Ultrapassa inclinados esquerda?
			return false;
		}

		return true;
	}
}
