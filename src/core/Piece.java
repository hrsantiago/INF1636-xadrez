package core;

public abstract class Piece {
	
	public enum Color {
		WHITE,
		BLACK,
	}
	
	private int m_x;
	private int m_y;
	private Color m_color;

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
	
	public Color getColor() {
		return m_color;
	}
	public abstract boolean checkMove(Piece m_pieces[][],int x,int y);
	
	public int getX() {
		return m_x;
	}
	
	public int getY() {
		return m_y;
	}
}
