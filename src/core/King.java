package core;

public class King extends Piece {

	public King(int x, int y, Color color) {
		super(x, y, color);
	}

	public boolean isKing() {
		return true;
	}

	public boolean checkMove(Piece pieces[][], int x, int y) {
		if (!super.checkMove(pieces, x, y))
			return false;

		Piece otherPiece = pieces[y][x];
		if (otherPiece != null) {
			if (otherPiece.getColor() == m_color)
				return false;
			if (otherPiece.isKing())
				return false;
		}

		if (Math.abs(m_x - x) > 2 || Math.abs(m_y - y) > 1)
			return false;
		
		// Castling
		if(Math.abs(m_x - x) == 2) {
			if(m_y != y)
				return false;
			if(hasMoved())
				return false;
			
			Piece rook = null;
			if(x == m_x + 2) {
				for(int j = m_x + 1; j <= m_x + 2; ++j)
					if(pieces[m_y][j] != null)
						return false;
				rook = pieces[m_y][m_x + 3];
			}
			else if(x == m_x - 2) {
				for(int j = m_x - 1; j >= m_x - 3; --j)
					if(pieces[m_y][j] != null)
						return false;
				rook = pieces[m_y][m_x - 4];
			}
			
			if(rook == null || rook.hasMoved())
				return false;
		}
		

		Game game = Game.getInstance();
		Board board = game.getBoard();

		// This function is slow, so it should be the last check.
		if (board.isTileChecked(x, y, getEnemyColor(), this))
			return false;

		return true;
	}
}
