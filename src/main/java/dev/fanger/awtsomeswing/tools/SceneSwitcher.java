package dev.fanger.awtsomeswing.tools;

import dev.fanger.awtsomeswing.SceneManager;
import dev.fanger.awtsomeswing.scene.SceneProperties;

public class SceneSwitcher {

	private SceneManager sceneManager;

	public SceneSwitcher(SceneManager sceneManager) {
		this.sceneManager = sceneManager;
	}

	public void changeScene(Enum sceneType, SceneProperties sceneProperties) {
		sceneManager.setCurrentScene(sceneType, sceneProperties);
	}

}
