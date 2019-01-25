package a10lib.net;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.function.Consumer;

import a10lib.event.ClientConnectionEvent;
import a10lib.event.ObjectStreamEvent;

/**
 * 
 * A class representing client-side connection
 * 
 * @author Athensclub
 *
 */
public class Connection {

    private String ip;

    private int port;

    private Client client;

    private Consumer<ObjectStreamEvent> onObjectRead;

    private Consumer<ClientConnectionEvent> onConnectionRefused, onDisconnect;

    /**
     * Create new instance of Connection
     * 
     * @param ip:
     *            the ip address that this Connection is going to connect to
     * @param port:
     *            the local port that this Connection is going to connect to
     */
    public Connection(String ip, int port) {

	this.ip = ip;
	this.port = port;

    }

    /**
     * Set Consumer when the object is read by this Connection Client. The consumer
     * will get called if server write object into this Connection's Client
     * 
     * @param onObjectRead:
     *            Consumer that get called when server write object into this
     *            Connection's Client
     */
    public void setOnObjectRead(Consumer<ObjectStreamEvent> onObjectRead) {
	this.onObjectRead = onObjectRead;
	if (client != null) {
	    client.setOnObjectRead(onObjectRead);
	}
    }

    /**
     * Set consumer that get called when this Connection's Client get Disconnected
     * from the Server
     * 
     * @param onDisconnect:
     *            Consumer that get called when this Connection's Client get
     *            Disconnected from the Server
     */
    public void setOnDisconnect(Consumer<ClientConnectionEvent> onDisconnect) {
	this.onDisconnect = onDisconnect;
	if (client != null) {
	    client.setOnDisconnect(onDisconnect);
	}
    }

    /**
     * 
     * @return Consumer that get called when this Connection's Client get
     *         Disconnected from the Server
     */
    public Consumer<ClientConnectionEvent> getOnDisconnect() {
	return onDisconnect;
    }

    /**
     * Set consumer that get called when this connection connect request is refused
     * 
     * @param onConnectionRefused:
     *            Consumer that get called when this connection connect request is
     *            refused
     */
    public void setOnConnectionRefused(Consumer<ClientConnectionEvent> onConnectionRefused) {
	this.onConnectionRefused = onConnectionRefused;
    }

    /**
     * 
     * @return Consumer that get called when this connection connect request is
     *         refused
     */
    public Consumer<ClientConnectionEvent> getOnConnectionRefused() {
	return onConnectionRefused;
    }

    /**
     * 
     * @return Consumer that get called when server write object into this
     *         Connection's Client
     */
    public Consumer<ObjectStreamEvent> getOnObjectRead() {
	return onObjectRead;
    }

    /**
     * Start connecting this Connection to the server
     * Should not be called more than once
     */
    public void start() {
	try {
	    client = new Client(new Socket(InetAddress.getByName(ip), port), null);
	    client.setOnObjectRead(onObjectRead);
	    client.setOnDisconnect(onDisconnect);
	    client.start();
	} catch (ConnectException e) {
	    if (onConnectionRefused != null) {
		onConnectionRefused.accept(new ClientConnectionEvent(System.currentTimeMillis(), client));
	    }
	} catch (UnknownHostException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    /**
     * Send the object to the server
     * 
     * @param obj:
     *            object to be sent to the server
     */
    public void sendObject(Object obj) {
	client.sendObject(obj);
    }

    /**
     * 
     * @return The ip address that this Connection is attempting to connect or is
     *         connecting to
     */
    public String getIP() {
	return ip;
    }

    /**
     * 
     * @return The local port that this Connection is attempting to connect or is
     *         connecting to
     */
    public int getPort() {
	return port;
    }

    /**
     * Get Client object involved in this connection
     * @return
     */
    public Client getClient() {
	return client;
    }

}
