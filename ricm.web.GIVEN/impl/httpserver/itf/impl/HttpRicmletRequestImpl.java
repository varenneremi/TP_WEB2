package httpserver.itf.impl;

import java.io.IOException;
import java.util.HashMap;

import httpserver.itf.HttpResponse;
import httpserver.itf.HttpRicmlet;
import httpserver.itf.HttpRicmletRequest;
import httpserver.itf.HttpRicmletResponse;
import httpserver.itf.HttpSession;

public class HttpRicmletRequestImpl extends HttpRicmletRequest {

	String classe;
	HashMap<String, String> arg;

	public HttpRicmletRequestImpl(HttpServer hs, String method, String ressname) throws IOException {
		super(hs, method, ressname);
		arg = new HashMap<String, String>();
	}

	@Override
	public HttpSession getSession() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getArg(String name) {
		return arg.get(name);
	}

	@Override
	public String getCookie(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void process(HttpResponse resp) throws Exception {
		if (m_ressname.contains("?")) {
			// Separate Class and args
			String[] req = m_ressname.split("\\?");
			classe = req[0].replace("/", ".");

			// Separate args
			String[] args = req[1].split("&");

			// Set the arg HashMap
			setAgrs(args);
		} else {
			classe = m_ressname.replace("/", ".");
		}
		
		HttpRicmlet ricmlet = null;
		try {
			ricmlet = m_hs.getInstance(classe);
		} catch (ClassNotFoundException e) {
			// We didn't find the class and send an error
			resp.setReplyError(404, "Ricmlet not found");
		}
		ricmlet.doGet(this, (HttpRicmletResponse) resp);
	}

	private void setAgrs(String[] args) {
		String[] keyVal;
		
		// Foreach args
		for (int i = 0; i < args.length; i++) {
			// Separate key and value
			keyVal = args[i].split("=");
			// Add the argument in the HashMap
			arg.put(keyVal[0], keyVal[1]);
		}

	}

}
