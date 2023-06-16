import java.net.*;
import java.io.*;

public class Servidor {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
	    while(true){
            	clientSocket = serverSocket.accept();
            	out = new PrintWriter(clientSocket.getOutputStream(), true);
            	in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            	String greeting = in.readLine();
            	if ("hola servidor".equals(greeting))
            	    out.println("hola cliente");
            	else
            	    out.println("ok");
	    }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    public void stop() {
        try {
            in.close();
            out.close();
            clientSocket.close();
            serverSocket.close();
        } catch (IOException e) {
	    System.out.println(e.getMessage());
        }

    }

    public static void main(String[] args) {
        Servidor server = new Servidor();
        server.start(6655);
    }
}
