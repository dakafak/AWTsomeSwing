package dev.fanger.awtsomeswing.scene;

import dev.fanger.awtsomeswing.tools.SceneSwitcher;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public abstract class Scene {

	private SceneSwitcher sceneSwitcher;

	public void setSceneSwitcher(SceneSwitcher sceneSwitcher) {
		this.sceneSwitcher = sceneSwitcher;
	}

	public void changeScene(Enum sceneType) {
		sceneSwitcher.changeScene(sceneType, null);
	}

	public void changeScene(Enum sceneType, SceneProperties sceneProperties) {
		sceneSwitcher.changeScene(sceneType, sceneProperties);
	}

	public abstract void onStart(SceneProperties sceneProperties);

	public abstract void onClose();

	public abstract void updateScene(double deltaUpdate);

	public abstract void draw(Graphics2D g, int screenWidth, int screenHeight);//TODO replace width and height with a cached size object, which holds cached values for aiding in calculating sizes

	public abstract void keyPress(KeyEvent e);

	public abstract void keyReleased(KeyEvent e);

	public abstract void mousePress(MouseEvent e);

	public abstract void mouseRelease(MouseEvent e);

	public abstract void mouseMove(MouseEvent e);

}
