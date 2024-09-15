/**
 * The Door class implements an application that
 * creates a door.
 * @author Kamile Simkute, checked by Joseph Ryan.
 * @version 1.0
 */
public class Door extends Item{

    private boolean doorOpen = false;
	private String image;
	private String closedimage = "FinalAssets/Door(closed).png";
	private String openImage = "FinalAssets/Door(open).png";

	public Door(){
		this.image = closedimage;
	}
	@Override
	public String getSprite(){
        return this.image;
	}
    /**
     * Get if door is open
     * @return Whether door is open. 
     */
	public boolean isOpen(){
		return doorOpen;
	}
	/**
	 * Allows for a Door's collection.
	 */
	public void open(){
		this.doorOpen = true;
		this.image = openImage;
	}


	@Override
    public String toString(){
        return this.getClass().getSimpleName();
    }

}
