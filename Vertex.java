
public class Vertex {

	private double x;
	private double y;
	private int number;
	
	public Vertex(double x, double y, int number) {
		
		this.x = x;
		this.y = y;
		this.number = number;
		
	}
	
	public double distanceTo(Vertex other) {
		
		double distance = Math.sqrt((this.x-other.x)*(this.x-other.x)+(this.y-other.y)*(this.y-other.y));
		return distance;
		
	}
	
	public double X() {
		
		return this.x;
		
	}
	
	public double Y() {
		
		return this.y;
		
	}
	
	public int number() {
		
		return this.number;
		
	}
	
}
