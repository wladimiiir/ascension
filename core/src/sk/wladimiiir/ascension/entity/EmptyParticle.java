/**
 * Copyright (c) 2010-2014 Software AG. All Rights Reserved.
 */

package sk.wladimiiir.ascension.entity;

/**
 * @author Y12370
 * @version 1.0
 * @since 22. 10. 2014, 15:01
 */
public class EmptyParticle implements Particle {
    public static final Particle INSTANCE = new EmptyParticle();

    @Override
    public Position getPosition() {
        return null;
    }

    @Override
    public void setPosition(Position position) {
    }

    @Override
    public Particle getParent() {
        return null;
    }

    @Override
    public void setParent(Particle parent) {
    }

    @Override
    public boolean isEmpty() {
        return true;
    }
}