package core;

public class Bishop extends Piece {

	public Bishop(int x, int y, Color color) {
		super(x, y, color);
	}

	public boolean isBishop() { return true; }
	public void checkMove(Piece pieces[][], int x, int y) throws MoveException {
		super.checkMove(pieces, x, y);

		Piece otherPiece = pieces[y][x];
		if(otherPiece != null && otherPiece.getColor() == m_color)
			throw new MoveException("Invalid move");

		if(Math.abs(getX()-x) != Math.abs(getY()-y))
			throw new MoveException("Invalid move");

		if(x > getX()) {
			if(y > getY()) {
				for(int i = 1; i < Math.abs(getX()-x); i++) {
					if(pieces[getY()+i][getX()+i] != null)
						throw new MoveException("Invalid move");
				}
			}
			else {		//y<getY()
				for(int i = 1; i < Math.abs(getX()-x); i++) {
					if(pieces[getY()-i][getX()+i] != null)
						throw new MoveException("Invalid move");
				}
			}
		}
		else {
			if(y > getY()) {
				for(int i = 1;i < Math.abs(getX()-x); i++) {
					if(pieces[getY()+i][getX()-i] != null)
						throw new MoveException("Invalid move");
				}
			}
			else {		//y<getY()
				for(int i = 1; i < Math.abs(getX()-x); i++) {
					if(pieces[getY()-i][getX()-i] != null)
						throw new MoveException("Invalid move");
				}
			}
		}
	}
}
