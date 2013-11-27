package decaf.error;

import decaf.Location;

/**
 * example：integer literal 112233445566778899 is too large<br>
 * PA1
 */
public class DoubleTooLargeError extends DecafError {

	private String val;

	public DoubleTooLargeError(Location location, String val) {
		super(location);
		this.val = val;
	}

	@Override
	protected String getErrMsg() {
		return "double literal " + val + " is too large";
	}

}
