import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

public abstract class GameObject
{
	public double x,y,w,h;
	public double xv,yv = 0;
	public Rectangle2D hitbox;
	
	public GameObject(double x, double y,double w, double h)
	{
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		
		hitbox = new Rectangle2D.Double(x, y, w, h);
	}
	
	public abstract void render(Graphics g);
	public abstract void tick();
	
	public boolean collidesWith(GameObject gameObject)
	{
		return (hitbox.intersects(gameObject.hitbox));
	}
}
