package sk.wladimiiir.ascension.screen;

import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import sk.wladimiiir.ascension.entity.Combatant;
import sk.wladimiiir.ascension.graphics.LightEntity;

/**
 * User: wladimiiir
 * Date: 9/21/14
 * Time: 11:21 AM
 */
public class CombatScreen extends ScreenAdapter {
    private final Combatant leftOne;
    private final Combatant rightOne;
    private SpriteBatch batch;
    private Sprite backgroundImage;
    private World world;
    private LightEntity leftEntity;
    private LightEntity rightEntity;
    private RayHandler rayHandler;
    private OrthographicCamera camera;

    public CombatScreen(Combatant leftOne, Combatant rightOne) {
        this.leftOne = leftOne;
        this.rightOne = rightOne;
    }

    @Override
    public void resize(int width, int height) {
        final int screenWidth = Gdx.graphics.getWidth();
        final int screenHeight = Gdx.graphics.getHeight();
        camera = new OrthographicCamera(screenWidth, screenHeight);
        camera.translate(screenWidth / 2f, screenHeight / 2f);
        camera.update();

        if (rayHandler != null) {
            rayHandler.dispose();
        }
        rayHandler = new RayHandler(world);
        rayHandler.setCombinedMatrix(camera.combined);
        rayHandler.setAmbientLight(0.8f);

        final int minSize = screenHeight / 4;
        final int maxSize = screenHeight / 3;
        leftEntity = new LightEntity(rayHandler, Color.GREEN, screenWidth / 5, screenHeight / 4, minSize, maxSize);
        rightEntity = new LightEntity(rayHandler, Color.RED, screenWidth / 5 * 4, screenHeight / 4, minSize, maxSize);
    }

    @Override
    public void show() {
        world = new World(new Vector2(0, 10), true);
        batch = new SpriteBatch();
        backgroundImage = new Sprite(new Texture("magic_forest.jpg"));
        backgroundImage.setOrigin(0, 0);

        float scaleX = (float) Gdx.graphics.getWidth() / backgroundImage.getTexture().getWidth();
        float scaleY = (float) Gdx.graphics.getHeight() / backgroundImage.getTexture().getHeight();
        backgroundImage.setScale(scaleX, scaleY);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        backgroundImage.draw(batch);
        batch.end();

        leftEntity.update();
        rightEntity.update();

        rayHandler.updateAndRender();
    }
}
