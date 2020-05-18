package dev.fanger.awtsomeswing.elements.buttons;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class AppButton extends Button {

	private BufferedImage appImage;

	public AppButton(String text, BufferedImage appImage, float x, float y, float buttonSize, ActionListener actionListener) {
		super(text, x, y, buttonSize, buttonSize, actionListener);
		this.appImage = appImage;
	}

	@Override
	public void drawButton(Graphics2D g, Rectangle bounds) {
		g.setColor(Color.WHITE);
		if(currentlyHovering) {
			g.drawRoundRect(0, 0, (int) Math.round(bounds.getWidth() * 1), (int) Math.round(bounds.getHeight() * 1), (int) Math.round(bounds.getWidth() * .1), (int) Math.round(bounds.getHeight() * .1));
		}

		if(appImage != null) {
			g.drawImage(appImage, (int) Math.round(bounds.getWidth() * .1), (int) Math.round(bounds.getHeight() * .075), (int) Math.round(bounds.getWidth() * .8), (int) Math.round(bounds.getHeight() * .8), null);
		} else {
			g.fillRoundRect((int) Math.round(bounds.getWidth() * .1), (int) Math.round(bounds.getHeight() * .075), (int) Math.round(bounds.getWidth() * .8), (int) Math.round(bounds.getHeight() * .8), (int) Math.round(bounds.getWidth() * .1), (int) Math.round(bounds.getHeight() * .1));
		}

		int fontSize = (int) Math.round(bounds.getHeight() * .1);
		g.setFont(new Font(Font.MONOSPACED, 0, fontSize));
		int textWidthOffset = (int) Math.round(g.getFontMetrics().stringWidth(getButtonString()) / 2.0);
		int textHeightOffset = (int) Math.round(fontSize);
		g.drawString(getButtonString(), ((int) Math.round(bounds.getWidth() / 2)) - textWidthOffset, (int) Math.round(bounds.getHeight() * .965));
	}

}
