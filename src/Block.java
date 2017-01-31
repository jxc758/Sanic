import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Block extends GameObject
{
	public Block (double x, double y, double w, double h)
	{
		super(x,y,w,h);
	}
	
	public void render (Graphics g)
	{
		Graphics2D g2d = (Graphics2D)g;
		g2d.setColor(Color.GREEN);
		g2d.fill(hitbox);
	}

	@Override
	public void tick()
	{
		
	}			
}
