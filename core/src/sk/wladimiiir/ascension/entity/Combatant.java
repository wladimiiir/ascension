package sk.wladimiiir.ascension.entity;

/**
 * User: wladimiiir
 * Date: 9/21/14
 * Time: 11:23 AM
 */
public class Combatant {
    private final Element[] knownElements;

    public Combatant(Element[] knownElements) {
        this.knownElements = knownElements;
    }

    public Element[] getKnownElements() {
        return knownElements;
    }
}
