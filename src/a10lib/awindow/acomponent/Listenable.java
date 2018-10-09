package a10lib.awindow.acomponent;

import java.util.ArrayList;

public interface Listenable {

	/**
	 * Add listener to this component
	 * 
	 * @param r:
	 *            the listener to add to this component
	 */
	public void addListener(Runnable r);

	/**
	 * Remove listener from this component
	 * 
	 * @param r:
	 *            the listener to remove from this component
	 */
	public void removeListener(Runnable r);

	/**
	 * Get all of listeners of this components
	 * 
	 * @return all of listeners of this components
	 */
	public ArrayList<Runnable> getListeners();

}
