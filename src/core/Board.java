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
		for(int i = 0; i < m_height; i+=m_height-1) {
			Piece.Color color = Color.BLACK;
			if(i == 0)
				color = Color.WHITE;
			m_pieces[i][0] = new Rook(0, i, color);
			m_pieces[i][1] = new Knight(1, i, color);
			m_pieces[i][2] = new Bishop(2, i, color);
			m_pieces[i][3] = new King(3, i, color);
			m_pieces[i][4] = new Queen(4, i, color);
			m_pieces[i][5] = new Bishop(5, i, color);
			m_pieces[i][6] = new Knight(6, i, color);
			m_pieces[i][7] = new Rook(7, i, color);
			
			int pawnRow = (i == 0) ? 1 : m_height - 2;
			for(int j = 0; j < m_width; ++j)
				m_pieces[pawnRow][j] = new Pawn(j, pawnRow, color);
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
