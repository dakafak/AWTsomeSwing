package dev.fanger.awtsomeswing.elements.buttons;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionListener;

public class RectangleTextButton extends Button {

	public RectangleTextButton(String text, float x, float y, float buttonWidth, float buttonHeight, ActionListener actionListener) {
		super(text, x, y, buttonWidth, buttonHeight, actionListener);
		setUseRelativeWidthBounds(true);
	}

	@Override
	public void drawButton(Graphics2D g, Rectangle bounds) {
		if(currentlyHovering) {
			g.setColor(Color.WHITE);
		} else {
			g.setColor(Color.RED);
		}

		int fontSize = (int) (bounds.getHeight() * .75);
		g.setFont(new Font(Font.MONOSPACED, 0, fontSize));
		int textWidthOffset = (int) Math.round(g.getFontMetrics().stringWidth(getButtonString()) / 2.0);
		int textHeightOffset = (int) Math.round(fontSize / 4);

		g.drawRect(0, 0, (int) bounds.getWidth() - 1, (int) bounds.getHeight() - 1);
		g.drawString(getButtonString(), ((int) bounds.getWidth() / 2) - textWidthOffset, (int) bounds.getHeight() / 2 + textHeightOffset);
	}

}
