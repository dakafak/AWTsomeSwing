package dev.fanger.awtsomeswing.elements;

public abstract class Element implements DrawableObject {

	/**
	 * These values should be set in between
	 * 0 exclusive and
	 * 1 inclusive
	 *
	 * These values represent the relative location and size to the size of the screen
	 */
	float x;
	float y;
	float width;
	float height;

	public Element(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}
}
