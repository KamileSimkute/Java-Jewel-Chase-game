/**
 * The Item class implements an application that
 * returns if an Item is being collected.
 * @author Kamile Simkute, checked by Joseph Ryan.
 * @version 1.0
 */
public abstract class Item {

	private Boolean impeding = false;
	private String image;
	/**
	 * Allows for an item's collection.
	 * @param isPlayer isPlayer checks if player picks up an Item.
	 */
	public void action(boolean isPlayer) {
		impeding = true;
	}
	/**
	 * Getter for the items image
	 * @return image path
	 */
	public String getSprite(){
        return this.image;
	}
    /**
	 * Getter for the item's colour, null for most
	 * @return colour
	 */
	public String getColour(){
		return "";
	}
	/**
	 * Get the impeded square with item.
	 * @return The Impeded Item.
	 */
	public boolean getImpeding() {
		return impeding;
	}
}
