/**
 * Copyright (c) 2010-2014 Software AG. All Rights Reserved.
 */

package sk.wladimiiir.ascension.entity;

/**
 * @author Y12370
 * @version 1.0
 * @since 22. 10. 2014, 14:55
 */
public interface Particle {
    boolean isEmpty();

    Position getPosition();

    void setPosition(Position position);

    Particle getParent();

    void setParent(Particle parent);
}