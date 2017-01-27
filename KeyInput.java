import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter
{
	public boolean leftDown = false;
	public boolean rightDown = false;
	public boolean upDown = false;
	
	public KeyInput()
	{
		
	}
	
	public void keyPressed(KeyEvent e)
	{
		int key = e.getKeyCode();
		
		switch(key)
		{
			case KeyEvent.VK_LEFT:
				leftDown = true;
				break;
				
			case KeyEvent.VK_RIGHT:
				rightDown = true;
				break;
			
			case KeyEvent.VK_UP:
				upDown = true;
				break;
		}
	}
	
	public void keyReleased(KeyEvent e)
	{
		int key = e.getKeyCode();
		
		switch(key)
		{
			case KeyEvent.VK_LEFT:
				leftDown = false;
				break;
				
			case KeyEvent.VK_RIGHT:
				rightDown = false;
				break;
			
			case KeyEvent.VK_UP:
				upDown = false;
				break;
		}
	}

}
