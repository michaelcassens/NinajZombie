import java.awt.event.ActionEvent;
import java.util.Random;
/*
 * This class just allows us to create multiple fighters
 */
public class Enemy extends Character {

	private static Random myRand = new Random();
	private static int delay = myRand.nextInt(500) + 500;
	
	CustomRectangle healthBar;
	private int healthY;
	public Enemy(int x, int y, String filePath, int health) {
		
		super(x,y,filePath,health,delay);
		healthY = y-5;
		healthBar = new CustomRectangle(x,healthY,5,20);
	}

	public CustomRectangle getHealthBar()
	{
		return healthBar;
	}
	public int getHealthY()
	{
		return healthY;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		y-=10;
		healthY-=10;
		healthBar.setY(healthY);
	}

}
