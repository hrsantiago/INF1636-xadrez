package core;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Vector;

import core.Piece.Color;

public class Board {
	
	private int m_width;
	private int m_height;
	private Piece m_pieces[][];
	private Position m_selectedPosition;
	private boolean ignorePlayerTurn = false;
	private Vector<Move> m_moves;
	
	public Board(int width, int height) {
		m_width = width;
		m_height = height;
		m_pieces = new Piece[height][width];
		m_moves = new Vector<Move>();
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
		
		m_selectedPosition = null;
	}
	
	public void serialize(FileOutputStream out) throws IOException {
		for(int i = 0; i < m_height; ++i) {
			for(int j = 0; j < m_width; ++j) {
				Piece piece = m_pieces[i][j];
				if(piece != null) {
					Color color = piece.getColor();
					out.write(1);
					out.write(j);
					out.write(i);
					out.write(color == Color.WHITE ? 0 : 1);
					
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
		m_moves = new Vector<Move>();
		
		int code;
		while((code = in.read()) != -1) {
			if(code == 0)
				break;
			else if(code == 1) {
				int x = in.read();
				int y = in.read();
				int colorCode = in.read();
				int pieceCode = in.read();
				
				Piece.Color color = (colorCode == 0) ? Color.WHITE : Color.BLACK;
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
	
	public Position getSelectedPosition() {
		return m_selectedPosition;
	}
	
	public Piece getSelectedPiece() {
		if(m_selectedPosition != null)
			return m_pieces[m_selectedPosition.y][m_selectedPosition.x];
		return null;
	}
	
	public boolean processTileClick(int x, int y, Color playerColor) {
		boolean moved = false;
		Piece selectedPiece = getSelectedPiece();
		if(selectedPiece == null) {
			Piece piece = m_pieces[y][x];
			if(piece != null && (ignorePlayerTurn || piece.getColor() == playerColor))
				m_selectedPosition = piece.getPosition();
		}
		else {
			if(selectedPiece.getX() != x || selectedPiece.getY() != y) {
				Piece piece = m_pieces[y][x];
				if(piece != null) {
					if(piece.getColor() == selectedPiece.getColor())
						m_selectedPosition = piece.getPosition();
					else if(!movePiece(m_selectedPosition, x, y))
						m_selectedPosition = null;
					else
						moved = true;
				}
				else if(!movePiece(m_selectedPosition, x, y))
					m_selectedPosition = null;
				else
					moved = true;
			}
			else
				m_selectedPosition = null;
		}
		
		if(moved && ignorePlayerTurn)
			m_selectedPosition = new Position(x, y);
		
		if(moved && !ignorePlayerTurn)
			m_selectedPosition = null;
		
		return moved;
	}
	
	public boolean movePiece(Position from, int x, int y) {
		Piece piece = m_pieces[from.y][from.x];
		if(piece != null) {
			try {
				piece.checkMove(m_pieces, x, y);
			} catch (MoveException e) {
				try {
					piece.checkCapture(m_pieces, x, y, false);
				} catch (MoveException e1) {
					return false;
				}
			}

			Piece toPiece = m_pieces[y][x];
			
			internalMove(piece.getPosition(), new Position(x, y), false);
			
			// Special moves
			if(piece.isPawn()) {
				Pawn pawn = (Pawn)piece;
				
				// Promotion
				if(pawn.canPromote())
					m_pieces[y][x] = new Queen(x, y, piece.getColor());
				
				// En passant
				if(toPiece == null && Math.abs(from.x - x) == 1 && Math.abs(from.y - y) == 1)
					internalRemove(new Position(x, from.y), true);
			}
			
			// Castling 
			if(piece.isKing()) {
				if(x == from.x + 2) {
					Piece rook = getPiece(from.y, from.x + 3);
					internalMove(rook.getPosition(), new Position(from.x + 1, from.y), true);
				}
				else if(x == from.x - 2) {
					Piece rook = getPiece(from.y, from.x - 4);
					internalMove(rook.getPosition(), new Position(from.x - 1, from.y), true);
				}
			}
			
			// Well, in fact we cant move cause this will get the king in check
			Piece king = getKing(piece.getColor());
			if(king != null && king.isChecked()) {
				undo();
				return false;
			}
				
			return true;
		}
		return false;
	}
	
	public boolean checkMovePiece(Position from, int x, int y) {
		if(movePiece(from, x, y)) {
			undo();
			return true;
		}
		return false;
	}
	
	public boolean playerHasMoves(Color color) {
		for(int i = 0; i < m_height; ++i) {
			for(int j = 0; j < m_width; ++j) {
				Piece piece = m_pieces[i][j];
				if(piece != null && piece.getColor() == color) {
					if(pieceHasMoves(piece))
						return true;
				}
			}
		}
		return false;
	}
	
	public boolean pieceHasMoves(Piece piece) {
		Position position = piece.getPosition();
		for(int i = 0; i < m_height; ++i) {
			for(int j = 0; j < m_width; ++j) {
				if(checkMovePiece(position, j, i))
					return true;
			}
		}
		return false;
	}
	
	public Piece getKing(Color color) {
		for(int i = 0; i < m_height; ++i) {
			for(int j = 0; j < m_width; ++j) {
				Piece piece = m_pieces[i][j];
				if(piece != null && piece.isKing() && piece.getColor() == color)
					return piece;
			}
		}
		return null;
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
					else {
						try {
							piece.checkCapture(m_pieces, x, y, true);
							return true;
						} catch (MoveException e) {
						}
						
					}
						
				}
			}
		}
		return false;
	}
	
	public boolean undo() {
		if(m_moves.size() == 0)
			return false;
		
		Move move = null;
		do {
			move = m_moves.lastElement();
			
			Position fromPos = move.getFromPosition();
			if(fromPos != null) {
				Piece fromPiece = move.getFrom();
				m_pieces[fromPos.y][fromPos.x] = fromPiece;
			}
			Position toPos = move.getToPosition();
			if(toPos != null) {
				Piece toPiece = move.getTo();
				m_pieces[toPos.y][toPos.x] = toPiece;
			}

			m_moves.remove(m_moves.size() - 1);
		} while(move.isLinked());
		return true;
	}
	
	private void internalMove(Position from, Position to, boolean linked) {
		Piece fromPiece = m_pieces[from.y][from.x];
		Piece toPiece = m_pieces[to.y][to.x];
		
		Move move = new Move(fromPiece, toPiece, linked);
		move.setFromPosition(from);
		move.setToPosition(to);
		m_moves.add(move);
		
		fromPiece.move(to.x, to.y);
		m_pieces[to.y][to.x] = fromPiece;
		m_pieces[from.y][from.x] = null;
	}
	
	private void internalRemove(Position position, boolean linked) {
		Piece piece = m_pieces[position.y][position.x];
		
		Move move = new Move(piece, null, linked);
		move.setFromPosition(position);
		m_moves.add(move);
		
		m_pieces[position.y][position.x] = null;
	}
}
