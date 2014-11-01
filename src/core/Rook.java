package core;

public class Rook extends Piece {

	public Rook(int x, int y, Color color) {
		super(x, y, color);
	}

	public boolean isRook() {
		return true;
	}

	public void checkMove(Piece pieces[][], int x, int y) throws MoveException {
		super.checkMove(pieces, x, y);

		Piece otherPiece = pieces[y][x];
		if (otherPiece != null && otherPiece.getColor() == m_color)
			throw new MoveException("Invalid move");

		if (getX() == x && getY() != y) {
			if (y > getY()) {
				for (int i = getY() + 1; i < y; i++) {
					if (pieces[i][x] != null)
						throw new MoveException("Invalid move");
				}
			} else {
				for (int i = getY() - 1; i > y; i--) {
					if (pieces[i][x] != null)
						throw new MoveException("Invalid move");
				}
			}
			return;
		}
		if (getY() == y && getX() != x) {
			if (x > getX()) {
				for (int i = getX() + 1; i < x; i++) {
					if (pieces[y][i] != null)
						throw new MoveException("Invalid move");
				}
			} else {
				for (int i = getX() - 1; i > x; i--) {
					if (pieces[y][i] != null)
						throw new MoveException("Invalid move");
				}
			}
			return;
		}
		throw new MoveException("Invalid move");
	}
}
