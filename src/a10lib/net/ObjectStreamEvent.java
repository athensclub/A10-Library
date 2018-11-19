package a10lib.net;

/**
 * Class representing event that occur when object is transfer between client
 * and server
 * 
 * @author Athensclub
 *
 */
public class ObjectStreamEvent {

    private Object object;

    private long when;

    private Client clientSource;

    /**
     * Create new instance of ObjectStreamEvent
     * 
     * @param obj:
     *            The object that would get transferred
     * @param w:
     *            The time that this event occurs
     * @param c:
     *            The client involved in providing this transfer
     */
    public ObjectStreamEvent(Object obj, long w, Client c) {
	object = obj;
	when = w;
	clientSource = c;
    }

    /**
     * 
     * @return The client involved in providing this transfer
     */
    public Client getClientSource() {
	return clientSource;
    }

    /**
     * 
     * @return The object that would get transferred
     */
    public Object getObject() {
	return object;
    }

    /**
     * 
     * @return The time that this event occurs
     */
    public long getWhen() {
	return when;
    }

}
