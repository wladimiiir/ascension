/**
 * Copyright (c) 2010-2014 Software AG. All Rights Reserved.
 */

package sk.wladimiiir.ascension.graphics;

import box2dLight.Light;
import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import sk.wladimiiir.ascension.entity.Element;
import sk.wladimiiir.ascension.entity.Particle;
import sk.wladimiiir.ascension.entity.Universe;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Y12370
 * @version 1.0
 * @since 24. 10. 2014, 12:04
 */
public class UniverseGraphics implements Graphics {
    private final Universe universe;
    private final Collection<Graphics> particleGraphics = new ArrayList<>();
    private final List<Light> lights = new ArrayList<>();
    private RayHandler rayHandler;

    public UniverseGraphics(final Universe universe) {
        this.universe = universe;
    }

    @Override
    public void init(final RayHandler rayHandler) {
        particleGraphics.clear();
        lights.clear();
        this.rayHandler = rayHandler;
//        initParticles(rayHandler);
    }

    private void initParticles(final RayHandler rayHandler) {
        for (Particle particle : universe.getParticles()) {
            final Graphics graphics = createParticleGraphics(particle);
            if (graphics != null) {
                graphics.init(rayHandler);
                particleGraphics.add(graphics);
            }
        }
    }

    private Graphics createParticleGraphics(final Particle particle) {
        if (particle instanceof Element) {
            return new ElementGraphics((Element) particle);
        }
        return null;
    }

    @Override
    public void processMouseInput(final int x, final int y) {
        lights.parallelStream()
                .forEach(l -> l.setActive(false));

        final List<Element> elements = universe.getParticlesInArea(x, y, 3).stream()
                .map(p -> (Element) p).collect(Collectors.toList());
        IntStream.range(0, elements.size())
                .forEach(index -> updateLight(getLight(index), elements.get(index)));
    }

    private void updateLight(final Light light, final Element element) {
        light.setActive(true);
        light.setPosition(element.getPosition().getX(), element.getPosition().getY());
        light.setColor(element.getColor());
    }

    private Light getLight(final int index) {
        if (lights.size() > index) {
            return lights.get(index);
        } else {
            return createLight();
        }
    }

    private Light createLight() {
        final PointLight light = new PointLight(rayHandler, 10, Color.WHITE, 2, 0, 0);
        lights.add(light);
        return light;
    }

    @Override
    public void update() {
        for (Graphics graphics : particleGraphics) {
            graphics.update();
        }
    }

    @Override
    public void render(final SpriteBatch spriteBatch, final ShapeRenderer shapeRenderer) {
        for (Graphics graphics : particleGraphics) {
            graphics.render(spriteBatch, shapeRenderer);
        }
    }
}