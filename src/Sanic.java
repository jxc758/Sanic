import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Sanic extends GameObject
{
	public double runAccel = 0.5;
	
	public Sanic(double x,double y,double w,double h)
	{
		super(x,y,w,h);
	}
	
	public void tick()
	{
		x += xv;
		y += yv;
		hitbox.setRect(x,y,w,h);
	}
	
	public void render(Graphics g)
	{
		Graphics2D g2d = (Graphics2D)g;
		g2d.setColor(Color.BLUE);
		g2d.fill(hitbox);
	}
}
