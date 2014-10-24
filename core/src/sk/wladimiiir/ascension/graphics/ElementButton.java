/**
 * Copyright (c) 2010-2014 Software AG. All Rights Reserved.
 */

package sk.wladimiiir.ascension.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import sk.wladimiiir.ascension.entity.Element;

/**
 * @author Y12370
 * @version 1.0
 * @since 23. 9. 2014, 11:40
 */
public class ElementButton {
    private final Element element;
    private final float size;
    private final float x;
    private final float y;

    public ElementButton(Element element, float x, float y, float size) {
        this.element = element;
        this.x = x;
        this.y = y;
        this.size = size;
    }

    public void draw(ShapeRenderer renderer) {
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(element.getColor());
        renderer.rect(x, y, size, size);
        renderer.end();
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(Color.BLACK);
        renderer.rect(x, y, size, size);
        renderer.end();
    }

    public Element getElement() {
        return element;
    }

    public boolean containsPoint(float x, float y) {
        return x >= this.x && x <= (this.x + size)
                && y >= this.y && y <= (this.y + size);
    }
}