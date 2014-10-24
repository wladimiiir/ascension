/**
 * Copyright (c) 2010-2014 Software AG. All Rights Reserved.
 */

package sk.wladimiiir.ascension.graphics;

import box2dLight.Light;
import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import sk.wladimiiir.ascension.entity.Element;

import java.util.Random;

/**
 * @author Y12370
 * @version 1.0
 * @since 23. 10. 2014, 15:49
 */
public class ElementGraphics implements Graphics {
    private static final Random RANDOMIZER = new Random();

    private final Element element;
    private Light light;

    public ElementGraphics(final Element element) {
        this.element = element;
    }

    @Override
    public void init(final RayHandler rayHandler) {
        light = new PointLight(rayHandler, 10, element.getColor(), 2, element.getPosition().getX() * 2, element.getPosition().getY() * 2);
        light.setSoft(false);
        light.setActive(false);
        light.setStaticLight(true);
        light.setXray(true);
    }

    @Override
    public void processMouseInput(final int x, final int y) {

    }

    @Override
    public void update() {
        if (light.isActive()) {
            if (RANDOMIZER.nextInt(200) == 0) {
                light.setActive(false);
            }
        } else if (RANDOMIZER.nextInt(1000) == 0) {
            light.setActive(true);
        }
    }

    @Override
    public void render(final SpriteBatch spriteBatch, final ShapeRenderer shapeRenderer) {
    }
}