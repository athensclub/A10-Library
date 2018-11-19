package a10lib.net;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.function.Consumer;

/**
 * A class containing a socket streams represent a client both on server-side
 * and on client-side
 * 
 * @author Athensclub
 *
 */
public class Client {

    private ObjectInputStream in;

    private ObjectOutputStream out;

    private Socket socket;

    private Server server;

    private Consumer<ObjectStreamEvent> onObjectRead;

    private Consumer<ClientConnectionEvent> onDisconnect;

    /**
     * Create new instance of Client
     * 
     * @param s:
     *            the socket that will be used as stream of this Client
     * @param server:
     *            the server that is connecting to this client, null if it is
     *            client-side Client object
     */
    public Client(Socket s, Server server) {
	socket = s;
	this.server = server;
    }

    /**
     * 
     * @return The socket that is used for io stream of this Client
     */
    public Socket getSocket() {
	return socket;
    }

    /**
     * 
     * @param onDisconnect:
     *            Consumer that accept event when this client is disconnected from
     *            server
     */
    public void setOnDisconnect(Consumer<ClientConnectionEvent> onDisconnect) {
	this.onDisconnect = onDisconnect;
    }

    /**
     * 
     * @return Consumer that accept event when this client is disconnected from
     *         server
     */
    public Consumer<ClientConnectionEvent> getOnDisconnect() {
	return onDisconnect;
    }

    /**
     * Start piping io stream of this Client using its socket Should not be called
     * more than once
     */
    public void start() {
	try {
	    out = new ObjectOutputStream(socket.getOutputStream());
	    in = new ObjectInputStream(socket.getInputStream());
	} catch (IOException e) {
	    e.printStackTrace();
	}
	try {
	    while (true) {
		Object obj = in.readObject();
		ObjectStreamEvent event = new ObjectStreamEvent(obj, System.currentTimeMillis(), this);
		if (server != null) {
		    server.getOnObjectRead().accept(event);
		} else if (onObjectRead != null) {
		    onObjectRead.accept(event);
		}
	    }
	} catch (EOFException | SocketException e) {
	    ClientConnectionEvent event = new ClientConnectionEvent(System.currentTimeMillis(), this);
	    if (server != null) {
		server.getOnDisconnect().accept(event);
	    } else if (onDisconnect != null) {
		onDisconnect.accept(event);
	    }
	} catch (ClassNotFoundException | IOException e) {
	    e.printStackTrace();
	} finally {
	    try {
		out.close();
		in.close();
		socket.close();
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	    if (server != null) {
		server.getAllConnectingClients().remove(this);
	    }
	}
    }

    /**
     * Send Object to this Client output stream; If server-side: This method will
     * send object to the client-side If client-side: This method will send object
     * to the server-side
     * 
     * @param obj
     *            the object that will be sent to this Client output stream
     */
    public void sendObject(Object obj) {
	try {
	    out.writeObject(obj);
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    /**
     * Set Consumer that get called when the object is read by this socket input
     * stream; If server-side: The consumer will get called if client write object
     * into this Client If client-side: The consumer will get called if server write
     * object into this Client
     * 
     * @param onObjectRead:
     *            Consumer that get called when the object is read by this socket
     *            input stream
     */
    public void setOnObjectRead(Consumer<ObjectStreamEvent> onObjectRead) {
	this.onObjectRead = onObjectRead;
    }

    @Override
    public boolean equals(Object other) {
	if (other instanceof Client) {
	    return ((Client) other).socket.equals(socket);
	}
	if (other instanceof Socket) {
	    return ((Socket) other).equals(socket);
	}
	return false;
    }

}
