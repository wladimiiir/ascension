/**
 * Copyright (c) 2010-2014 Software AG. All Rights Reserved.
 */

package sk.magiksoft.ascension.screen;

import box2dLight.ConeLight;
import box2dLight.DirectionalLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

/**
 * @author Y12370
 * @version 1.0
 * @since 22. 9. 2014, 14:40
 */
public class TestScreen extends ScreenAdapter {
    private SpriteBatch batch;
    private Sprite meditationImage;
    private World world;
    private MyConeLight[] lights;
    private RayHandler rayHandler;
    private OrthographicCamera camera;

    @Override
    public void show() {
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.translate(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);
        camera.update();

        world = new World(new Vector2(0, 0), true);
        rayHandler = new RayHandler(world);
        batch = new SpriteBatch();
        meditationImage = new Sprite(new Texture("meditation.jpg"));
        meditationImage.setOrigin(0, 0);

        new DirectionalLight(rayHandler, 100, new Color(1, 1, 1, 0.3f), 0);
        lights = new MyConeLight[20];
        for (int index = 0; index < lights.length; index++) {
            lights[index] = new MyConeLight(rayHandler, 3, Color.WHITE, 130, Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f, index * 18, 8);
            lights[index].setXray(true);
        }
//        light = new ConeLight(rayHandler, 3, Color.YELLOW, 300, Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f, 90, 5);


        final BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        meditationImage.draw(batch);
        batch.end();

        for (int index = 0; index < lights.length; index++) {
            final MyConeLight light = lights[index];
            light.setDirection(light.getDirection() + 1);
            if (light.distanceIncreasing) {
                light.setDistance(light.getDistance() + 1);
                if (light.getDistance() > 150) {
                    light.distanceIncreasing = false;
                }
            } else {
                light.setDistance(light.getDistance() - 1);
                if (light.getDistance() < 130) {
                    light.distanceIncreasing = true;
                }
            }
        }

        rayHandler.setCombinedMatrix(camera.combined);
        rayHandler.updateAndRender();
    }

    private class MyConeLight extends ConeLight {
        private boolean distanceIncreasing = true;

        /**
         * @param rayHandler
         * @param rays
         * @param color
         * @param distance
         * @param x
         * @param y
         * @param directionDegree
         * @param coneDegree
         */
        public MyConeLight(RayHandler rayHandler, int rays, Color color, float distance, float x, float y, float directionDegree, float coneDegree) {
            super(rayHandler, rays, color, distance, x, y, directionDegree, coneDegree);
        }

        public float getDirection() {
            return direction;
        }
    }
}