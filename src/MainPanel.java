import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class MainPanel extends JPanel implements MouseListener, ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	CustomRectangle[][] myCustomRect = new CustomRectangle[11][10];
	int markerX, markerY;
	ArrayList<Ninja> ninjas = new ArrayList<Ninja>();
	String ninjaFile = "ninjacleansmall.png";
	ArrayList<Enemy> zombies = new ArrayList<Enemy>();
	Timer updateTimer = new Timer((int) (1000 / 30), this);
	ImageIcon background = new ImageIcon("./src/images/throwingstart.jpg");
	int level = 1;
	// enemy timers
	Timer myEnemyWaveTimer = new Timer((int) (5000), this);
	int wave = 1;
	int score = 0;
	int money = 100;
	int ninjaCost = 7;
	int moneyGained = 5;
	int lives = 1;
	int scoreX = 50;
	int scoreY = 775;
	int moneyX = 250;
	int moneyY = 775;
	int livesX = 450;
	int livesY = 775;
	int levelX = 600;
	int levelY = 775;
	int regularFontSize = 12;
	int largeFontSize = 120;
	int gameOverX = 100;
	int gameOverY = 660;
	String gameOver = "";
	final int RECSIZE = 75;
	final int WIDTH = 825;
	final int HEIGHT = 800;
	final int enemyMultiplier = 2;
	final int INITIALHEALTH = 100;
	final int OFFSET = 10;
	final int BACKGROUNDX = 185;
	final int BACKGROUNDY = 100;
	final int SPRITEWIDTH = 50;
	final int SPRITEHEIGHT = 63;
	final int HEALTHTHRESHOLD = 50;
	final int NINJAOFFSET = 20;
	final int ZOMBIEOFFSET = 75;
	// constructor
	public MainPanel() {

		setBackground(Color.white);
		addMouseListener(this);
		updateTimer.start();
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		for (int i = 0; i < myCustomRect.length; i++) {
			for (int j = 0; j < myCustomRect[0].length; j++) {
				myCustomRect[i][j] = new CustomRectangle(i * RECSIZE, j * RECSIZE, RECSIZE, RECSIZE);
			}
		}
		createEnemies();
		
	}

	public void createEnemies()
	{
		Random myRand = new Random();
		
		int numberOfEnemies = myRand.nextInt(level*enemyMultiplier) + 1;
		int yLocation = 675;
		int xLocation = 0;
		
		for (int i = 0; i < numberOfEnemies; i++) {
			
			
			CustomRectangle temp = findSquare(xLocation, yLocation);
			
			if(temp != null)
			{
			zombies.add(new Enemy(temp.getX()+OFFSET, temp.getY() + OFFSET, 
					"zombie.png", INITIALHEALTH));
			xLocation+=75;
			}
			
		}
		
		for(int i = 0; i < zombies.size(); i ++)
		{
			
			zombies.get(i).getTimer().start();
		}
	}
	/*
	 * This method is called every time the screen is drawn
	 */
	public void paintComponent(Graphics page) {

		super.paintComponent(page);

		page.drawImage(background.getImage(), BACKGROUNDX, BACKGROUNDY, null);

		for (int i = 0; i < myCustomRect.length; i++) {
			for (int j = 0; j < myCustomRect[0].length; j++) {
				page.drawRect(myCustomRect[i][j].getX(),
						myCustomRect[i][j].getY(),
						myCustomRect[i][j].getHeight(),
						myCustomRect[i][j].getWidth());
			}
		}

		for (int i = 0; i < ninjas.size(); i++) {

			Ninja myNinja = ninjas.get(i);

			//Toolkit tk = Toolkit.getDefaultToolkit();
			Image goodGuy = myNinja.getCharacterIcon().getImage();
			drawSpriteFrame(goodGuy, page, myNinja.getX(), myNinja.getY(), 6,
					myNinja.getNinjaFrameNumber(), SPRITEWIDTH, SPRITEHEIGHT);

		}

		for (int i = 0; i < zombies.size(); i++) {
			zombies
					.get(i)
					.getCharacterIcon()
					.paintIcon(this, page, zombies.get(i).getX(),
							zombies.get(i).getY());
			CustomRectangle health = zombies.get(i).getHealthBar();
			//page.drawRect(health.getX(), health.getY(), health.getWidth(), health.getHeight());
			if(zombies.get(i).getHealth() <= HEALTHTHRESHOLD)
			{
				page.setColor(Color.RED);
			}
			else
			{
				page.setColor(Color.GREEN);
			}
			page.fillRect(health.getX(), health.getY(), health.getWidth(), health.getHeight());
			page.setColor(Color.BLACK);
			if(zombies.get(i).getY() < 0)
			{
				zombies.remove(i);
				lives--;
				money += moneyGained; 
				if(lives <=0)
				{
					endGame();
				}
			}
		}

		page.setFont(new Font("Tahoma", Font.PLAIN, regularFontSize));
		page.drawString("Score: " + score, scoreX, scoreY);

		page.drawString("Money: " + money,moneyX, moneyY);
		
		page.drawString("Lives: " + lives,livesX, livesY);
		
		page.drawString("Level: " + level,levelX, levelY);
		
		page.setFont(new Font("Tahoma", Font.PLAIN, largeFontSize)); 
		page.drawString(gameOver, gameOverX,gameOverY);
	}
 
	public void endGame()
	{
		for(int i = 0; i < zombies.size(); i++)
			zombies.get(i).getTimer().stop();
		
		for(int i = 0; i < ninjas.size(); i++)
		{
			ninjas.get(i).getTimer().stop();
		}
		
		myEnemyWaveTimer.stop();
		updateTimer.stop();
		
		gameOver = "GAME OVER";
	}
	public CustomRectangle findSquare(int x, int y) {
		CustomRectangle temp = null;
		for (int i = 0; i < myCustomRect.length; i++) {
			for (int j = 0; j < myCustomRect[0].length; j++) {
				if (myCustomRect[i][j].intersects(x, y))
					temp = myCustomRect[i][j];

			}
		}
		return temp;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {

		int x = arg0.getX();
		int y = arg0.getY();

		CustomRectangle myRect = findSquare(x, y);
		if (myRect != null) {
			markerX = myRect.getX();
			markerY = myRect.getY();
				
			// place a marker there..
			if(money >= 5)
			{
				Ninja myBall = new Ninja(markerX+10, markerY, ninjaFile, INITIALHEALTH);
				ninjas.add(myBall);
				money -= ninjaCost;
			}
		}

		repaint();
	}

	public void collides() {
		int i = 0;

		
		for (i = 0; i < ninjas.size(); i++) {

			int topLeftX1 = ninjas.get(i).getX();
			int bottomRightX1 = ninjas.get(i).getX() + NINJAOFFSET;
			int topLeftY1 = ninjas.get(i).getY();
			int bottomRightY1 = ninjas.get(i).getY() + NINJAOFFSET;

			for (int j = 0; j < zombies.size(); j++) {
				int topLeftX = zombies.get(j).getX();
				int bottomRightX = zombies.get(j).getX() + ZOMBIEOFFSET;
				int topLeftY = zombies.get(j).getY();
				int bottomRightY = zombies.get(j).getY() + ZOMBIEOFFSET;

				if (areRectsColliding(topLeftX, bottomRightX, topLeftY,
						bottomRightY, topLeftX1, bottomRightX1, topLeftY1,
						bottomRightY1)) {
				
					if(i < ninjas.size())
					// reduce fighter health
						ninjas.get(i).setHealth(ninjas.get(i).getHealth() - zombies.get(j).getDamage());
					if(zombies.size() > j && ninjas.size() > i)
					{
						zombies.get(j).setHealth(
								zombies.get(j).getHealth() - ninjas.get(i).getDamage());
					}
					if(i < ninjas.size() && ninjas.get(i).getHealth() <= 0)
						{
							// show explosion
							
						ninjas.remove(i);
						}
						// remove fighter
						if (zombies.get(j).getHealth() <= 0) {
						money += moneyGained;
						zombies.remove(j);
						score++;
						if(zombies.size() <= 0)
						{
							level++;
							money+=60;
							createEnemies();
							myEnemyWaveTimer.start();
						}
						
						
					}
				}// end collision if
				
			}

		}
		
	}
	
	private boolean areRectsColliding(int r1TopLeftX, int r1BottomRightX,
			int r1TopLeftY, int r1BottomRightY, int r2TopLeftX,
			int r2BottomRightX, int r2TopLeftY, int r2BottomRightY) {

		if (r1TopLeftX < r2BottomRightX && r1BottomRightX > r2TopLeftX
				&& r1TopLeftY < r2BottomRightY && r1BottomRightY > r2TopLeftY) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == myEnemyWaveTimer)
		{
			
			if(wave == level)
			{
				myEnemyWaveTimer.stop();
				wave = 0; 
			}
			else
			{
				createEnemies();
			}
			wave++;
		}
		else if(e.getSource() == updateTimer)
		{
			if(zombies.size() == 0)
			{
				createEnemies();
			}
			collides();
		}
		repaint();
	}

	private void drawSpriteFrame(Image source, Graphics g2d, int x, int y, int columns,
			int frame, int width, int height) {
		int frameX = (frame % columns) * width;
		int frameY = (frame / columns) * height;
		g2d.drawImage(source, x, y, x + width, y + height, frameX, frameY,
				frameX + width, frameY + height, this);
	}

}
