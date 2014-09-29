package sk.wladimiiir.ascension.graphics;

import box2dLight.ConeLight;
import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * User: wladimiiir
 * Date: 9/22/14
 * Time: 9:16 PM
 */
public class LightEntity {
    private static final int CONE_LIGHT_COUNT = 20;
    private static final int LIGHT_DEGREE_STEP = 360 / CONE_LIGHT_COUNT;
    private static final int CONE_DEGREE = 8;

    private final MyConeLight[] lights;
    private final float minSize;
    private final float maxSize;
    private final float positionX;
    private final float positionY;
    private final PointLight centerLight;

    private boolean distanceIncreasing = true;

    public LightEntity(RayHandler rayHandler, Color entityColor, float positionX, float positionY, float minSize, float maxSize) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.minSize = minSize;
        this.maxSize = maxSize;
        lights = new MyConeLight[CONE_LIGHT_COUNT];
        for (int index = 0; index < lights.length; index++) {
            lights[index] = new MyConeLight(rayHandler, 3, entityColor, minSize, positionX, positionY, index * LIGHT_DEGREE_STEP, CONE_DEGREE);
        }
        centerLight = new PointLight(rayHandler, 100, entityColor, maxSize, positionX, positionY);
    }

    public void attachToBody(Body body, float offsetX, float offsetY) {
        for (MyConeLight light : lights) {
            light.attachToBody(body, offsetX, offsetY);
        }
        centerLight.attachToBody(body, offsetX, offsetY);
    }

    public void update() {
        for (int index = 0; index < lights.length; index++) {
            final MyConeLight light = lights[index];
            float newDirection = light.getDirection() + 1f;
            light.setDirection(newDirection > 360 ? 0 : newDirection);
            if (distanceIncreasing) {
                if (light.getDistance() > maxSize) {
                    distanceIncreasing = false;
                }
            } else {
                if (light.getDistance() < minSize) {
                    distanceIncreasing = true;
                }
            }
        }
    }

    public Vector2 getPosition() {
        return new Vector2(positionX, positionY);
    }

    private class MyConeLight extends ConeLight {
        public MyConeLight(RayHandler rayHandler, int rays, Color color, float distance, float x, float y, float directionDegree, float coneDegree) {
            super(rayHandler, rays, color, distance, x, y, directionDegree, coneDegree);
        }

        public float getDirection() {
            return direction;
        }
    }
}
