package core;

public class Pawn extends Piece {

	boolean m_first = true;

	public Pawn(int x, int y, Color color) {
		super(x, y, color);
	}

	public boolean isPromotion() {
		if(getColor() == Color.BLACK && getX() == 0)
			return true;
		else if(getColor() == Color.WHITE && getX() == 7)
			return true;
		return false; 
	}

	public Piece promotion(String s){
		Piece p = null;
		if(s.compareTo("Queen") == 0)
			p = new Queen(getX(), getY(), getColor());
		else if(s.compareTo("Rook") == 0)
			p = new Rook(getX(), getY(), getColor());
		else if(s.compareTo("Knight") == 0)
			p = new Knight(getX(), getY(), getColor());
		else if(s.compareTo("Bishop") == 0)
			p = new Bishop(getX(), getY(), getColor());
		return p;
	}

	public boolean isFirst() { return m_first; }

	public void move(int x, int y) {
		super.move(x, y);
		m_first = false;
	}

	public boolean isPawn() { return true; }

	public boolean checkMove(Piece pieces[][], int x, int y) {
		// TODO: en passant
		if(!super.checkMove(pieces, x, y))
			return false;

		Piece otherPiece = pieces[y][x];
		if(otherPiece != null) {
			if(m_color != otherPiece.getColor()) {
				if(getX()+1 == x || getX()-1 == x) {
					if(m_color == Color.WHITE && getY()+1 == y)
						return true;
					if(m_color == Color.BLACK && getY()-1 == y)
						return true;
				}
			}
			else
				return false;
		}

		else {
			if(getX() != x)
				return false;
			
			if(m_first) {
				if(m_color == Color.WHITE && (getY()+1 == y || getY()+2 == y))
					return true;
				if(m_color == Color.BLACK && (getY()-1 == y || getY()-2 == y))
					return true;
			}
			else {
				if(m_color == Color.WHITE && getY()+1 == y)
					return true;
				if(m_color == Color.BLACK && getY()-1 == y)
					return true;
			}
		}

		return false;
	}
}
