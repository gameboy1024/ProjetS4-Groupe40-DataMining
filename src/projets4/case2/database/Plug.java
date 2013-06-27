package projets4.case2.database;

public class Plug {
	
	private int id;
	private double power;

	public Plug(int id, double power) {
		this.id = id;
		this.power = power;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getPower() {
		return power;
	}

	public void setPower(double power) {
		this.power = power;
	}

}
