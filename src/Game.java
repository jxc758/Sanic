import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

public class Game extends JPanel implements Runnable,KeyListener
{
	private Blob blob;
	private final int blobWidth = 20;
	private final int blobHeight = 20;
	private Thread gameThread;
	private boolean leftDown = false;
	private boolean rightDown = false;
	
	public Game()
	{
		gameThread = new Thread(this);
		gameThread.run();	
	}
	
	public void run()
	{
		this.addKeyListener(this);
		this.setFocusable(true);
		this.requestFocusInWindow();
		blob = new Blob(Sanic.frame_width/2,Sanic.frame_height/2,0,0,1);
		
		
		System.out.println("we runnin");
	}
	
	public void paint(Graphics g)
	{
		g.clearRect(0,0,800,450);
		g.drawRect((int)blob.getX(),(int)blob.getY(), blobWidth, blobHeight);
		g.setColor(Color.BLUE);
		g.fillRect((int)blob.getX(),(int)blob.getY(), blobWidth, blobHeight);
		
	}
	
	public void update()
	{
		System.out.println(blob.getxv());
		repaint();
	}

	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode())
    	{
    	case KeyEvent.VK_LEFT:
    		leftDown = true;
    		break;
    		
    	case KeyEvent.VK_RIGHT:
    		rightDown = true;
    		break;
    	} 
	}
	  
    public void keyReleased(KeyEvent e) {
    	switch(e.getKeyCode())
    	{
    	case KeyEvent.VK_LEFT:
    		blob.setxv(blob.getxv() - blob.getRunAcceleration());
    		blob.setX(blob.getX() + blob.getxv());
    		update();
    		leftDown = false;
    		break;
    		
    	case KeyEvent.VK_RIGHT:
    		blob.setxv(blob.getxv() + blob.getRunAcceleration());
    		blob.setX(blob.getX() + blob.getxv());
    		update();
    		rightDown = false;
    		break;
    	}
    }
    
	@Override
	public void keyTyped(KeyEvent e) {
		// never used :( 
		
	}
}

