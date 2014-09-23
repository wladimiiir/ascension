package sk.wladimiiir.ascension.screen;

import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import sk.wladimiiir.ascension.entity.Combatant;
import sk.wladimiiir.ascension.entity.Element;
import sk.wladimiiir.ascension.graphics.ElementGraphic;
import sk.wladimiiir.ascension.graphics.LightEntity;

import java.util.*;

/**
 * User: wladimiiir
 * Date: 9/21/14
 * Time: 11:21 AM
 */
public class CombatScreen extends ScreenAdapter {
    private static final float PIXELS_TO_METER = 16f;

    private final Combatant leftCombatant;
    private final Combatant rightCombatant;

    private World world;
    private SpriteBatch batch;
    private Sprite backgroundImage;
    private ShapeRenderer shapeRenderer;
    private RayHandler rayHandler;
    private OrthographicCamera camera;
    private Box2DDebugRenderer worldRenderer;

    private LightEntity leftEntity;
    private LightEntity rightEntity;
    private ElementGraphic[] leftElements;
    private ElementGraphic[] rightElements;

    private InputProcessor inputProcessor = new InputAdapter() {
        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            for (ElementGraphic leftElement : leftElements) {
                if (leftElement.containsPoint(screenX / PIXELS_TO_METER, (Gdx.graphics.getHeight() - screenY) / PIXELS_TO_METER)) {
                    fireElement(leftElement.getElement(), leftEntity.getPosition(), rightEntity.getPosition());
                    return true;
                }
            }
            for (ElementGraphic rightElement : rightElements) {
                if (rightElement.containsPoint(screenX / PIXELS_TO_METER, (Gdx.graphics.getHeight() - screenY) / PIXELS_TO_METER)) {
                    fireElement(rightElement.getElement(), rightEntity.getPosition(), leftEntity.getPosition());
                    return true;
                }
            }
            return false;
        }
    };

    public CombatScreen(Combatant leftCombatant, Combatant rightCombatant) {
        this.leftCombatant = leftCombatant;
        this.rightCombatant = rightCombatant;
    }

    private void fireElement(Element element, Vector2 fromPosition, Vector2 toPosition) {
        final boolean leftToRight = fromPosition.x < toPosition.x;

        final BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(fromPosition);

        final Body body = world.createBody(bodyDef);
        final LightEntity lightEntity = new LightEntity(rayHandler, element.getColor(), 0, 0, 20, 40);
        lightEntity.attachToBody(body, 0, 0);

        final int velocity = 1000;
        body.applyLinearImpulse(new Vector2((leftToRight ? 1 : -1) * velocity, 0), body.getWorldCenter(), false);
    }

    @Override
    public void resize(int width, int height) {
        final float screenWidth = Gdx.graphics.getWidth() / PIXELS_TO_METER;
        final float screenHeight = Gdx.graphics.getHeight() / PIXELS_TO_METER;
        camera = new OrthographicCamera(screenWidth, screenHeight);
        camera.translate(screenWidth / 2f, screenHeight / 2f);
        camera.update();

        if (rayHandler != null) {
            rayHandler.dispose();
        }
        rayHandler = new RayHandler(world);
        rayHandler.setCombinedMatrix(camera.combined);
        rayHandler.setAmbientLight(0.8f);

        final float minSize = screenHeight / 4;
        final float maxSize = screenHeight / 3;
        leftEntity = new LightEntity(rayHandler, Color.GREEN, screenWidth / 5, screenHeight / 4, minSize, maxSize);
        rightEntity = new LightEntity(rayHandler, Color.RED, screenWidth / 5 * 4, screenHeight / 4, minSize, maxSize);

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(camera.combined);

        worldRenderer = new Box2DDebugRenderer();
        worldRenderer.setDrawVelocities(true);

        leftElements = createElementGraphics(new ArrayList<>(leftCombatant.getKnownElements()), 10 / PIXELS_TO_METER, screenHeight - 50 / PIXELS_TO_METER, false);
        rightElements = createElementGraphics(new ArrayList<>(rightCombatant.getKnownElements()), screenWidth - 10 / PIXELS_TO_METER, screenHeight - 50 / PIXELS_TO_METER, true);
    }

    private ElementGraphic[] createElementGraphics(List<Element> elements, float startX, float y, boolean rightToLeft) {
        final ElementGraphic[] graphics = new ElementGraphic[elements.size()];
        final float elementSize = 30 / PIXELS_TO_METER;
        int index = 0;

        if (rightToLeft) {
            Collections.reverse(elements);
        }
        for (Element element : elements) {
            final float x;
            if (rightToLeft) {
                x = startX - (elementSize + 5 / PIXELS_TO_METER) * (index + 1);
            } else {
                x = startX + (elementSize + 5 / PIXELS_TO_METER) * index;
            }
            graphics[index++] = new ElementGraphic(element, x, y, elementSize);
        }

        return graphics;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(inputProcessor);

        world = new World(new Vector2(0, -10), true);
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

        world.step(1 / 90f, 6, 2);

        batch.begin();
        backgroundImage.draw(batch);
        batch.end();

        leftEntity.update();
        rightEntity.update();

        for (ElementGraphic leftElement : leftElements) {
            leftElement.draw(shapeRenderer);
        }
        for (ElementGraphic rightElement : rightElements) {
            rightElement.draw(shapeRenderer);
        }

        rayHandler.updateAndRender();
        worldRenderer.render(world, camera.combined);
    }
}
