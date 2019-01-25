package a10lib.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import a10lib.event.ClientConnectionEvent;
import a10lib.event.ObjectStreamEvent;

/**
 * ' A class representing server-side
 * 
 * @author Athensclub
 *
 */
public class Server {

    private ServerSocket serverSocket;

    private List<Client> clients;

    private ExecutorService threadPool;

    private Consumer<ObjectStreamEvent> onObjectRead;

    private Consumer<ClientConnectionEvent> onConnect, onDisconnect;

    /**
     * Create new instance of this server
     * 
     * @param port:
     *            the port of this Server
     * @param backlong:
     *            the backlong of this server and the maximum client count per given
     *            time of this server
     */
    public Server(int port, int backlong) {
	clients = Collections.synchronizedList(new ArrayList<>());
	threadPool = Executors.newFixedThreadPool(backlong);
	try {
	    serverSocket = new ServerSocket(port, backlong);
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
    
    /**
     *
     * @return The thread pool handling all of threads in this server
     */
    public ExecutorService getThreadPool() {
	return threadPool;
    }

    /**
     * Set the consumer that get called when object get read from the Client
     * connecting this server
     * 
     * @param onObjectRead:
     *            The consumer that get called when object get read from the Client
     *            connecting this server
     */
    public void setOnObjectRead(Consumer<ObjectStreamEvent> onObjectRead) {
	this.onObjectRead = onObjectRead;
    }

    /**
     * Set consumer that get called when a client connects to this Server
     * 
     * @param onConnect:
     *            The consumer that get called when a client connects to this Server
     */
    public void setOnConnect(Consumer<ClientConnectionEvent> onConnect) {
	this.onConnect = onConnect;
    }

    /**
     * Set consumer that get called when client disconnects this server
     * 
     * @param onDisconnect:
     *            The consumer that get called when client disconnects this server
     */
    public void setOnDisconnect(Consumer<ClientConnectionEvent> onDisconnect) {
	this.onDisconnect = onDisconnect;
    }

    /**
     * 
     * @return The consumer that get called when client disconnects this server
     */
    public Consumer<ClientConnectionEvent> getOnDisconnect() {
	return onDisconnect;
    }

    /**
     * 
     * @return The consumer that get called when object get read from the Client
     *         connecting this server
     */
    public Consumer<ObjectStreamEvent> getOnObjectRead() {
	return onObjectRead;
    }

    /**
     * @return The reference value of the list of all clients connecting to this
     *         server
     */
    public List<Client> getAllConnectingClients() {
	return clients;
    }

    /**
     * Send the given object to all client connecting to this server
     * 
     * @param obj:
     *            the object to be sent to all client connecting to this server
     */
    public void broadcast(Object obj) {
	clients.forEach(client -> {
	    client.sendObject(obj);
	});
    }

    /**
     * Start the server Should not be called more than once
     */
    public void start() {
	try {
	    while (true) {
		final Socket socket = serverSocket.accept();
		threadPool.execute(() -> {
		    Client client = new Client(socket, this);
		    clients.add(client);
		    onConnect.accept(new ClientConnectionEvent(System.currentTimeMillis(), client));
		    client.start();
		});
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	}

    }

}
