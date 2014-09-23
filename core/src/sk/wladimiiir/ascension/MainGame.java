package sk.wladimiiir.ascension;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import sk.wladimiiir.ascension.data.ElementDAO;
import sk.wladimiiir.ascension.entity.Combatant;
import sk.wladimiiir.ascension.entity.Element;
import sk.wladimiiir.ascension.screen.CombatScreen;

import java.util.Collections;

public class MainGame extends Game {
    private ElementDAO elementDAO = new ElementDAO();


    @Override
    public void create() {
        initData();
//        setScreen(new MeditationScreen());
        setScreen(new CombatScreen(new Combatant(elementDAO.getAllElements()), new Combatant(Collections.singleton(elementDAO.getElementByName("Fire")))));
    }

    private void initData() {
        Element element;

        element = new Element();
        element.setName("Water");
        element.setColor(Color.BLUE);
        elementDAO.saveElement(element);

        element = new Element();
        element.setName("Fire");
        element.setColor(Color.RED);
        elementDAO.saveElement(element);

        element = new Element();
        element.setName("Air");
        element.setColor(new Color(0.5f, 1, 1, 1));
        elementDAO.saveElement(element);

        element = new Element();
        element.setName("Earth");
        element.setColor(new Color(0.5f, 0.2f, 0, 1));
        elementDAO.saveElement(element);
    }
}
