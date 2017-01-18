
public class Blob {
	private double x,y,xv,yv;
	private double runAcceleration;
	
	public Blob(double x, double y, double xv,double yv,double runAcceleration)
	{
		this.x = x;
		this.y = y;
		this.xv = xv;
		this.yv = yv;
		this.runAcceleration = runAcceleration;
		
		System.out.println("hi");
	}

	public double getxv() {
		return xv;
	}

	public void setxv(double xv) {
		this.xv = xv;
	}

	public double getyv() {
		return yv;
	}

	public void setyv(double yv) {
		this.yv = yv;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getRunAcceleration() {
		return runAcceleration;
	}

	public void setRunAcceleration(double runAcceleration) {
		this.runAcceleration = runAcceleration;
	}
	
	
}
