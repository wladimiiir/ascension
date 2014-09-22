package sk.wladimiiir.ascension.entity;

import java.util.HashSet;
import java.util.Set;

/**
 * User: wladimiiir
 * Date: 9/21/14
 * Time: 11:31 AM
 */
public class ElementEffect extends AbstractDBEntity {
    private Element element;
    private Set<Element> strongAgainst = new HashSet<>();
    private Set<Element> weakAgainst = new HashSet<>();
    private Set<Element> nonEffectiveAgainst = new HashSet<>();

    public Element getElement() {
        return element;
    }

    public void setElement(Element element) {
        this.element = element;
    }

    public Set<Element> getStrongAgainst() {
        return strongAgainst;
    }

    public void setStrongAgainst(Set<Element> strongAgainst) {
        this.strongAgainst = strongAgainst;
    }

    public Set<Element> getWeakAgainst() {
        return weakAgainst;
    }

    public void setWeakAgainst(Set<Element> weakAgainst) {
        this.weakAgainst = weakAgainst;
    }

    public Set<Element> getNonEffectiveAgainst() {
        return nonEffectiveAgainst;
    }

    public void setNonEffectiveAgainst(Set<Element> nonEffectiveAgainst) {
        this.nonEffectiveAgainst = nonEffectiveAgainst;
    }
}
