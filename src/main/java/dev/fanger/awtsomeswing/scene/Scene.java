package dev.fanger.awtsomeswing.scene;

import dev.fanger.awtsomeswing.tools.SceneSwitcher;

import java.awt.Color;
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

    public abstract void draw(Graphics2D g, int screenWidth, int screenHeight);

    public void drawDebugDetails(Graphics2D g, long lastFPS, int width, int height, String name, double deltaUpdate) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 200, 100);

        g.setColor(Color.WHITE);
        g.drawString("fps: " + lastFPS, 5, 15);
        g.drawString("screen size: (" + width + ", " + height + ")", 5, 30);
        g.drawString("current scene: " + name, 5, 45);
        g.drawString("delta update: " + deltaUpdate, 5, 60);
    }

    public abstract void keyPress(KeyEvent e);

    public abstract void keyReleased(KeyEvent e);

    public abstract void mousePress(MouseEvent e);

    public abstract void mouseRelease(MouseEvent e);

    public abstract void mouseMove(MouseEvent e);

    public abstract void moveMouseWheel(MouseWheelEvent e);

}
