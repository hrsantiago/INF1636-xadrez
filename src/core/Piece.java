package core;

public class Piece {
	
	public enum Color {
		WHITE,
		BLACK,
	}
	
	private Color m_color;

	public Piece(Color color) {
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
}
