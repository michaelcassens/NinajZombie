import java.awt.event.ActionEvent;
import javax.swing.Timer;

public class Ninja extends Character{

	private int ninjaFrameNumber = 5;
	private static int delay = (int)1000/3;
	
	public Ninja(int x, int y, String filePath, int health)
	{
		super(x,y,filePath,health, delay);
		startTimer();
	}

	public void startTimer()
	{
		Timer myTimer = getTimer();
		myTimer.start();
	}
	public int getNinjaFrameNumber()
	{
		return ninjaFrameNumber;
	}

	
		@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		ninjaFrameNumber++;
		if(ninjaFrameNumber > 10)
		{
			ninjaFrameNumber = 5;
		}
	
	}

	


}
