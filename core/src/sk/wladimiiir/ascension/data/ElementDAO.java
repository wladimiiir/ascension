/**
 * Copyright (c) 2010-2014 Software AG. All Rights Reserved.
 */

package sk.wladimiiir.ascension.data;

import sk.wladimiiir.ascension.entity.Element;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Y12370
 * @version 1.0
 * @since 23. 9. 2014, 11:31
 */
public class ElementDAO {
    private final Set<Element> elements = new HashSet<>();

    public void saveElement(Element element) {
        elements.add(element);
    }

    public Set<Element> getAllElements() {
        return new HashSet<>(elements);
    }

    public Element getElementByName(String name) {
        for (Element element : elements) {
            if(element.getName().equals(name)) {
                return element;
            }
        }
        return null;
    }
}