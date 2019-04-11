package httpserver.itf.impl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import httpserver.itf.HttpSession;

public class RemoveListener implements ActionListener {

	HttpServer server;
	HttpSession session;
	
	public RemoveListener(HttpServer httpServer, HttpSession s) {
		server = httpServer;
		session = s;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		server.removeSession(session);
	}

}
