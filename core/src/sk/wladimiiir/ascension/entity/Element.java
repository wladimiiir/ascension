package sk.wladimiiir.ascension.entity;

import java.awt.*;

/**
 * User: wladimiiir
 * Date: 9/21/14
 * Time: 11:24 AM
 */
public class Element extends AbstractDBEntity {
    private String name;
    private Color color;

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
}
