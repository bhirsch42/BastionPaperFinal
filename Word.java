import org.newdawn.slick.*;
import java.io.*;

public class Word {

	private static final int MAX_STEP = 300;
	private static final int MAX_FONT = 100;
	private static final float SMALLEST_SCALE = 0.3f;
	public static final float APPROX_HEIGHT = 16.0f;

	public static Sound hit0;
	static {
		Sound temp = null;
		try {
			temp = new Sound("hit0.wav");
		}
		catch (SlickException e) {
			e.printStackTrace();
		}
		hit0 = temp;
	}
	private String text;
	private Image bigImage;
	private Image image;
	private boolean active;
	private int step = 0;
	private float x;
	private float y;
	private boolean landed = false;
	private float shiftX = 0.0f;
	private float shiftY = 0.0f;
	private boolean playedSound = false;

	public Word(String text, String filename) {
		this.text = text;
		try {
			this.bigImage = new Image(filename);
		}
		catch (SlickException e) {
			e.printStackTrace();
		}
		this.image = this.bigImage.getScaledCopy(SMALLEST_SCALE);
		this.x = 0.0f;
		this.y = 0.0f;
		active = false;

		this.image.setFilter(Image.FILTER_LINEAR);
		this.bigImage.setFilter(Image.FILTER_LINEAR);
	}

	public void setPos(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public String getText() {
		return text;
	}

	public int getWidth() {
		return image.getWidth();
	}

	public int getHeight() {
		return image.getHeight();
	}

	public float getCenterX() {
		return this.x + this.getWidth()/2;
	}

	public float getCenterY() {
		return this.y + this.getHeight()/2;
	}

	public void translate(float x, float y) {
		this.x += x;
		this.y += y;
		shiftX += x;
		shiftY += y;
	}

	public void setTranslation(float x, float y) {
		this.x -= shiftX;
		this.y -= shiftY;
		shiftX = x;
		shiftY = y;
		this.x += shiftX;
		this.y += shiftY;
	}

	public void shift(float x, float y) {
		this.x += x;
		this.y += y;
	}

	public boolean contains(float x, float y) {
		return x >= getCenterX()-getWidth() && x <= getCenterX()+getWidth() && y >= getCenterY()-APPROX_HEIGHT/2.0f && y <= getCenterY()+APPROX_HEIGHT/2.0f;
	}

	public void activate(boolean animate) {
		if (active)
			return;
		this.active = true;
		if (!animate) {
			step = MAX_STEP;
			playedSound = true;
		}
	}

	public boolean isActive() {
		return active;
	}

	private void playSound() {
		if (playedSound)
			return;
		playedSound = true;
		if (Narrator.talking())
			hit0.play(1.0f, 0.2f);
		else 
			hit0.play();
	}

	private void incrementStep(int delta) {
		step += delta;
		if (step > MAX_STEP)
			step = MAX_STEP;
	}

	public void update(GameContainer container, int delta) {
		if (!active)
			return;
		incrementStep(delta);
	}

	public void render(GameContainer container, Graphics g) {
		if (!active)
			return;
		if (step == MAX_STEP) {
			g.drawImage(image,x,y);
			playSound();
		}
		else {
			float scale = SMALLEST_SCALE + (1.0f - SMALLEST_SCALE)*((float)((MAX_STEP*MAX_STEP)-(step*step))/(float)(MAX_STEP*MAX_STEP));
			Image img = bigImage.getScaledCopy(scale);
			float sx = getCenterX() - img.getWidth()/2.0f;
			float sy = getCenterY() - img.getHeight()/2.0f;
			g.drawImage(img,sx,sy,new Color(255,255,255,255*(step*step)/(MAX_STEP*MAX_STEP)));
		}
	}


}