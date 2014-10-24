/**
 * Copyright (c) 2010-2014 Software AG. All Rights Reserved.
 */

package sk.wladimiiir.ascension.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Y12370
 * @version 1.0
 * @since 22. 10. 2014, 10:58
 */
public class Universe extends AbstractParticle {
    private final Collection<Particle> particles = new ArrayList<>();

    @Override
    public boolean isEmpty() {
        return particles.isEmpty();
    }

    public void add(Particle particle, Position toPosition) {
        particle.setPosition(toPosition);
        particle.setParent(this);
        particles.add(particle);
    }

    public List<Particle> getParticlesInArea(int x, int y, int radius) {
        return particles.parallelStream()
                .filter(p -> p.getPosition().getX() == x && p.getPosition().getY() == y)
                .collect(Collectors.toList());
    }

    public Collection<Particle> getParticles() {
        return particles;
    }
}