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
	
	public void move(int x, int y) {
		super.move(x, y);
		m_first = false;
	}
	
	public boolean isPawn() { return true; }
	
	public boolean checkMove(Piece m_pieces[][], int x, int y) {
		//TODO: Manobra Passent, movimento diagonal bugado
		Color c = getColor();
		if(x < 0 || x >= 8 || y < 0 || y >= 8) {
			return false;
		}
		
		if(m_pieces[x][y]!=null){
		if(getX()+1==x||getX()-1==x){
			if(c == Color.WHITE&&getY()+1==y){
				
				if(m_pieces[x][y].getColor()==Color.BLACK){
					return true;
				}
				
			}
				if(c == Color.BLACK&&getY()-1==y){
					if(m_pieces[x][y].getColor()==Color.WHITE){
						return true;
					}
			}
		}
		}
		
		if(getX() != x) {
			return false;
		}
		
		if(m_first) {
			if(c == Color.WHITE && (getY()+1 == y || getY()+2 == y)) {
				return true;
			}
			if(c == Color.BLACK && (getY()-1 == y || getY()-2 == y)) {
				return true;
			}
		}
		else {
			if(c == Color.WHITE && getY()+1 == y) {
				return true;
			}
			if(c == Color.BLACK && getY()-1 == y) {
				return true;
			}
		}
		
		return false;
	}
}
