package sk.wladimiiir.ascension;

import com.badlogic.gdx.Game;
import sk.wladimiiir.ascension.screen.CombatScreen;

public class MainGame extends Game {
	@Override
	public void create () {
//        setScreen(new MeditationScreen());
        setScreen(new CombatScreen(null, null));
	}
}
