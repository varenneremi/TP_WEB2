package httpserver.itf.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

import httpserver.itf.HttpResponse;
import httpserver.itf.HttpRicmlet;
import httpserver.itf.HttpRicmletRequest;
import httpserver.itf.HttpRicmletResponse;
import httpserver.itf.HttpSession;

public class HttpRicmletRequestImpl extends HttpRicmletRequest {
	
	BufferedReader br;
	String classe;
	HashMap<String, String> arg;
	HashMap<String, String> cookie;

	public HttpRicmletRequestImpl(HttpServer hs, String method, String ressname, BufferedReader br) throws IOException {
		super(hs, method, ressname);
		this.br = br;
		arg = new HashMap<String, String>();
		cookie = new HashMap<String, String>();
	}

	@Override
	public HttpSession getSession() {
		return m_hs.getSession(cookie.get("sessionID"));
	}

	@Override
	public String getArg(String name) {
		return arg.get(name);
	}

	@Override
	public String getCookie(String name) {
		return cookie.get(name);
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
		
		foundCookie();
		
		ricmlet.doGet(this, (HttpRicmletResponse) resp);
	}

	private void foundCookie() throws IOException {
		String line = null;
		String[] c = null;
		while(!((line = br.readLine()).equals(""))) {
			if(line.startsWith("Cookie: ")) {
				line = line.substring("Cookie: ".length());
				c = line.split(";");
				for (String string : c) {
					String[] val = string.split("=");
					setCookie(val[0], val[1]);
				}
				
			}
		}
	}

	private void setCookie(String key, String val) {
		cookie.put(key, val);
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
