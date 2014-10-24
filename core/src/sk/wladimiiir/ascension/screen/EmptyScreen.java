/**
 * Copyright (c) 2010-2014 Software AG. All Rights Reserved.
 */

package sk.wladimiiir.ascension.screen;

import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

/**
 * @author Y12370
 * @version 1.0
 * @since 22. 10. 2014, 15:27
 */
public class EmptyScreen extends ScreenAdapter {
    private static final float PIXELS_TO_METER = 16f;

    private World world;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private RayHandler rayHandler;

    private InputProcessor inputProcessor = new InputAdapter() {
        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            return false;
        }
    };

    @Override
    public void resize(int width, int height) {
        final float screenWidth = Gdx.graphics.getWidth() / PIXELS_TO_METER;
        final float screenHeight = Gdx.graphics.getHeight() / PIXELS_TO_METER;
        final OrthographicCamera camera = new OrthographicCamera(screenWidth, screenHeight);

        camera.translate(screenWidth / 2f, screenHeight / 2f);
        camera.update();

        if (rayHandler != null) {
            rayHandler.dispose();
        }
        rayHandler = new RayHandler(world);
        rayHandler.setCombinedMatrix(camera.combined);
        rayHandler.setAmbientLight(0.8f);

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(camera.combined);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(inputProcessor);

        world = new World(new Vector2(0, -10), true);
        batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        world.step(1 / 45f, 6, 2);

        batch.begin();
        //draw batch
        batch.end();

        rayHandler.updateAndRender();
    }
}