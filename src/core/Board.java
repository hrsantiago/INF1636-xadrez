package core;

import util.Emitter;
import core.Piece.Color;

public class Board extends Emitter {
	private int m_width;
	private int m_height;
	private Piece m_pieces[][];
	
	public Board(int width, int height) {
		m_width = width;
		m_height = height;
		m_pieces = new Piece[height][width];
		initialize();
	}
	
	public void initialize() {
		for(int k = 0; k < m_height; k+=m_height-1) {
			Piece.Color color = Color.WHITE;
			if(k == 0)
				color = Color.BLACK;
			m_pieces[k][0] = new Rook(color);
			m_pieces[k][1] = new Knight(color);
			m_pieces[k][2] = new Bishop(color);
			m_pieces[k][3] = new King(color);
			m_pieces[k][4] = new Queen(color);
			m_pieces[k][5] = new Bishop(color);
			m_pieces[k][6] = new Knight(color);
			m_pieces[k][7] = new Rook(color);
			
			int i = (k == 0) ? 1 : m_height - 2;
			for(int j = 0; j < m_width; ++j)
				m_pieces[i][j] = new Pawn(color);
		}
	}
	
	public int getWidth() {
		return m_width;
	}
	
	public int getHeight() {
		return m_height;
	}
	
	public Piece getPiece(int row, int column) {
		return m_pieces[row][column];
	}
	
	public void removePiece(int row, int column) {
		m_pieces[row][column] = null;
		emit("onPieceRemoved", row, column);
	}
}
