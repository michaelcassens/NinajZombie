public class CustomRectangle {

	private int x;

	private int y;
	private int height;
	private int width;

	public CustomRectangle(int x, int y, int height, int width) {
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y)
	{
		this.y = y;
	}
	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public boolean intersects(int x, int y) {
		if (x >= this.x && x < this.x + width && y >= this.y
				&& this.y < this.y + height)
			return true;
		else
			return false;
	}
}
