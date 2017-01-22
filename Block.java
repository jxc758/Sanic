import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Block
{
	public int x,y,w,h;
	public Block (int x, int y, int w, int h)
	{
		this.x = x;
		this.y = y; 
		this.w = w;
		this.h = h;
	}
	public void render (Graphics g)
	{
		g.setColor(Color.RED);
		g.fillRect(x,y,w,h);
	}
	public boolean sanicIntersects (Sanic sanic)
	{
		Rectangle b = new Rectangle(x,y,w,h);
		Rectangle s = new Rectangle((int) sanic.x,(int)sanic.y,30,30);
		return b.intersects(s);
	}
			
}
