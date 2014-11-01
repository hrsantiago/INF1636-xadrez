package core;

public abstract class Piece {
	
	public enum Color {
		WHITE,
		BLACK,
	}
	
	protected int m_x;
	protected int m_y;
	protected Color m_color;
	protected int m_moveCount = 0;

	public Piece(int x, int y, Color color) {
		m_x = x;
		m_y = y;
		m_color = color;
	}
	
	public Piece clone() {
		Piece piece = null;
		if(isKing())
			piece = new King(m_x, m_y, m_color);
		else if(isQueen())
			piece = new Queen(m_x, m_y, m_color);
		else if(isKnight())
			piece = new Knight(m_x, m_y, m_color);
		else if(isBishop())
			piece = new Bishop(m_x, m_y, m_color);
		else if(isRook())
			piece = new Rook(m_x, m_y, m_color);
		else if(isPawn())
			piece = new Pawn(m_x, m_y, m_color);
		
		piece.setMoveCount(m_moveCount);
		return piece;
	}
	
	public boolean isKing() { return false; }
	public boolean isQueen() { return false; }
	public boolean isKnight() { return false; }
	public boolean isBishop() { return false; }
	public boolean isRook() { return false; }
	public boolean isPawn() { return false; }
	
	public boolean isChecked() {
		Game game = Game.getInstance();
		Board board = game.getBoard();
		return board.isTileChecked(m_x, m_y, getEnemyColor(), this);
	}
	
	public Color getColor() {
		return m_color;
	}
	
	public Color getEnemyColor() {
		if(m_color == Color.WHITE)
			return Color.BLACK;
		else
			return Color.WHITE;
	}
	
	public void checkMove(Piece pieces[][], int x, int y) throws MoveException {
		if(x < 0 || x >= 8 || y < 0 || y >= 8)
			throw new MoveException("Out of bounds");
		
		if(m_x == x && m_y == y)
			throw new MoveException("Cant move to same position");
	}
	
	public void checkCapture(Piece pieces[][], int x, int y, boolean ignorePiece) throws MoveException {
		checkMove(pieces, x, y);
	}
	
	public int getX() {
		return m_x;
	}
	
	public int getY() {
		return m_y;
	}
	
	public Position getPosition() {
		return new Position(m_x, m_y);
	}
	
	public void setX(int x) {
		m_x = x;
	}
	
	public void setY(int y) {
		m_y = y;
	}
	
	public boolean hasMoved() {
		return m_moveCount > 0;
	}
	
	public void setMoveCount(int moveCount) {
		m_moveCount = moveCount;
	}
	
	public int getMoveCount() {
		return m_moveCount;
	}
	
	public void move(int x, int y) {
		m_x = x;
		m_y = y;
		m_moveCount += 1;
	}
}
