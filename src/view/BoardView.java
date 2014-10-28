package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import core.Board;
import core.Game;
import core.Piece;

public class BoardView extends JPanel {

	private static final long serialVersionUID = -8326868557052884988L;
	private boolean m_keepAspectRatio = true;
	private Map<String, Image> m_images;
	
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
				
				int tileX = (x - x0) / tileWidth;
				int tileY = (y - y0) / tileHeight;				
				game.processTileClick(tileX, tileY);
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
		
		m_images = new HashMap<String, Image>();
		
		Game game = Game.getInstance();
		game.connect("onCreate", this, "onGameCreated");
		game.connect("onUnserialize", this, "onGameUnserialize");
		onGameCreated();
	}
	
	public void onGameCreated() {
		Game game = Game.getInstance();
		Board board = game.getBoard();
		board.connect("onTileClicked", this, "onTileClicked");
		repaint();
	}
	
	public void onGameUnserialize() {
		repaint();
	}
	
	public void onTileClicked(int x, int y) {
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
			}
		}
		
		paintPieces(g, board, x0, y0, tileWidth, tileHeight);
	}
	
	private void paintPieces(Graphics g, Board board, int x0, int y0, int tileWidth, int tileHeight) {
		Graphics2D g2 = (Graphics2D)g;
		
		int lw = 4;
		
		g2.setStroke(new BasicStroke(lw));
		
		Piece selectedPiece = board.getSelectedPiece();
		for(int i = 0; i < board.getHeight(); ++i) {
			for(int j = 0; j < board.getWidth(); ++j) {
				int x = x0 + j * tileWidth;
				int y = y0 + i * tileHeight;
				Piece piece = board.getPiece(i, j);
				
				if(selectedPiece != null && (selectedPiece.checkMove(board.getPieces(), j, i) || selectedPiece.checkCapture(board.getPieces(), j, i, false))) {
					g2.setColor(Color.YELLOW);
					g2.drawRect(x + lw/2, y + lw/2, tileWidth - lw, tileHeight - lw);
				}
				
				if(piece != null) {
					if(piece == selectedPiece) {
						g2.setColor(Color.RED);
						g2.drawRect(x + lw/2, y + lw/2, tileWidth - lw, tileHeight - lw);
					}	
					paintPiece(g, piece, x, y, tileWidth, tileHeight);
				}
			}
		}
	}
	
	private void paintPiece(Graphics g, Piece piece, int x, int y, int w, int h) {
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
			
			Image image = m_images.get(source);
			if(image == null) {
				image = ImageIO.read(new File(source));
				m_images.put(source, image);
			}
			g.drawImage(image, x, y, w, h, null);
		}
		catch(IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
