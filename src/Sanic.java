import java.awt.Color;
import java.awt.Graphics;

public class Sanic
{
	public double x,y,xv,yv,runAccel,friction;
	
	public Sanic(double x,double y,double xv,double yv,double runAccel,double friction)
	{
		this.x = x;
		this.y = y;
		this.xv = xv;
		this.yv = yv;
		this.runAccel = runAccel;
		this.friction = friction;
	}
	
	public void tick()
	{
		x += xv;
		y += yv;
	}
	
	public void render(Graphics g)
	{
		g.setColor(Color.BLUE);
		g.fillRect((int) x, (int) y, 30, 30);
	}
}
