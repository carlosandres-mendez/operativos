import java.net.*;
import java.io.*;
public class DateServer
{
	public static void main(String[] args) {
		try {
			ServerSocket sock = new ServerSocket(1352);
			/* now listen for connections */
			while (true) {
				Socket client = sock.accept();
				PrintWriter pout = new
				PrintWriter(client.getOutputStream(), true);
				/* write the Date to the socket */
pout.println("HTTP/1.1 200 OK");
pout.println("Date: Mon, 23 May 2005 22:38:34 GMT");
pout.println("Content-Type: text/html; charset=UTF-8");
pout.println("Content-Length: 155");
pout.println("Last-Modified: Wed, 08 Jan 2003 23:11:55 GMT");
pout.println("Server: Apache/1.3.3.7 (Unix) (Red-Hat/Linux)");
pout.println("Accept-Ranges: bytes");
pout.println("Connection: close");
pout.println("");
pout.println("<html>");
pout.println("  <head>");
pout.println("    <title>An Example Page</title>");
pout.println("  </head>");
pout.println("  <body>");
pout.println("    <p>Hello World, this is a very simple HTML document.</p>");
pout.println("  </body>");
pout.println("</html>");

				/* close the socket and resume */
				/* listening for connections */
				client.close();
			}
		}
		catch (IOException ioe) {
			System.err.println(ioe);
		}
	}
}
