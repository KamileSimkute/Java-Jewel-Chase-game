/**
 * The Clock class implements an application that
 * creates a Clock and triggers it's timer.
 * @author Kamile Simkute, checked by Joseph Ryan.
 * @version 1.0
 */
public class Clock extends Collectable {

	private int seconds;
	private String image = "FinalAssets/Red_Clock.png";

	/**
	 * Create a Clock that counts in seconds.
	 */
	public Clock(int seconds) {
		this.seconds = seconds;
	}

	public int getSeconds() {
		return seconds;
	}
	@Override
	public int getValue(boolean isPlayer) {
		if (isPlayer == false) {
			return seconds;
		}
		return seconds;
	}
	@Override
	public String getSprite(){
        return this.image;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
}
