/**
 * Copyright (c) 2010-2014 Software AG. All Rights Reserved.
 */

package sk.wladimiiir.ascension.screen;

import box2dLight.RayHandler;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import sk.wladimiiir.ascension.entity.Universe;
import sk.wladimiiir.ascension.graphics.UniverseGraphics;

/**
 * @author Y12370
 * @version 1.0
 * @since 22. 10. 2014, 15:27
 */
public class UniverseScreen extends ScreenAdapter {
    private static final float PIXELS_TO_METER = 16f;

    private final UniverseGraphics universeGraphics;

    private World world;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private RayHandler rayHandler;
    private OrthographicCamera camera;

    private InputProcessor inputProcessor = new InputAdapter() {
        private boolean altPressed = false;

        @Override
        public boolean keyDown(final int keycode) {
            switch (keycode) {
                case Input.Keys.ALT_LEFT:
                case Input.Keys.ALT_RIGHT:
                    altPressed = true;
                    break;
                case Input.Keys.ENTER:
                    if (altPressed) {
                        toggleFullscreen();
                        return true;
                    }
            }
            return false;
        }

        @Override
        public boolean keyUp(final int keycode) {
            switch (keycode) {
                case Input.Keys.ALT_LEFT:
                case Input.Keys.ALT_RIGHT:
                    altPressed = false;
                    break;
            }
            return false;
        }

        @Override
        public boolean mouseMoved(final int screenX, final int screenY) {
            universeGraphics.processMouseInput((int) (screenX / PIXELS_TO_METER), (int) ((Gdx.graphics.getHeight() - screenY) / PIXELS_TO_METER));
            return true;
        }

        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            return false;
        }
    };

    private void toggleFullscreen() {
        final boolean fullscreen = Gdx.graphics.isFullscreen();
        if (fullscreen) {
            Gdx.graphics.setDisplayMode(1024, 768, false);
        } else {
            Gdx.graphics.setDisplayMode(1920, 1080, true);
        }
    }

    public UniverseScreen(Universe universe) {
        this.universeGraphics = new UniverseGraphics(universe);
    }

    @Override
    public void resize(int width, int height) {
        final float screenWidth = Gdx.graphics.getWidth() / PIXELS_TO_METER;
        final float screenHeight = Gdx.graphics.getHeight() / PIXELS_TO_METER;

        camera = new OrthographicCamera(screenWidth, screenHeight);
        camera.translate(screenWidth / 2f, screenHeight / 2f);
        camera.update();

        if (rayHandler != null) {
            rayHandler.dispose();
        }
        rayHandler = new RayHandler(world);
        rayHandler.setCombinedMatrix(camera.combined);
        rayHandler.setBlur(false);

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(camera.combined);

        universeGraphics.init(rayHandler);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(inputProcessor);
        Gdx.input.setCursorCatched(true);

        world = new World(new Vector2(0, -10), true);
        batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        world.step(1 / 45f, 6, 2);

        universeGraphics.update();

        batch.begin();
        universeGraphics.render(batch, shapeRenderer);
        batch.end();

        rayHandler.updateAndRender();
    }
}