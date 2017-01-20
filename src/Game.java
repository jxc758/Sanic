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
	private KeyInput key;

	public static void main(String args[])
	{
		new Game();
	}

	public Game()
	{
		this.setFocusable(true);
		key = new KeyInput();
		this.addKeyListener(key);
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
		
		sanic.xv *= sanic.friction;
		
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
