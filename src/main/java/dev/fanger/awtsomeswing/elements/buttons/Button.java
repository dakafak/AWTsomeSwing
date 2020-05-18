package dev.fanger.awtsomeswing.elements.buttons;

import dev.fanger.awtsomeswing.elements.Element;
import dev.fanger.awtsomeswing.elements.MouseInteractableObject;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public abstract class Button extends Element implements MouseInteractableObject {

	private String buttonString;

	private int cachedScreenWidth;
	private int cachedScreenHeight;
	private boolean useRelativeWidthBounds;
	private Rectangle cachedBounds;
	private BufferedImage cachedBufferedImage;

	public Button(String text, float x, float y, float buttonWidth, float buttonHeight, ActionListener actionListener) {
		super(x, y, buttonWidth, buttonHeight);
		this.buttonString = text;
		this.actionListener = actionListener;
	}

	private void resetCachedValues(int screenWidth, int screenHeight) {
		cachedScreenWidth = screenWidth;
		cachedScreenHeight = screenHeight;
		cachedBounds = new Rectangle(
				(int) (getX() * cachedScreenWidth),
				(int) (getY() * cachedScreenHeight),
				(int) (useRelativeWidthBounds ? screenWidth * getWidth() : screenHeight * getWidth()),
				(int) (screenHeight * getHeight()));
		cachedCurrentlyHovering = currentlyHovering;
	}

	private boolean shouldResetCachedValues(int screenWidth, int screenHeight) {
		return screenWidth != cachedScreenWidth || screenHeight != cachedScreenHeight || currentlyHovering != cachedCurrentlyHovering;
	}

	@Override
	public void draw(Graphics2D g2d, int screenWidth, int screenHeight) {
		if(shouldResetCachedValues(screenWidth, screenHeight)) {
			resetCachedValues(screenWidth, screenHeight);

			cachedBufferedImage = new BufferedImage((int) cachedBounds.getWidth() + 1, (int) cachedBounds.getHeight() + 1, BufferedImage.TYPE_INT_ARGB);
			Graphics2D bufferedImageGraphics = cachedBufferedImage.createGraphics();
			drawButton(bufferedImageGraphics, cachedBounds);
			bufferedImageGraphics.dispose();
		}

		g2d.drawImage(cachedBufferedImage, (int) getBounds().getX(), (int) getBounds().getY(), (int) getBounds().getWidth(), (int) getBounds().getHeight(), null);
	}

	public abstract void drawButton(Graphics2D g, Rectangle bounds);

	@Override
	public void checkButtonPress(MouseEvent e) {
		if(cachedBounds != null && cachedBounds.contains(e.getX(), e.getY())) {
			actionPerformed(null);
		}
	}

	ActionListener actionListener;
	public void actionPerformed(ActionEvent e) {
		if(actionListener != null) {
			actionListener.actionPerformed(e);
		}
	}

	boolean currentlyHovering = false;
	private boolean cachedCurrentlyHovering = false;
	public void checkHover(MouseEvent e) {
		if(getBounds() != null) {
			currentlyHovering = getBounds().contains(e.getX(), e.getY());
		}
	}

	public String getButtonString() {
		return buttonString;
	}

	public void setButtonString(String buttonString) {
		this.buttonString = buttonString;
	}

	private Rectangle getBounds() {
		return cachedBounds;
	}

	public boolean isUseRelativeWidthBounds() {
		return useRelativeWidthBounds;
	}

	public void setUseRelativeWidthBounds(boolean useRelativeWidthBounds) {
		this.useRelativeWidthBounds = useRelativeWidthBounds;
	}
}
