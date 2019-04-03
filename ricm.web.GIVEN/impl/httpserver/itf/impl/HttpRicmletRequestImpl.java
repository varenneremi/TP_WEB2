package httpserver.itf.impl;

import java.io.IOException;

import httpserver.itf.HttpResponse;
import httpserver.itf.HttpRicmlet;
import httpserver.itf.HttpRicmletRequest;
import httpserver.itf.HttpRicmletResponse;
import httpserver.itf.HttpSession;

public class HttpRicmletRequestImpl extends HttpRicmletRequest {

	String classe;
	String[] arg;
	
	public HttpRicmletRequestImpl(HttpServer hs, String method, String ressname) throws IOException {
		super(hs, method, ressname);
	}

	@Override
	public HttpSession getSession() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getArg(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCookie(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void process(HttpResponse resp) throws Exception {
		if(m_ressname.contains("?")) {
			String[] req = m_ressname.split("?");
			classe = req[0].replace("/", ".");
			arg = req[1].split("&");
		} else {
			classe = m_ressname.replace("/", ".");
		}
		
		HttpRicmlet ricmlet = (HttpRicmlet) Class.forName(classe).newInstance();
		ricmlet.doGet(this, (HttpRicmletResponse) resp);
	}

}
