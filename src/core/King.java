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

		if (Math.abs(m_x - x) > 1 || Math.abs(m_y - y) > 1)
			return false;

		Game game = Game.getInstance();
		Board board = game.getBoard();

		System.out.println(x + " " + y);

		// This function is slow, so it should be the last check.
		if (board.isTileChecked(x, y, getEnemyColor(), this))
			return false;

		return true;
	}
}
