package core;

class Move {
	
	private Piece m_from;
	private Position m_fromPosition;
	
	private Piece m_to;
	private Position m_toPosition;
	
	private boolean m_linked;
	
	public Move(Piece from, Piece to, boolean linked) {
		if(from != null)
			m_from = from.clone();
		else
			m_from = null;
		
		if(to != null)
			m_to = to.clone();
		else
			m_to = null;
		
		m_linked = linked;
		m_fromPosition = null;
		m_toPosition = null;
	}
	
	public void setFromPosition(Position position) {
		m_fromPosition = position;
	}
	
	public void setToPosition(Position position) {
		m_toPosition = position;
	}
	
	public Position getFromPosition() {
		return m_fromPosition;
	}
	
	public Position getToPosition() {
		return m_toPosition;
	}
	
	public Piece getFrom() {
		return m_from;
	}
	
	public Piece getTo() {
		return m_to;
	}
	
	public boolean isLinked() {
		return m_linked;
	}	
}
