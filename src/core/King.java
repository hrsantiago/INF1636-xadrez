package core;

public class King extends Piece {
	
	public King(int x, int y, Color color) {
		super(x, y, color);
	}
	
	public boolean isKing() { return true; }
	
	public boolean isChecked(Piece m_pieces[][]){
		//TODO: preencher todos as possibilidades(os 3 casos)
		
		//caso do peão
		if(getX()==0){ //lado esquerdo		
			if(getColor()==Color.BLACK&&m_pieces[0][getY()+1]!=null){
			if(m_pieces[1][getY()+1].isPawn()==true&&m_pieces[1][getY()+1].getColor()==Color.WHITE){
				
				return true;
			}
			}
			if(getColor()==Color.WHITE&&m_pieces[1][getY()+1]!=null){
				if(m_pieces[1][getY()-1].isPawn()==true&&m_pieces[1][getY()-1].getColor()==Color.WHITE){
					
					return true;
				}
			}
		}
		if(getX()==7){//lado direito
			if(getColor()==Color.BLACK&&m_pieces[0][getY()+1]!=null){
				if(m_pieces[6][getY()+1].isPawn()==true&&m_pieces[6][getY()+1].getColor()==Color.WHITE){
					
					return true;
				}
				}
				if(getColor()==Color.WHITE&&m_pieces[1][getY()+1]!=null){
					if(m_pieces[6][getY()-1].isPawn()==true&&m_pieces[6][getY()-1].getColor()==Color.WHITE){
						
						return true;
					}
				}
		}
		
		if(getColor()==Color.BLACK){
			if(m_pieces[getX()-1][getY()-1]!=null){ //Centro
		
				if(m_pieces[getX()-1][getY()-1].isPawn()==true&&m_pieces[getX()][getY()-1].getColor()==Color.WHITE){
				
				return true;
				}
			}
			if(m_pieces[getX()+1][getY()-1]!=null){
				if(m_pieces[getX()+1][getY()-1].isPawn()==true&&m_pieces[getX()][getY()-1].getColor()==Color.WHITE){
				
				return true;
				}
			}
		}
		if(getColor()==Color.WHITE){
			if(m_pieces[getX()+1][getY()+1]!=null){
		
				if(m_pieces[getX()+1][getY()+1].isPawn()==true&&m_pieces[getX()+1][getY()+1].getColor()==Color.BLACK){
					
					return true;
				}
			}
			if(m_pieces[getX()-1][getY()+1]!=null){
				if(getColor()==Color.WHITE&&m_pieces[1][getY()+1]!=null){
					if(m_pieces[getX()-1][getY()+1].isPawn()==true&&m_pieces[getX()-1][getY()+1].getColor()==Color.BLACK){
						
						return true;
					}
			}
			}
		}
		//caso do cavalo
		
		
		//outros casos
		
		return false;
	}
	
	public boolean checkMove(Piece m_pieces[][],int x,int y) {
		if(x < 0 || x >= 8 || y < 0 || y >= 8) {
			return false;
		}
		if(getX()+1 < x || getX()-1 > x) {
			return false;
		}
		if(getY()+1 < y || getY()-1 > y) {
			return false;
		}
		
		if(getX() == x && (getY()+1 < y || getY()-1 > y)) { //Ultrapassa Lados?
			return false;
		}
		if(getY() == y && (getX()+1 < x || getX()-1 > x)) { //Ultrapassa  Cima e baixo?
			return false;
		}
		if(getX()+1 == y && (getY()+1 < y || getY()-1 > y)) { //Ultrapassa inclinados direita?
			return false;
		}
		if(getX()-1 == y && (getY()+1 < y || getY()-1 > y)) { //Ultrapassa inclinados esquerda?
			return false;
		}

		return true;
	}
}
