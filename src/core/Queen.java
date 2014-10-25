package core;

public class Queen extends Piece {

	public Queen(int x, int y, Color color) {
		super(x, y, color);
	}

	public boolean isQueen() {
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
					if (pieces[i][x] != null) {
						return false;
					}
				}
			} else {
				for (int i = getY() - 1; i > y; i--) {
					if (pieces[i][x] != null) {
						return false;
					}
				}
			}
			return true;
		}
		if (getY() == y && getX() != x) {
			if (x > getX()) {
				for (int i = getX() + 1; i < x; i++) {
					if (pieces[y][i] != null) {
						return false;
					}
				}
			} else {
				for (int i = getX() - 1; i > x; i--) {
					if (pieces[y][i] != null) {
						return false;
					}
				}
			}
			return true;
		}

		if (Math.abs(getX() - x) == Math.abs(getY() - y)) {
			if (x > getX()) {
				if (y > getY()) {
					for (int i = 1; i < Math.abs(getX() - x); i++) {
						if (pieces[getY() + i][getX() + i] != null) {
							return false;
						}

					}
				} else { // y<getY()
					for (int i = 1; i < Math.abs(getX() - x); i++) {
						if (pieces[getY() - i][getX() + i] != null) {
							return false;
						}

					}
				}
			} else {
				if (y > getY()) {
					for (int i = 1; i < Math.abs(getX() - x); i++) {
						if (pieces[getY() + i][getX() - i] != null) {
							return false;
						}

					}
				} else { // y<getY()
					for (int i = 1; i < Math.abs(getX() - x); i++) {
						if (pieces[getY() - i][getX() - i] != null) {
							return false;
						}

					}
				}
			}
			return true;
		}
		return false;
	}
}
