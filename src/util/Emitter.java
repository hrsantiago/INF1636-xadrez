package util;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

class Connection {
	private Object m_receiver;
	private Method m_slot;
	
	public Connection(Object receiver, Method slot) {
		m_receiver = receiver;
		m_slot = slot;
	}

	public Object getReceiver() {
		return m_receiver;
	}
	
	public Method getSlot() {
		return m_slot;
	}
}

public abstract class Emitter {
	private Map<String, List<Connection>> m_connections = new HashMap<String, List<Connection>>();
	
	public void connect(String signal, Object receiver, Method slot) {
		List<Connection> connections = m_connections.get(signal);
		if(connections == null)
			connections = new LinkedList<Connection>();
		connections.add(new Connection(receiver, slot));
		m_connections.put(signal, connections);
	}
	
	public void disconnect(String signal, Object receiver, Method slot) {
		
	}
	
	protected void emit(String signal, Object... args) {
		List<Connection> connections = m_connections.get(signal);
		if(connections != null) {
			Iterator<Connection> iterator = connections.iterator();
			while(iterator.hasNext()) {
				Connection connection = iterator.next();
				Method slot = connection.getSlot();
				Object receiver = connection.getReceiver();
				try {
					slot.invoke(receiver, args);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
