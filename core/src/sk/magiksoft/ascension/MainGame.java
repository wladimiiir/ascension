package sk.magiksoft.ascension;

import com.badlogic.gdx.Game;
import sk.magiksoft.ascension.screen.MeditationScreen;

public class MainGame extends Game {
    private MeditationScreen meditationScreen;

	@Override
	public void create () {
        meditationScreen = new MeditationScreen();
        setScreen(meditationScreen);
	}
}
