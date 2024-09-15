/**
 * The Bomb class implements an application that
 * creates a Bomb and triggers it's detonation. 
 * @author Kamile Simkute, checked by Joseph Ryan
 * @version 1.0
 */
public class Bomb extends Item {

	public boolean bombTriggered = false;
	public boolean isPlayer = false;
	private boolean impeding = true;
	private int timeRemaining = 3;
	private String litSprite = "FinalAssets/Bomb(lit).png";
	private String unlitSprite ="FinalAssets/Bomb(unlit).png";
	private String image;

	/**
	 * Create a bomb.
	 */
	public Bomb() {
		this.image = unlitSprite;
	}

	/**
	 * Get the impeded square with Bomb.
	 * @return The Impeded bomb.
	 */
	public boolean getImpeding() {
		return impeding;
	}

	/**
	 * Get the bomb's detonation.
	 * @return The bomb's Trigger.
	 */
	public boolean isTriggered() {
		return bombTriggered;
	}

	/**
	 * Creates a new value to remaining time.
	 * @param timeRemaining This changes the time of Bomb.
	 */
	public void tick() {
		timeRemaining -= 1;
	}

	/**
	 * Get the time remaining.
	 * @return The time remaining.
	 */
	public int getTimeRemaining() {
		return this.timeRemaining;
	}

	/**
	 * Trigger the bomb.
	 * @param isPlayer Checks if player detonates the bomb.
	 */
	public void action(boolean isPlayer) {
		bombTriggered = true;
		this.image = litSprite;
	}
	
	/** 
	 * @return String
	 */
	@Override
	public String getSprite(){
        return this.image;
	}

	
	/** 
	 * @return String
	 */
	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
}
