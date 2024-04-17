package searchengine;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

/**
 * The class represents an HTTP server which queries the dataset of webpages through reqeusts and forwards back the results.
 */
public class WebServer {
	private static final int BACKLOG = 0;
	private static final Charset CHARSET = StandardCharsets.UTF_8;

	private HttpServer server;
	private int port;
	private QueryEngine queryEngine;


	/**
	 * Instantiates the WebServer object.
	 * @param port The network port where the server listens for incoming HTTP requests.
	 * @param queryEngine An instance of a class implementing the QueryEngine interface, to process query requests from HTTP requests.
	 * @throws IOException If the socket address is not available.
	 */
	WebServer(int port, QueryEngine queryEngine) throws IOException {
		this.queryEngine = queryEngine;
		this.port = port;
		this.server = HttpServer.create(new InetSocketAddress(port), BACKLOG);
		this.addContexts();
	}


	/**
	 * Gets the currently used port number by the server.
	 * @return The port number used by the server.
	 */
	public int getPort() {
		return this.port;
	}


	private void addContexts() {
		this.server.createContext("/", io -> respond(io, 200, "text/html", fileToBytes("web/index.html")));
		this.server.createContext("/favicon.ico", io -> respond(io, 200, "image/x-icon", fileToBytes("web/favicon.ico")));
		this.server.createContext("/code.js", io -> respond(io, 200, "application/javascript", fileToBytes("web/code.js")));
		this.server.createContext("/style.css", io -> respond(io, 200, "text/css", fileToBytes("web/style.css")));
		this.server.createContext("/search", io -> respond(io, 200, "application/json", queryResultToBytes(getTermFromURI(io))));
	}


	private void printStartupMessage() {
		String msg = " WebServer running on http://localhost:" + port + " ";
		System.out.println("╭"+"─".repeat(msg.length())+"╮");
		System.out.println("│"+msg+"│");
		System.out.println("╰"+"─".repeat(msg.length())+"╯");
	}


	/**
	 * Initializes the server to listen for incoming requests. Prints up a successful startup message.
	 */
	public void startServer() {
		this.server.start();
		this.printStartupMessage();
	}

	
	/**
	 * Stops the server from listening to incoming requests.
	 * @param num The number of seconds to wait until all HTTP exchanges have finished.
	 */
	public void stopServer(int num) {
		this.server.stop(num);
	}
	

	private String getTermFromURI(HttpExchange io) {
		try {
			return io.getRequestURI().getRawQuery().split("=")[1];
		}
		catch (IndexOutOfBoundsException e) {
			return "";
		}
	}


	private byte[] queryResultToBytes(String term) {
		List<String> responseList = new ArrayList<>();
		for (WebPage page : this.queryEngine.query(term)) {
			responseList.add(String.format("{\"url\": \"%s\", \"title\": \"%s\"}", page.getUrl(), page.getSiteName()));
		}
		byte[] bytes = responseList.toString().getBytes(CHARSET);
		return bytes;
	}


	private byte[] fileToBytes(String filename) {
		try {
			return Files.readAllBytes(Paths.get(filename));
		} catch (IOException e) {
			e.printStackTrace();
			return new byte[0];
		}
	}


	private void respond(HttpExchange io, int httpResonseCode, String mime, byte[] response) {
		try {
			io.getResponseHeaders().set("Content-Type", String.format("%s; charset=%s", mime, CHARSET.name()));
			io.sendResponseHeaders(httpResonseCode, response.length);
			io.getResponseBody().write(response);
		} 
		catch (Exception e) {
		} 
		finally {
			io.close();
		}
	}
}