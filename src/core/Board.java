package core;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

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
			Piece.Color color = Color.WHITE;
			if(i == 0)
				color = Color.BLACK;
			m_pieces[i][0] = new Rook(0, i, color);
			m_pieces[i][1] = new Knight(1, i, color);
			m_pieces[i][2] = new Bishop(2, i, color);
			m_pieces[i][3] = new Queen(3, i, color);
			m_pieces[i][4] = new King(4, i, color);
			m_pieces[i][5] = new Bishop(5, i, color);
			m_pieces[i][6] = new Knight(6, i, color);
			m_pieces[i][7] = new Rook(7, i, color);
			
			int pawnRow = (i == 0) ? 1 : m_height - 2;
			for(int j = 0; j < m_width; ++j)
				m_pieces[pawnRow][j] = new Pawn(j, pawnRow, color);
		}
		
		m_selectedPiece = null;
	}
	
	public void serialize(FileOutputStream out) throws IOException {
		for(int i = 0; i < m_height; ++i) {
			for(int j = 0; j < m_width; ++j) {
				Piece piece = m_pieces[i][j];
				if(piece != null) {
					Piece.Color color = piece.getColor();
					out.write(1);
					out.write(j);
					out.write(i);
					out.write(color == Piece.Color.WHITE ? 0 : 1);
					
					if(piece.isPawn())
						out.write(0);
					else if(piece.isRook())
						out.write(1);
					else if(piece.isKnight())
						out.write(2);
					else if(piece.isBishop())
						out.write(3);
					else if(piece.isQueen())
						out.write(4);
					else if(piece.isKing())
						out.write(5);
					else
						throw new IOException("Board is corrupted");
				}
			}
		}
		out.write(0);
	}
	
	public void unserialize(FileInputStream in) throws IOException {
		m_pieces = new Piece[m_height][m_width];
		
		int code;
		while((code = in.read()) != -1) {
			if(code == 0)
				break;
			else if(code == 1) {
				int x = in.read();
				int y = in.read();
				int colorCode = in.read();
				int pieceCode = in.read();
				
				Piece.Color color = (colorCode == 0) ? Piece.Color.WHITE : Piece.Color.BLACK;
				Piece piece = null;
				if(pieceCode == 0)
					piece = new Pawn(x, y, color);
				else if(pieceCode == 1)
					piece = new Rook(x, y, color);
				else if(pieceCode == 2)
					piece = new Knight(x, y, color);
				else if(pieceCode == 3)
					piece = new Bishop(x, y, color);
				else if(pieceCode == 4)
					piece = new Queen(x, y, color);
				else if(pieceCode == 5)
					piece = new King(x, y, color);
				else
					throw new IOException("File is corrupted");
				
				m_pieces[y][x] = piece;
			}
		}
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
