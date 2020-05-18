package dev.fanger.awtsomeswing.elements;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Text extends Element{

	String text;

	public Text(String text, float x, float y, float width, float height) {
		super(x, y, width, height);
		this.text = text;
	}

	private int cachedScreenWidth;
	private int cachedScreenHeight;
	private Rectangle cachedBounds;
	private BufferedImage cachedImage;

	private void resetCachedValues(int screenWidth, int screenHeight) {
		cachedScreenWidth = screenWidth;
		cachedScreenHeight = screenHeight;
		cachedBounds = new Rectangle(
				(int) (x * cachedScreenWidth),
				(int) (y * cachedScreenHeight),
				(int) (screenWidth * width),
				(int) (screenHeight * height));
	}

	private boolean shouldResetCachedValues(int screenWidth, int screenHeight) {
		return screenWidth != cachedScreenWidth || screenHeight != screenHeight;
	}

	@Override
	public void draw(Graphics2D g2d, int screenWidth, int screenHeight) {
		if(shouldResetCachedValues(screenWidth, screenHeight)) {
			resetCachedValues(screenWidth, screenHeight);
			cachedImage = new BufferedImage((int) cachedBounds.getWidth(), (int) cachedBounds.getHeight(), BufferedImage.TYPE_INT_ARGB);
			Graphics2D bufferedImageGraphics = cachedImage.createGraphics();

			int fontSize = (int) (cachedBounds.getHeight() * .75);
			bufferedImageGraphics.setFont(new Font(Font.MONOSPACED, 0, fontSize));
			int textWidthOffset = (int) Math.round(bufferedImageGraphics.getFontMetrics().stringWidth(text) / 2.0);
			int textHeightOffset = (int) Math.round(fontSize / 4);

			bufferedImageGraphics.drawString(text, ((int) cachedBounds.getWidth() / 2) - textWidthOffset, (int) cachedBounds.getHeight() / 2 + textHeightOffset);
			bufferedImageGraphics.dispose();
		}

		g2d.drawImage(cachedImage, (int) cachedBounds.getX(), (int) cachedBounds.getY(), (int) cachedBounds.getWidth(), (int) cachedBounds.getHeight(), null);
	}

}
