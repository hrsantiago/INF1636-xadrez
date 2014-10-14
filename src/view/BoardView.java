package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import core.Board;
import core.Game;
import core.Piece;

public class BoardView extends JPanel {

	private static final long serialVersionUID = -8326868557052884988L;
	private boolean m_keepAspectRatio = true;
	
	public BoardView() {
		addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent event) {
				int x = event.getX();
				int y = event.getY();

				Game game = Game.getInstance();
				Board board = game.getBoard();

				int width = getWidth();
				width -= width % board.getWidth();
				int height = getHeight();
				height -= height % board.getHeight();
				
				if(m_keepAspectRatio) {
					width = Math.min(width, height);
					height = width;
				}
				
				int x0 = (getWidth() - width) / 2;
				int y0 = (getHeight() - height) / 2;
				if(x < x0 || x >= x0 + width || y < y0 || y >= y0 + width)
					return;
				
				int tileWidth = width / board.getWidth();
				int tileHeight = height / board.getHeight();
				
				int i = (y - y0) / tileHeight;
				int j = (x - x0) / tileWidth;
				
				System.out.println("Position (X,Y): " + j + " , " + i);
				board.removePiece(i, j);
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {}
			@Override
			public void mouseExited(MouseEvent arg0) {}
			@Override
			public void mousePressed(MouseEvent arg0) {}
			@Override
			public void mouseReleased(MouseEvent arg0) {}
		});
		
		Game game = Game.getInstance();
		Board board = game.getBoard();
		
		try {
			board.connect("onPieceRemoved", this, BoardView.class.getMethod("onPieceRemoved", new Class[]{int.class, int.class}));
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void onPieceRemoved(int row, int column) {
		System.out.println("Testing emitter class, onPieceRemoved" + row + " " + column);
		repaint();
	}

	public void paintComponent(Graphics g) {
		super.paintComponents(g);
		
		Game game = Game.getInstance();
		Board board = game.getBoard();
		paintBoard(g, board);
	}

	private void paintBoard(Graphics g, Board board) {
		int width = getWidth();
		width -= width % board.getWidth();
		int height = getHeight();
		height -= height % board.getHeight();
		
		if(m_keepAspectRatio) {
			width = Math.min(width, height);
			height = width;
		}
		
		int tileWidth = width / board.getWidth();
		int tileHeight = height / board.getHeight();
		int x0 = (getWidth() - width) / 2;
		int y0 = (getHeight() - height) / 2;
		
		for(int i = 0; i < board.getHeight(); ++i) {
			for(int j = 0; j < board.getWidth(); ++j) {
				int x = x0 + j * tileWidth;
				int y = y0 + i * tileHeight;
				
				g.setColor(((i+j) % 2) == 0 ? Color.WHITE : Color.BLACK);
				g.fillRect(x, y, tileWidth, tileHeight);
				
				Piece piece = board.getPiece(i, j);
				if(piece != null)
					paintPiece(g, piece, x, y, tileWidth, tileHeight);
			}
		}
	}
	
	private void paintPiece(Graphics g, Piece piece, int x, int y, int w, int h) {
		// TODO: cache image
		try {
			String source = "assets/images/";
			
			if(piece.getColor() == Piece.Color.WHITE)
				source += "b_";
			else
				source += "p_";
			
			if(piece.isKing())
				source += "rei.gif";
			else if(piece.isQueen())
				source += "dama.gif";
			else if(piece.isBishop())
				source += "bispo.gif";
			else if(piece.isKnight())
				source += "cavalo.gif";
			else if(piece.isRook())
				source += "torre.gif";
			else if(piece.isPawn())
				source += "peao.gif";
			
			Image image = ImageIO.read(new File(source));
			g.drawImage(image, x, y, w, h, null);
		}
		catch(IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
