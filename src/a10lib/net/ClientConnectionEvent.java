package a10lib.net;

/**
 * Event class about connections
 * 
 * @author Athensclub
 *
 */
public class ClientConnectionEvent {

    private long when;

    private Client client;

    /**
     * Create new instance of ClientConnectionEvent
     * 
     * @param when:
     *            the time when connection event occurs
     * @param client:
     *            the client involved in this connection
     */
    public ClientConnectionEvent(long when, Client client) {
	this.when = when;
	this.client = client;
    }

    /**
     * 
     * @return Client involved in this connection
     */
    public Client getClient() {
	return client;
    }

    /**
     * 
     * @return The time when connection event occurs
     */
    public long getWhen() {
	return when;
    }

}
