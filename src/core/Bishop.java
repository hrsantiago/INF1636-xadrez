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

		if(x > getX()) {
			if(y > getY()) {
				for(int i = 1; i < Math.abs(getX()-x); i++) {
					if(pieces[getY()+i][getX()+i] != null)
						return false;
				}
			}
			else {		//y<getY()
				for(int i = 1; i < Math.abs(getX()-x); i++) {
					if(pieces[getY()-i][getX()+i] != null)
						return false;
				}
			}
		}
		else {
			if(y > getY()) {
				for(int i = 1;i < Math.abs(getX()-x); i++) {
					if(pieces[getY()+i][getX()-i] != null)
						return false;
				}
			}
			else {		//y<getY()
				for(int i = 1; i < Math.abs(getX()-x); i++) {
					if(pieces[getY()-i][getX()-i] != null)
						return false;
				}
			}
		}
		return true;
	}
}
