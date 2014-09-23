package sk.wladimiiir.ascension.entity;

import java.util.Set;

/**
 * User: wladimiiir
 * Date: 9/21/14
 * Time: 11:23 AM
 */
public class Combatant {
    private final Set<Element> knownElements;

    public Combatant(Set<Element> knownElements) {
        this.knownElements = knownElements;
    }

    public Set<Element> getKnownElements() {
        return knownElements;
    }
}
