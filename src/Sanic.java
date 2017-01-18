import javax.swing.JFrame;

public class Sanic extends JFrame {
	public static final int frame_width = 800;
	public static final int frame_height = 450;
	
	public static void main(String[]args)
	{
		new Sanic();
	}
	
	public Sanic()
	{
		setTitle("Sanic");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(frame_width,frame_height);
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false);
		add(new Game());
	}
}
