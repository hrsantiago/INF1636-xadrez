package util;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

class Connection {
	private WeakReference<Object> m_receiver;
	private Method m_slot;
	
	public Connection(Object receiver, Method slot) {
		m_receiver = new WeakReference<Object>(receiver);
		m_slot = slot;
	}

	public Object getReceiver() {
		return m_receiver.get();
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
	
	public void connect(String signal, Object receiver, String methodName, Class<?>[] methodClasses) {
		try {
			connect(signal, receiver, receiver.getClass().getMethod(methodName, methodClasses));
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
	}
	
	public void connect(String signal, Object receiver, String methodName) {
		Method[] methods = receiver.getClass().getDeclaredMethods();
		for(Method m : methods) {
			if(m.getName().equals(methodName)) {
				connect(signal, receiver, m);
				break;
			}			
		}
	}

	public void disconnect(String signal, Object receiver, String methodName) {
		List<Connection> connections = m_connections.get(signal);
		if(connections != null) {
			Iterator<Connection> iterator = connections.iterator();
			while(iterator.hasNext()) {
				Connection connection = iterator.next();
				if(connection.getReceiver() == null || (connection.getReceiver() == receiver && connection.getSlot().getName() == methodName))
					iterator.remove();
			}
		}
	}
	
	protected void emit(String signal, Object... args) {
		List<Connection> connections = m_connections.get(signal);
		if(connections != null) {
			Iterator<Connection> iterator = connections.iterator();
			while(iterator.hasNext()) {
				Connection connection = iterator.next();
				Object receiver = connection.getReceiver();
				if(receiver != null) {
					Method slot = connection.getSlot();
					try {
						slot.invoke(receiver, args);
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						e.printStackTrace();
					}
				}
				else
					iterator.remove();
			}
		}
	}
}
