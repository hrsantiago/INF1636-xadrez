package core;

public class Game {
	private static Game m_instance = null;
	
	private Board m_board;
	
	protected Game() {
		m_board = new Board(8, 8);
	}
	
	public static Game getInstance() {
		if(m_instance == null)
			m_instance = new Game();
		return m_instance;
	}
	
	public Board getBoard() {
		return m_board;
	}
}
