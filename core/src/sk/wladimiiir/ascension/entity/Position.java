/**
 * Copyright (c) 2010-2014 Software AG. All Rights Reserved.
 */

package sk.wladimiiir.ascension.entity;

/**
 * @author Y12370
 * @version 1.0
 * @since 22. 10. 2014, 11:04
 */
public class Position {
    private long x;
    private long y;
    private long z;

    public Position() {
        this(0, 0, 0);
    }

    public Position(long x, long y, long z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public long getX() {
        return x;
    }

    public long getY() {
        return y;
    }

    public long getZ() {
        return z;
    }
}