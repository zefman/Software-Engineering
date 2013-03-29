
public class Ant {	
	
	// All ant vairables
	private int id;
	private boolean is_Red;
	private int direction;
	private int resting;
	private boolean has_Food;
	private int brain_State;
	
	public Ant(int id, boolean is_Red) {
		// Initialise Fields
		this.id = id;
		this.is_Red = is_Red;
		direction = 0;
		resting = 0;
		has_Food = false;
		brain_State = 0;
	}
}
