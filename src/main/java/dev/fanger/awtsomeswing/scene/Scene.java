package dev.fanger.awtsomeswing.scene;

import dev.fanger.awtsomeswing.tools.SceneSwitcher;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public abstract class Scene {

    long updateCap = 1_000_000_000 / 300;
    private SceneSwitcher sceneSwitcher;

    public SceneSwitcher getSceneSwitcher() {
        return sceneSwitcher;
    }

    public long getUpdateCap() {
        return updateCap;
    }

    public void setFPSCap(int cap) {
        updateCap = 1_000_000_000 / cap;
    }

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

    public abstract void moveMouseWheel(MouseWheelEvent e);

}
