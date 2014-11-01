package core;

public class King extends Piece {

	public King(int x, int y, Color color) {
		super(x, y, color);
	}

	public boolean isKing() {
		return true;
	}

	public void checkMove(Piece pieces[][], int x, int y) throws MoveException {
		super.checkMove(pieces, x, y);

		Piece otherPiece = pieces[y][x];
		if (otherPiece != null) {
			if (otherPiece.getColor() == m_color)
				throw new MoveException("Invalid move");
			if (otherPiece.isKing())
				throw new MoveException("Invalid move");
		}

		if (Math.abs(m_x - x) > 2 || Math.abs(m_y - y) > 1)
			throw new MoveException("Invalid move");

		// Castling
		if(Math.abs(m_x - x) == 2) {
			if(m_y != y)
				throw new MoveException("Invalid move");
			if(hasMoved())
				throw new MoveException("Invalid move");

			Piece rook = null;
			if(x == m_x + 2) {
				for(int j = m_x + 1; j <= m_x + 2; ++j)
					if(pieces[m_y][j] != null)
						throw new MoveException("Invalid move");
				rook = pieces[m_y][m_x + 3];
			}
			else if(x == m_x - 2) {
				for(int j = m_x - 1; j >= m_x - 3; --j)
					if(pieces[m_y][j] != null)
						throw new MoveException("Invalid move");
				rook = pieces[m_y][m_x - 4];
			}

			if(rook == null || rook.hasMoved())
				throw new MoveException("Invalid move");
		}

		Game game = Game.getInstance();
		Board board = game.getBoard();

		// This function is slow, so it should be the last check.
		if (board.isTileChecked(x, y, getEnemyColor(), this))
			throw new MoveException("Invalid move");
	}
}
