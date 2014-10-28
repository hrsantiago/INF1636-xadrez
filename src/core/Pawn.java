package core;

public class Pawn extends Piece {

	boolean m_first = true;

	public Pawn(int x, int y, Color color) {
		super(x, y, color);
	}

	public boolean canPromote() {
		if (getColor() == Color.WHITE && getY() == 0)
			return true;
		else if (getColor() == Color.BLACK && getY() == 7)
			return true;
		return false;
	}

	public boolean isFirst() {
		return m_first;
	}

	public void move(int x, int y) {
		super.move(x, y);
		m_first = false;
	}

	public boolean isPawn() {
		return true;
	}

	public boolean checkMove(Piece pieces[][], int x, int y) {
		// TODO: en passant
		if (!super.checkMove(pieces, x, y))
			return false;

		if (getX() != x)
			return false;
		
		Piece otherPiece = pieces[y][x];
		if (otherPiece != null)
			return false;

		if (m_first) {
			if (m_color == Color.BLACK) {
				if (getY() + 1 == y) {
					return true;

				} else if (getY() + 2 == y) {
					if (pieces[y - 1][x] != null) {
						return false;
					} else {
						return true;
					}
				}
			}
			if (m_color == Color.WHITE) {
				if (getY() - 1 == y) {
					return true;

				} else if (getY() - 2 == y) {
					if (pieces[y + 1][x] != null) {
						return false;
					} else {
						return true;
					}
				}
			}
		} else {
			if (m_color == Color.BLACK && getY() + 1 == y)
				return true;
			if (m_color == Color.WHITE && getY() - 1 == y)
				return true;
		}
		return false;
	}

	public boolean checkCapture(Piece pieces[][], int x, int y, boolean ignorePiece) {
		if (!super.checkMove(pieces, x, y))
			return false;
		
		if (m_color == Color.BLACK && getY() + 1 != y)
			return false;
		if (m_color == Color.WHITE && getY() - 1 != y)
			return false;
		
		if (Math.abs(m_x - x) != 1)
			return false;

		if(!ignorePiece) {
			Piece otherPiece = pieces[y][x];
			if (otherPiece == null)
				return false;
	
			if (m_color == otherPiece.getColor())
				return false;
		}

		return true;
	}
}