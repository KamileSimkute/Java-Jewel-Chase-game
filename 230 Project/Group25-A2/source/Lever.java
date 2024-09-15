/**
 * The Lever class implements an application that
 * creates a Lever and returns it's colour.
 * @author Kamile Simkute,checked by Joseph Ryan.
 * @version 1.0
 */
public class Lever extends Collectable {
	private String colour;
	private String image;
	private String redLever = "FinalAssets/Red_Lever(on).png";
	private String greenLever = "FinalAssets/Green_lever(on).png";
	/**
	 * Create a lever and set a colour.
	 */
	public Lever(String colourParameter) {
		this.colour = colourParameter;
		switch (colourParameter){
			case "RED":
				this.image =redLever;
				break;
			case "GREEN":
				this.image =greenLever;
				break;
			default:
		}
	}
	
	/** 
	 * @return String
	 */
	@Override
	public String getSprite(){
        return this.image;
	}

	/**
	 * Get the gate's colour.
	 * @return The colour.
	 */
	@Override
	public String getColour() {
		return this.colour;
	}

	
	/** 
	 * @return String
	 */
	@Override
	public String toString() {
		return (this.getColour() + this.getClass().getSimpleName());
	}
}
