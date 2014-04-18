import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.Timer;


public abstract class Character implements ActionListener {
	protected int x;
	protected int y;
	protected String filePath;
	protected ImageIcon characterIcon;
	protected int health;
	protected int DELAY;
	private Timer myTimer;
	
	public Character(int x, int y, String filePath, int health, int delay)
	{
		this.x = x;
		this.y = y;
		this.filePath = filePath;
		setImageIcon();
		this.health = health;
		this.DELAY = delay;
		myTimer = new Timer(DELAY, this);
		
	}
	
	private void setImageIcon()
	{
		characterIcon = new ImageIcon("./src/images/" + filePath);
	}

	public void setHealth(int health)
	{
		this.health = health;
	}
	public int getHealth()
	{
		return health;
	}
	public ImageIcon getCharacterIcon()
	{
		return characterIcon;
	}
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public String getFilePath() {
		return filePath;
	}
	
	
	public Timer getTimer()
	{
		return myTimer;
	}

	public int getDamage() {
		Random myRand = new Random();
		return myRand.nextInt(3) + 1;
	}

}
