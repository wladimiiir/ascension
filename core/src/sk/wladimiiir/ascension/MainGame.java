package sk.wladimiiir.ascension;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import sk.wladimiiir.ascension.data.ElementDAO;
import sk.wladimiiir.ascension.entity.Element;
import sk.wladimiiir.ascension.entity.Position;
import sk.wladimiiir.ascension.entity.Universe;
import sk.wladimiiir.ascension.screen.UniverseScreen;

import java.util.Random;
import java.util.UUID;

public class MainGame extends Game {
    private ElementDAO elementDAO = new ElementDAO();


    @Override
    public void create() {
        initData();
//        setScreen(new MeditationScreen());
//        setScreen(new CombatScreen(new Combatant(elementDAO.getAllElements()), new Combatant(Collections.singleton(elementDAO.getElementByName("Fire")))));
        setScreen(new UniverseScreen(createUniverse()));
    }

    private Universe createUniverse() {
        final Universe universe = new Universe();
        final Random random = new Random();
        for (int x = 0; x < 200; x++) {
            for (int y = 0; y < 200; y++) {
                if (random.nextInt(4) == 0) {
                    final Element element = new Element(UUID.randomUUID().toString(), new Color(random.nextFloat(), random.nextFloat(), random.nextFloat(), 1));
                    universe.add(element, new Position(x, y, 0));
                }
            }
        }
        return universe;
    }

    private void initData() {
        Element element;

        element = new Element("Water", Color.BLUE);
        elementDAO.saveElement(element);

        element = new Element("Fire", Color.RED);
        elementDAO.saveElement(element);

        element = new Element("Air", new Color(0.5f, 1, 1, 1));
        elementDAO.saveElement(element);

        element = new Element("Earth", new Color(0.5f, 0.2f, 0, 1));
        elementDAO.saveElement(element);
    }
}
