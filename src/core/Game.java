package core;

public class Game {
	private static Game m_instance = null;
	private boolean m_playerBlackTurn=true;//Começa com o jogador das peças pretas.
	
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
	public boolean isPlayerBlackTurn() {//verdadeiro se fora  vez do jogador preto e falso se for o jogador branco
		return m_playerBlackTurn;
	}
}
