package a10lib.awindow;

@Deprecated
public interface KeyTracker {

	/**
	 * Check whether the given keycode is being pressed 
	 * @param code: Keycode to check whether it is being pressed
	 * @return whether the given keycode is being pressed 
	 */ 
	public boolean isHoldingKey(int code);
	
}
