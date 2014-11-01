package core;

public class Pawn extends Piece {

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

	public boolean isPawn() {
		return true;
	}

	public void checkMove(Piece pieces[][], int x, int y) throws MoveException {
		super.checkMove(pieces, x, y);

		if (getX() != x)
			throw new MoveException("Invalid move");
		
		Piece otherPiece = pieces[y][x];
		if (otherPiece != null)
			throw new MoveException("Invalid move");

		if (!hasMoved()) {
			if (m_color == Color.BLACK) {
				if (getY() + 1 == y)
					return;
				else if (getY() + 2 == y) {
					if(pieces[y - 1][x] == null)
						return;
					else
						throw new MoveException("Invalid move");
				}
			}
			if (m_color == Color.WHITE) {
				if (getY() - 1 == y)
					return;
				else if (getY() - 2 == y) {
					if(pieces[y + 1][x] == null)
						return;
					else
						throw new MoveException("Invalid move");
				}
			}
		}
		else {
			if (m_color == Color.BLACK && getY() + 1 == y)
				return;
			if (m_color == Color.WHITE && getY() - 1 == y)
				return;
		}
		
		throw new MoveException("Invalid move");
	}

	public void checkCapture(Piece pieces[][], int x, int y, boolean ignorePiece) throws MoveException {
		super.checkMove(pieces, x, y);
		
		// En passant
		Piece pawn = null;
		if(m_color == Color.WHITE && m_y == 3) {
			if(y == m_y - 1 && x == m_x - 1)
				pawn = pieces[m_y][m_x - 1];
			else if(y == m_y - 1 && x == m_x + 1)
				pawn = pieces[m_y][m_x + 1];
		}
		else if(m_color == Color.BLACK && m_y == 4) {
			if(y == m_y + 1 && x == m_x - 1)
				pawn = pieces[m_y][m_x - 1];
			else if(y == m_y + 1 && x == m_x + 1)
				pawn = pieces[m_y][m_x + 1];
		}
		if(pawn != null && pawn.isPawn() && pawn.getColor() != m_color && pawn.getMoveCount() == 1)
			return;
		
		if (m_color == Color.BLACK && getY() + 1 != y)
			throw new MoveException("Invalid move");
		if (m_color == Color.WHITE && getY() - 1 != y)
			throw new MoveException("Invalid move");
		
		if (Math.abs(m_x - x) != 1)
			throw new MoveException("Invalid move");

		if(!ignorePiece) {
			Piece otherPiece = pieces[y][x];
			if (otherPiece == null)
				throw new MoveException("Invalid move");
			if (m_color == otherPiece.getColor())
				throw new MoveException("Invalid move");
		}
	}
}