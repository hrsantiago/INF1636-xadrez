package core;

import util.Emitter;
import core.Piece.Color;

public class Board extends Emitter {
	private int m_width;
	private int m_height;
	private Piece m_pieces[][];
	private Piece m_selectedPiece;
	
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
		
		m_selectedPiece = null;
	}
	
	public int getWidth() {
		return m_width;
	}
	
	public int getHeight() {
		return m_height;
	}
	
	public Piece getPiece(int row, int column) {
		if(column < 0 || column >= m_width || row < 0 || row >= m_height)
			return null;
		return m_pieces[row][column];
	}
	
	public Piece[][] getPieces() {
		return m_pieces;
	}
	
	public Piece getSelectedPiece() {
		return m_selectedPiece;
	}
	
	public void processTileClick(int x, int y) {
		if(m_selectedPiece == null) {
			m_selectedPiece = m_pieces[y][x];
		}
		else {
			if(m_selectedPiece.getX() != x || m_selectedPiece.getY() != y) {
				Piece piece = m_pieces[y][x];
				if(piece != null) {
					if(piece.getColor() == m_selectedPiece.getColor())
						m_selectedPiece = piece;
					else if(!movePiece(m_selectedPiece, x, y))
						m_selectedPiece = null;
						
				}
				else if(!movePiece(m_selectedPiece, x, y))
					m_selectedPiece = null;
			}
			else
				m_selectedPiece = null;
		}
		emit("onTileClicked", x, y);
	}
	
	public boolean movePiece(Piece piece, int x, int y) {
		if(piece.checkMove(m_pieces, x, y)) {
			m_pieces[piece.getY()][piece.getX()] = null;
			m_pieces[y][x] = piece;
			piece.move(x, y);
			return true;
		}
		return false;
	}
	
	public boolean isTileChecked(int x, int y, Color checkerColor, Piece callerPiece) {
		for(int i = 0; i < m_height; ++i) {
			for(int j = 0; j < m_width; ++j) {
				Piece piece = m_pieces[i][j];
				if(piece != null && piece.getColor() == checkerColor) {
					// Kings can't check themselves
					if(callerPiece != null && callerPiece.isKing() && piece.isKing()) {
						if(Math.abs(piece.getX()-x) <= 1 && Math.abs(callerPiece.getX()-x) <= 1 && 
						   Math.abs(piece.getY()-y) <= 1 && Math.abs(callerPiece.getY()-y) <= 1)
						return true;
					}
					else if(piece.checkMove(m_pieces, x, y))
						return true;
				}
			}
		}
		return false;
	}
}
