package httpserver.itf.impl;

import java.util.HashMap;

import httpserver.itf.HttpSession;

public class Session implements HttpSession {

	private String id;
	private HashMap<String, Object> session;
	
	public Session(String id) {
		this.id = id;
		session = new HashMap<String, Object>();
	}
	
	@Override
	public String getId() {
		return id;
	}

	@Override
	public Object getValue(String key) {
		return session.get(key);
	}

	@Override
	public void setValue(String key, Object value) {
		session.put(key, value);
	}

}
