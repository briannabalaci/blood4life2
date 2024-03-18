package serverUtils;

import exception.ServerException;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public abstract class AbstractServer {
    private final Integer port;
    private ServerSocket serverSocket = null;

    public AbstractServer(Integer port) {
        this.port = port;
    }

    protected abstract void processRequest(Socket client);

    public void start() throws ServerException {
        try {
            serverSocket = new ServerSocket(port);
            while(true) {
                System.out.println("Waiting for clients");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected");
                processRequest(clientSocket);
            }
        } catch (IOException ex) {
            throw new ServerException("Server start error!!! " + ex);
        } finally {
            stop();
        }
    }

    public void stop() throws ServerException{
        try {
            serverSocket.close();
        } catch (IOException e) {
            throw new ServerException("Server close method!!! " + e);
        }
    }
}
