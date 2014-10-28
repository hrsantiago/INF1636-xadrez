package core;

public class Rook extends Piece {

	public Rook(int x, int y, Color color) {
		super(x, y, color);
	}

	public boolean isRook() {
		return true;
	}

	public boolean checkMove(Piece pieces[][], int x, int y) {
		if (!super.checkMove(pieces, x, y))
			return false;

		Piece otherPiece = pieces[y][x];
		if (otherPiece != null && otherPiece.getColor() == m_color)
			return false;

		if (getX() == x && getY() != y) {
			if (y > getY()) {
				for (int i = getY() + 1; i < y; i++) {
					if (pieces[i][x] != null)
						return false;
				}
			} else {
				for (int i = getY() - 1; i > y; i--) {
					if (pieces[i][x] != null)
						return false;
				}
			}
			return true;
		}
		if (getY() == y && getX() != x) {
			if (x > getX()) {
				for (int i = getX() + 1; i < x; i++) {
					if (pieces[y][i] != null)
						return false;
				}
			} else {
				for (int i = getX() - 1; i > x; i--) {
					if (pieces[y][i] != null)
						return false;
				}
			}
			return true;
		}
		return false;
	}
}
