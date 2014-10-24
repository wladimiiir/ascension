package sk.wladimiiir.ascension.entity;

import com.badlogic.gdx.graphics.Color;

/**
 * User: wladimiiir
 * Date: 9/21/14
 * Time: 11:24 AM
 */
public class Element extends AbstractParticle {
    private String name;
    private Color color;

    public Element() {
    }

    public Element(String name, Color color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
