package core;

public abstract class Piece {
	
	public enum Color {
		WHITE,
		BLACK,
	}
	
	protected int m_x;
	protected int m_y;
	protected Color m_color;

	public Piece(int x, int y, Color color) {
		m_x = x;
		m_y = y;
		m_color = color;
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
	
	public boolean checkMove(Piece pieces[][], int x, int y) {
		if(x < 0 || x >= 8 || y < 0 || y >= 8)
			return false;
		
		if(m_x == x && m_y == y)
			return false;
		
		return true;
	}
	
	public boolean checkCapture(Piece pieces[][], int x, int y, boolean ignorePiece) {
		return checkMove(pieces, x, y);
	}
	
	public int getX() {
		return m_x;
	}
	
	public int getY() {
		return m_y;
	}
	
	public void move(int x, int y) {
		m_x = x;
		m_y = y;
	}
}
