package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import core.Board;
import core.Game;
import core.Piece;

public class BoardView extends JPanel {

	private static final long serialVersionUID = -8326868557052884988L;

	public void paintComponent(Graphics g) {
		super.paintComponents(g);
		
		Game game = Game.getInstance();
		Board board = game.getBoard();
		paintBoard(g, board);
	}
	
	private void paintBoard(Graphics g, Board board) {
		int tileWidth = getWidth() / board.getWidth();
		int tileHeight = getHeight() / board.getHeight();
		
		for(int i = 0; i < board.getHeight(); ++i) {
			for(int j = 0; j < board.getWidth(); ++j) {
				int x = j * tileWidth;
				int y = i * tileHeight;
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
