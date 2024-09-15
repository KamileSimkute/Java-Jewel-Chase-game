/**
 * The Collectible class implements an application that returns if a collectible
 * is being collected.
 * @author Kamile Simkute, checked by Joseph Ryan.
 * @version 1.0
 */
public class Collectable extends Item {

	private int value;

	/**
	 * Allows for an item's collection.
	 * 
	 * @param isPlayer isPlayer checks if player picks up the Item.
	 * @return value The Value of Item.
	 */
	public int getValue(boolean isPlayer) {
		if (isPlayer == false) {
			return -value;
		}
		return value;
	}
}
