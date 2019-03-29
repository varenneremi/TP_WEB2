package httpserver.itf.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;

import httpserver.itf.HttpRequest;
import httpserver.itf.HttpResponse;

public class HttpStaticRequest extends HttpRequest {
	static final int BUF_SZ = 1024;
	static final String DEFAULT_FILE = "index.html";
	
	public HttpStaticRequest(HttpServer hs, String method, String ressname) throws IOException {
		super(hs, method, ressname);
	}
	
	public void process(HttpResponse resp) throws Exception {
		File m_file = null;	// asked file
		FileInputStream fis = null;	
		PrintStream ps = null;
		File folder = m_hs.getFolder();	// folder where we can found the file

		try {
			m_file = new File(folder + m_ressname);
			fis = new FileInputStream(m_file);
			
			// We found the file and send it
			resp.setReplyOk();
			resp.setContentLength((int) m_file.length());
			resp.setContentType(m_ressname);
			ps = resp.beginBody();
			sendFile(ps, fis);
			
		} catch (FileNotFoundException e) {
			// We didn't find the file and send an error
			resp.setReplyError(404, e.toString());
		}
		
	}

	private void sendFile(PrintStream ps, FileInputStream fis) {
		int n = 0;
		byte[] buffer = new byte[BUF_SZ];
		
		try {
			while((n = fis.read(buffer)) >= 0) {
				
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
	}

}
