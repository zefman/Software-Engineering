
public class Cell {

	private int foodContained;
	private int redPheromone;
	private int blackPheromone;
	Type type;
	
	public Cell() {
		
	}
	
	public enum Type {
		CLEAR, ROCKY, REDANTHILL, BLACKANTHILL, REDANT, BLACKANT, FOOD, REDWITHFOOD, BLACKWITHFOOD
	}
	
	public void setType(Type type) {
		this.type = type;
		
	}
	
	public void giveFood() {
		foodContained=5;
	}
	
	public Type getType() {
		return type;
	}
	
	public int getRedPheronome() {
		return redPheromone;
	}
	
	public void setRedPheromone(int pheromone) {
		redPheromone = pheromone;
	}
	
	public int getBlackPheronome() {
		return blackPheromone;
	}
	
	public void setBlackPheromone(int pheromone) {
		blackPheromone = pheromone;
	}
	
	public void reduceFood() {
		foodContained--;
	}
	
}
