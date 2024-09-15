/**
 * The Loot class implements an application that
 * creates loot and returns it's value.
 * @author Kamile Simkute
 * @version 1.0
 */
public class Loot extends Collectable {
	private final int lootValue;
	private final int SILVER_COIN_VALUE = 10;
	private final int GOLD_COIN_VALUE = 30;
	private final int RUBY_VALUE = 50;
	private final int DIAMOND_VALUE = 100;
	private String name;
	private String image;
	private String goldImage ="FinalAssets/GoldCoin.png";
	private String silverImage ="FinalAssets/SilverCoin.png";
	private String rubyImage = "FinalAssets/Ruby.png";
	private String diamondImage = "FinalAssets/Diamond.png";
	public Loot(String name) {
        this.name = name;
		switch (name) {
			case "SilverCoin":
				this.lootValue = this.SILVER_COIN_VALUE;
				this.image = silverImage;
				break;
			case "GoldCoin":
				this.lootValue = this.GOLD_COIN_VALUE;
				this.image = goldImage;
				break;
			case "Ruby":
				this.lootValue = this.RUBY_VALUE;
				this.image = rubyImage;
				break;
			case "Diamond":
				this.lootValue = this.DIAMOND_VALUE;
				this.image = diamondImage;
				break;
			default:
				throw new IllegalArgumentException("invalid collectable passed");
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
	 * Get the loot value.
	 * @return The loot's value.
	 */
	@Override
	public int getValue(boolean isPlayer) {
		if (isPlayer == false) {
			return lootValue;
		}
		return lootValue;
	}

	
	/** 
	 * @return String
	 */
	@Override
	public String toString() {
		return this.name;
	}

}
