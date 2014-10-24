/**
 * Copyright (c) 2010-2014 Software AG. All Rights Reserved.
 */

package sk.wladimiiir.ascension.graphics;

import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * @author Y12370
 * @version 1.0
 * @since 24. 10. 2014, 12:12
 */
public interface Graphics {
    void init(RayHandler rayHandler);

    void processMouseInput(final int x, final int y);

    void update();

    void render(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer);
}