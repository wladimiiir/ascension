/**
 * Copyright (c) 2010-2014 Software AG. All Rights Reserved.
 */

package sk.magiksoft.ascension.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

/**
 * @author Y12370
 * @version 1.0
 * @since 19. 9. 2014, 8:09
 */
public class MeditationScreen extends ScreenAdapter {
    private SpriteBatch batch;
    private Sprite meditationImage;
    private Wanderer wanderer;
    private final Random random = new Random();

    @Override
    public void show() {
        batch = new SpriteBatch();
        meditationImage = new Sprite(new Texture("meditation.jpg"));
        meditationImage.setOrigin(0, 0);
        wanderer = new Wanderer(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);

        Gdx.input.setCursorCatched(true);
    }

    @Override
    public void resize(int width, int height) {
        float scaleX = (float) Gdx.graphics.getWidth() / meditationImage.getTexture().getWidth();
        float scaleY = (float) Gdx.graphics.getHeight() / meditationImage.getTexture().getHeight();
        meditationImage.setScale(scaleX, scaleY);
        wanderer.wandererImage.setScale(scaleX, scaleY);
    }

    @Override
    public void render(float delta) {
        updateWanderer();
        handleInput();

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        meditationImage.draw(batch);
        wanderer.draw(batch);
        batch.end();
    }

    private void handleInput() {
        final int deltaX = Gdx.input.getDeltaX();
        final int deltaY = Gdx.input.getDeltaY();

        wanderer.positionX += deltaX;
        wanderer.positionY -= deltaY;
    }

    private void updateWanderer() {
        final int maxX = Gdx.graphics.getWidth();
        final int maxY = Gdx.graphics.getHeight();

        final int angleDiff = 25;
        if (wanderer.lastAngle == -1) {
            wanderer.lastAngle = random.nextInt(360);
        } else {
            wanderer.lastAngle = wanderer.lastAngle + (angleDiff - random.nextInt(angleDiff * 2));
            if (wanderer.lastAngle >= 360) {
                wanderer.lastAngle -= 360;
            } else if (wanderer.lastAngle < 0) {
                wanderer.lastAngle += 360;
            }
        }

        if (wanderer.getDistanceFromStart() > Math.max(50 * wanderer.currentMoveSpeed, 10)) {
            wanderer.calmLoad--;
            if (wanderer.calmLoad <= 0) {
                wanderer.currentMoveSpeed = (float) Math.max(0.0, Math.min(wanderer.currentMoveSpeed + 0.5, 10));
                wanderer.calmLoad = 0;
            }
        } else {
            wanderer.calmLoad++;
            if (wanderer.calmLoad >= 100) {
                wanderer.currentMoveSpeed = (float) Math.max(0.0, Math.min(wanderer.currentMoveSpeed - 0.5, 10));
                wanderer.calmLoad = 0;
            }
        }

        wanderer.positionX += Math.cos(Math.toRadians(wanderer.lastAngle)) * wanderer.currentMoveSpeed;
        wanderer.positionY += Math.sin(Math.toRadians(wanderer.lastAngle)) * wanderer.currentMoveSpeed;

        wanderer.positionX = Math.max(0, Math.min(maxX, wanderer.positionX));
        wanderer.positionY = Math.max(0, Math.min(maxY, wanderer.positionY));
    }

    private class Wanderer {
        private final Sprite wandererImage;
        private float startPositionX;
        private float startPositionY;
        private float positionX;
        private float positionY;
        private float lastAngle = -1;
        private float currentMoveSpeed = 5;
        private int calmLoad = 0;

        private Wanderer(float p_positionX, float p_positionY) {
            startPositionX = p_positionX;
            startPositionY = p_positionY;
            positionX = p_positionX;
            positionY = p_positionY;
            wandererImage = new Sprite(new Texture("wanderer.png"));
            wandererImage.setAlpha(0.5f);
        }

        public void draw(SpriteBatch batch) {
            wandererImage.setPosition(positionX - wandererImage.getWidth() / 2, positionY - wandererImage.getHeight() / 2);
            wandererImage.draw(batch);
        }

        public double getDistanceFromStart() {
            return Math.sqrt(Math.pow(startPositionX - positionX, 2) + Math.pow(startPositionY - positionY, 2));
        }
    }
}