package sk.wladimiiir.ascension.graphics;

import box2dLight.ConeLight;
import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.Color;

/**
 * User: wladimiiir
 * Date: 9/22/14
 * Time: 9:16 PM
 */
public class LightEntity {
    private static final int CONE_LIGHT_COUNT = 20;
    private static final int LIGHT_DEGREE_STEP = 180 / (CONE_LIGHT_COUNT / 2);
    private static final int CONE_DEGREE = 8;
    private final MyConeLight[] lights;
    private final int minSize;
    private final int maxSize;

    private boolean distanceIncreasing = true;

    public LightEntity(RayHandler rayHandler, Color entityColor, float positionX, float positionY, int minSize, int maxSize) {
        this.minSize = minSize;
        this.maxSize = maxSize;
        lights = new MyConeLight[CONE_LIGHT_COUNT];
        for (int index = 0; index < lights.length; index++) {
            lights[index] = new MyConeLight(rayHandler, 3, entityColor, minSize, positionX, positionY, index * LIGHT_DEGREE_STEP, CONE_DEGREE);
        }
        new PointLight(rayHandler, 100, Color.WHITE, minSize, positionX, positionY);
    }

    public void update() {
        for (int index = 0; index < lights.length; index++) {
            final MyConeLight light = lights[index];
            light.setDirection(light.getDirection() + 1);
            if (distanceIncreasing) {
                light.setDistance(light.getDistance() + 1);
                if (light.getDistance() > maxSize) {
                    distanceIncreasing = false;
                }
            } else {
                light.setDistance(light.getDistance() - 1);
                if (light.getDistance() < minSize) {
                    distanceIncreasing = true;
                }
            }
        }
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
