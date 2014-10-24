/**
 * Copyright (c) 2010-2014 Software AG. All Rights Reserved.
 */

package sk.wladimiiir.ascension.entity;

/**
 * @author Y12370
 * @version 1.0
 * @since 22. 10. 2014, 15:13
 */
abstract class AbstractParticle implements Particle {
    protected Position position = new Position();
    protected Particle parent;

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public Particle getParent() {
        return parent;
    }

    @Override
    public void setParent(Particle parent) {
        this.parent = parent;
    }
}