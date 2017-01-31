import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

public class Game extends Canvas implements Runnable
{
	//General
	public static final int WIDTH = 800, HEIGHT = (WIDTH / 16) * 9;
	private Thread thread;
	private boolean running = false;
	private KeyInput key = new KeyInput();
	
	//Game
	private final double gravity = 0.5;
	public final double friction = 0.97;
	private Sanic sanic;
	private ArrayList<Block> blocks = new ArrayList<Block>();

	public static void main(String args[])
	{
		new Game();
	}

	public Game()
	{
		this.setFocusable(true);
		this.addKeyListener(key);
		
		sanic = new Sanic(400,0,30,30);
		
		Block block1 = new Block(-WIDTH,350,3*WIDTH,25);
		blocks.add(block1);
		
		Block block2 = new Block(50,200,200,25);
		blocks.add(block2);

		Block block3 = new Block(WIDTH - 200,150,100,100);
		blocks.add(block3);

		Block block4 = new Block(200,100,30,30);
		blocks.add(block4);
		
		new Window(WIDTH, HEIGHT, "Sanic!", this);
	}

	private void tick()
	{
		sanic.xv *= friction;
		sanic.yv += gravity;
		
		if (key.leftDown)
		{
			sanic.xv -= sanic.runAccel; 
		}
		else if (key.rightDown)
		{
			sanic.xv += sanic.runAccel;
		}
			
		if(key.upDown && testTouchingGround(sanic))
		{
			sanic.yv = -15;
		}
		
		sanic.tick();
		
		for(int i = 0;i<blocks.size();i++)
		{
			blockCollision(sanic,blocks.get(i));
		}
	}
	
	private void render()
	{
		//create buffer strategy to stop flickering
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null)
		{
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		//create black background
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		//render objects
		sanic.render(g);
		
		for(int i=0;i<blocks.size();i++)
		{
			blocks.get(i).render(g);
		}
		
		g.dispose();
		bs.show();
	}
	
	public boolean testTouchingGround(GameObject obj)
	{
		Rectangle2D objBot = new Rectangle2D.Double(obj.x, obj.y + obj.h, obj.w, 2);
		
		for(int i=0;i<blocks.size();i++)
		{
			if(objBot.intersects(blocks.get(i).hitbox))
			{
				return true;
			}
		}
		
		return false;
	}
	
	public void blockCollision(GameObject obj,Block block)
	{
		//create new rectangle based on where object will be
		Rectangle2D newHitbox = new Rectangle2D.Double(obj.x + obj.xv,obj.y + obj.yv,obj.w,obj.h);
		
		if(newHitbox.intersects(block.hitbox))
		{
			double xDistance,yDistance,xTime,yTime;
			
			if(obj.xv > 0)
			{
			   //Down Right
			   if(obj.yv > 0)
			   {
				   xDistance = (newHitbox.getX() + newHitbox.getWidth()) - (block.x);
				   yDistance = (newHitbox.getY() + newHitbox.getHeight()) - (block.y);
				   xTime = Math.abs(xDistance/obj.xv);
				   yTime = Math.abs(yDistance/obj.yv);
				   
				   if (xTime < yTime)
				   {
					   obj.x = block.x - obj.w - 0.1;
					   obj.xv = 0;
				   }
				   else
				   {
					   obj.y = block.y - obj.h - 0.1;
					   obj.yv = 0;
				   }
			   }
			   //Up Right
			   else if(obj.yv < 0)
			   {
					xDistance = (newHitbox.getX() + newHitbox.getWidth()) - (block.x);
					yDistance = (block.y + block.h) - newHitbox.getY();
					xTime = Math.abs(xDistance/obj.xv);
					yTime = Math.abs(yDistance/obj.yv);
					
					if(xTime < yTime)
					{
						obj.x = block.x - obj.w - 0.1;
						obj.xv = 0;
					}
					else
					{
						obj.y = block.y + block.h + 0.1;
						obj.yv = 0;
					}
			   }
			   //Right
			   else if(obj.yv == 0)
			   {
				   obj.x = block.x - obj.w - 0.1;
				   obj.xv = 0;
			   }
			}
			else if(obj.xv < 0)
			{
				//Down Left
				if(obj.yv > 0)
				{
					xDistance = (block.x + block.w) - newHitbox.getX();
					yDistance = (newHitbox.getY() + newHitbox.getHeight()) - block.y;
					xTime = Math.abs(xDistance/obj.xv);
					yTime = Math.abs(yDistance/obj.yv);
					
					if(xTime < yTime)
					{
						obj.x = block.x + block.w + 0.1;
						obj.xv = 0;
					}
					else
					{
						obj.y = block.y - obj.h - 0.1;
						obj.yv = 0;
				   	}
				}
				//Up Left
				else if(obj.yv < 0)
				{
					xDistance = (block.x + block.w) - newHitbox.getX();
					yDistance = (block.y + block.h) - newHitbox.getY();
					xTime = Math.abs(xDistance/obj.xv);
					yTime = Math.abs(yDistance/obj.yv);				
					
					if(xTime < yTime)
					{
						obj.x = block.x + block.w + 0.1;
						obj.xv = 0;
					}
					else
					{
						obj.y = block.y + block.h + 0.1;
						obj.yv = 0;
					}
				}
				//Left
				else if(obj.yv == 0)
				{
					obj.x = block.x + block.w + 0.1;
					obj.xv = 0;
				}
			}
			else if(obj.xv == 0)
			{
				//Up
				if(obj.yv < 0)
				{
					obj.y = block.y + block.h + 0.1;
					obj.yv = 0;
				}
				
				//Down
				else if(obj.yv > 0)
				{
					obj.y = block.y - obj.h - 0.1;
					obj.yv = 0;
				}	
			}
		}
	}
	
	@Override
	public void run()
	{
		long lastTime = System.nanoTime();
        //amount of ticks
		double amountOfTicks = 60.0;
        //length of time in nanoseconds for each tick
        double ns = 1000000000 / amountOfTicks;
        
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        int ticks = 0;
        
        while(running)
        {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            
            while(delta >=1)
            {
                tick();
                ticks++;
                delta--;
            }
            
            if(running)render();
            
            frames++;
            
            if(System.currentTimeMillis() - timer > 1000)
            {
                timer += 1000;
                System.out.println("FPS: "+ frames);
                frames = 0;
                System.out.println("Ticks: "+ticks);
                ticks = 0;
            }
        }
        
        stop();
    }
	
	public synchronized void start()
	{
		thread = new Thread(this);
		thread.start();
		running = true;

	}

	public synchronized void stop()
	{
		try
		{
			thread.join();
			running = false;
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	
}
