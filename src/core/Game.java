package core;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import util.Emitter;

public class Game extends Emitter {
	private static Game m_instance = null;
	private Piece.Color m_playerTurnColor;
	
	private Board m_board;
	
	protected Game() {
		create();
	}
	
	public static Game getInstance() {
		if(m_instance == null)
			m_instance = new Game();
		return m_instance;
	}
	
	public void create() {
		m_board = new Board(8, 8);
		m_playerTurnColor = Piece.Color.WHITE;
		
		emit("onCreate");
	}
	
	public void serialize(FileOutputStream out) throws IOException {
		if(m_playerTurnColor == Piece.Color.WHITE)
			out.write(0);
		else
			out.write(1);
		
		m_board.serialize(out);
		emit("onSerialize");
	}
	
	public void unserialize(FileInputStream in) throws IOException {
		int color = in.read();
		if(color == 0)
			m_playerTurnColor = Piece.Color.WHITE;
		else
			m_playerTurnColor = Piece.Color.BLACK;
		
		m_board.unserialize(in);
		emit("onUnserialize");
	}
	
	public Board getBoard() {
		return m_board;
	}
}
