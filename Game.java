import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable
{
	public static final int WIDTH = 800, HEIGHT = (WIDTH / 16) * 9;
	private Thread thread;
	private boolean running = false;
	
	private Sanic sanic;
	private Block block;
	private Block floor;
	private KeyInput key;
	private Block[] blocks = new Block[2];

	public static void main(String args[])
	{
		new Game();
	}

	public Game()
	{
		this.setFocusable(true);
		key = new KeyInput();
		this.addKeyListener(key);
		block = new Block(200,50,30,80);
		floor = new Block(10,300,900,100);
		blocks[0] = block;
		blocks[1] = floor;
		sanic = new Sanic(100,100,0,0,1,0.97);
		new Window(WIDTH, HEIGHT, "Sanic!", this);
	}

	private void tick()
	{
		if (key.leftDown)
		{
			sanic.xv -= sanic.runAccel; 
		}
		else if (key.rightDown)
		{
			sanic.xv += sanic.runAccel;
		}
		else if (key.upDown)
		{
			sanic.yv += -0.3;
		}
		
		sanic.xv *= sanic.friction;
		sanic.yv += 0.1;
		sanic.tick();
	}
	
	private void render()
	{
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null)
		{
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		sanic.render(g);
		block.render(g);
		floor.render(g);
		for (int i =0;i<blocks.length;i++)
		{
				block = blocks[i];
				if (block.sanicIntersects(sanic))
				{
					//System.out.println("sanic" + sanic.x);
					//System.out.println("block" + block.x);
					//if ((int)sanic.x + 30 > block.x)
					//{
					//	System.out.println("HIT");
					//	sanic.setX(block.x - 30);
					//	sanic.setXv(-sanic.xv/2);
					//}
					

					 if(sanic.y  < block.y)
					{
						if (sanic.yv < 2)
						{
							System.out.println(sanic.yv);
							sanic.setY(block.y - 29);
							sanic.setYv(0);
						}
						else if (sanic.yv > 1)
						{
							System.out.println(sanic.yv);
							sanic.setY(block.y - 30);
							sanic.setYv(-sanic.yv/2);
						}
					}
						
				}
		}
		g.dispose();
		bs.show();
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
