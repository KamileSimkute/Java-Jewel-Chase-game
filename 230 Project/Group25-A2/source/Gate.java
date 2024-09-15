/**
 * The Gate class implements an application that
 * creates a Gate and returns it's colour.
 * @author Kamile Simkute,checked by Joseph Ryan.
 * @version 1.0
 */
public class Gate extends Item {

	private String colour;
	private boolean impeding = true;
	private String image;
	private String redGate ="FinalAssets/Red_Gate(Closed).png";
	private String greenGate ="FinalAssets/Green_Gate(Closed).png";

	/**
	 * Create a gate and set a colour.
	 */
	public Gate(String colourParameter) {
		this.colour = colourParameter;
		switch (colourParameter){
			case "RED":
				this.image =redGate;
				break;
			case "GREEN":
				this.image =greenGate;
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
	 * Get the impeded square with item.
	 * @return The impeded Gate.
	 */
	public boolean getImpeding() {
		return impeding;

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
