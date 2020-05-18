package dev.fanger.awtsomeswing.tools;

import dev.fanger.awtsomeswing.SceneManager;

public class SceneSwitcher {

	private SceneManager sceneManager;

	public SceneSwitcher(SceneManager sceneManager) {
		this.sceneManager = sceneManager;
	}

	public void changeScene(Enum sceneType) {
		sceneManager.setCurrentScene(sceneType);
	}

}
