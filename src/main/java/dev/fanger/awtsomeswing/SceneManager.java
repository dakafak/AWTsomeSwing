package dev.fanger.awtsomeswing;

import dev.fanger.awtsomeswing.scene.Scene;
import dev.fanger.awtsomeswing.scene.SceneProperties;
import dev.fanger.awtsomeswing.tools.SceneSwitcher;

import javax.swing.JComponent;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.HashMap;
import java.util.Map;

import static java.awt.Cursor.HAND_CURSOR;

public class SceneManager extends JComponent {

    private Map<Enum, Scene> scenes;
    private Enum currentScene;
    private Enum defaultSceneType;
    private SceneSwitcher sceneSwitcher;
    private boolean displayDebugDetails;

    long lastUpdateTime;
    long updateTimeDifference;

    long defaultFPSCap = 1_000_000_000 / 300;
    long baseDeltaTime;
    final short fpsLowerLimit = 15;
    double deltaUpdate = 1;

    long lastFPS;
    long currentFrames;
    long timeSinceLastFPSupdate;

    public SceneManager() {
        super.setDoubleBuffered(true);
        super.setCursor(Cursor.getPredefinedCursor(HAND_CURSOR));

        sceneSwitcher = new SceneSwitcher(this);
        scenes = new HashMap<>();

        baseDeltaTime = 1_000_000_000 / fpsLowerLimit;

        setFocusable(true);
        setupKeyListeners(this);
    }

    public void setDefaultSceneType(Enum defaultSceneType, SceneProperties sceneProperties) {
        this.defaultSceneType = defaultSceneType;
        sceneSwitcher.changeScene(defaultSceneType, sceneProperties);
    }

    public void addScene(Enum sceneType, Scene scene) {
        scene.setSceneSwitcher(sceneSwitcher);
        scenes.put(sceneType, scene);
    }

    private void setupKeyListeners(SceneManager sceneManager) {
        setFocusTraversalKeysEnabled(false);

        sceneManager.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) { }

            @Override
            public void keyPressed(KeyEvent e) {
                sceneManager.keyPress(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                sceneManager.keyReleased(e);
            }
        });

        sceneManager.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) { }

            @Override
            public void mousePressed(MouseEvent e) {
                sceneManager.mousePress(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                sceneManager.mouseRelease(e);
            }

            @Override
            public void mouseEntered(MouseEvent e) { }

            @Override
            public void mouseExited(MouseEvent e) { }
        });

        sceneManager.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                sceneManager.mouseMove(e);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                sceneManager.mouseMove(e);
            }
        });

        sceneManager.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                sceneManager.moveMouseWheel(e);
            }
        });
    }

    public void start() {
        lastUpdateTime = System.nanoTime();

        while (true) {
            updateTimeDifference = System.nanoTime() - lastUpdateTime;

            Scene currentSceneToUpdate = scenes.get(currentScene);
            if (updateTimeDifference >= (currentSceneToUpdate != null ? currentSceneToUpdate.getUpdateCap() : defaultFPSCap)) {
                lastUpdateTime = System.nanoTime();

                currentFrames++;
                timeSinceLastFPSupdate += updateTimeDifference;
                if (timeSinceLastFPSupdate >= 1_000_000_000) {
                    timeSinceLastFPSupdate = 0;
                    lastFPS = currentFrames;
                    currentFrames = 0;
                }

                deltaUpdate = ((double) updateTimeDifference) / baseDeltaTime;
                if (deltaUpdate > 2) {
                    deltaUpdate = 2;
                }

                runUpdatesForCurrentScene(deltaUpdate);
                repaint();
            }
        }
    }

    private void runUpdatesForCurrentScene(double currentDeltaUpdate) {
        if(scenes != null && scenes.get(currentScene) != null) {
            scenes.get(currentScene).updateScene(currentDeltaUpdate);
            repaint();
        }
    }

    public void keyPress(KeyEvent e) {
        if(scenes != null && scenes.get(currentScene) != null) {
            scenes.get(currentScene).keyPress(e);
        }
    }

    public void keyReleased(KeyEvent e) {
        if(scenes != null && scenes.get(currentScene) != null) {
            if (e.getKeyCode() == KeyEvent.VK_F3) {
                displayDebugDetails = !displayDebugDetails;
            }
            scenes.get(currentScene).keyReleased(e);
        }
    }

    public void mousePress(MouseEvent e) {
        if(scenes != null && scenes.get(currentScene) != null) {
            scenes.get(currentScene).mousePress(e);
        }
    }

    public void mouseRelease(MouseEvent e) {
        if(scenes != null && scenes.get(currentScene) != null) {
            scenes.get(currentScene).mouseRelease(e);
        }
    }

    public void mouseMove(MouseEvent e) {
        if(scenes != null && scenes.get(currentScene) != null) {
            scenes.get(currentScene).mouseMove(e);
        }
    }

    public void moveMouseWheel(MouseWheelEvent e) {
        if(scenes != null && scenes.get(currentScene) != null) {
            scenes.get(currentScene).moveMouseWheel(e);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        if(scenes != null && scenes.get(currentScene) != null) {
            Graphics2D g2d = (Graphics2D) g;
            scenes.get(currentScene).draw(g2d, getWidth(), getHeight());

            if (displayDebugDetails) {
                scenes.get(currentScene).drawDebugDetails(g2d, lastFPS, getWidth(), getHeight(), currentScene.name(), deltaUpdate);
            }
        } else {
            g.setColor(Color.BLUE);
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    public void setCurrentScene(Enum newSceneType, SceneProperties sceneProperties) {
        if(newSceneType != null && scenes.get(newSceneType) != null) {
            if (currentScene != null && scenes.get(currentScene) != null) {
                scenes.get(currentScene).onClose();
            }

            scenes.get(newSceneType).onStart(sceneProperties);
            this.currentScene = newSceneType;
        }
    }
}
