package core;

public class Pawn extends Piece {
	
	boolean m_first = true;
	
	public Pawn(int x, int y, Color color) {
		super(x, y, color);
	}
	
	public boolean isPromotion() {
		if(getColor() == Color.BLACK && getX() == 0) {
			return true;
		}
		if(getColor() == Color.WHITE && getX() == 7) {
			return true;
		}
		return false; 
	}
	
	public Piece promotion(String s){
		Piece p=null;
		if(s.compareTo("Queen") == 0) {
			p = new Queen(getX(), getY(), getColor());
		}
		if(s.compareTo("Rook") == 0) {
			p = new Rook(getX(), getY(), getColor());
		}
		if(s.compareTo("Knight") == 0) {
			p = new Knight(getX(), getY(), getColor());
		}
		if(s.compareTo("Bishop") == 0) {
			p = new Bishop(getX(), getY(), getColor());
		}
		return p;
	}
	
	public boolean isFirst() { return m_first; }
	
	public boolean isPawn() { return true; }
	
	public boolean checkMove(Piece m_pieces[][], int x, int y) {
		Color c = getColor();
		if(x < 0 || x >= 8 || y > 0 || y >= 8) {
			return false;
		}
		if(getY() != y) {
			return false;
		}
		
		if(m_first) {
			if(c == Color.BLACK && (getX()+1 == x || getX()+2 == x)) {
				return true;
			}
			if(c == Color.WHITE && (getX()-1 == x || getX()-2 == x)) {
				return true;
			}
		}
		else {
			if(getX()+1 == x || getX()+2 == x) {
				return true;
			}
		}
		
		return false;
	}
}
