package pt.ladon.games.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import pt.ladon.games.components.RenderComponent;

public class RenderSystem extends EntitySystem implements Disposable {
	private final SpriteBatch batch;
	private final Camera camera;
	private final Viewport gamePort;

	private final ComponentMapper<RenderComponent> renderMapper = ComponentMapper.getFor(RenderComponent.class);
	private ImmutableArray<Entity> entities;

	private static final int VIRTUAL_WIDTH = 400;
	private static final int VIRTUAL_HEIGHT = 400;
	private static final float PPM = 100;

	public RenderSystem() {
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		gamePort = new FitViewport(VIRTUAL_WIDTH / PPM, VIRTUAL_HEIGHT / PPM, camera);

		setCameraPosition();
	}

	private void setCameraPosition() {
		camera.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
	}

	@Override
	public void addedToEngine(Engine engine) {
		entities = engine.getEntitiesFor(Family.all(RenderComponent.class).get());
	}

	@Override
	public void update(float deltaTime) {
		camera.update();
		render();
	}

	private void render() {
		RenderComponent renderComponent;
		batch.begin();
		for (int i = 0; i < entities.size(); i++) {
			renderComponent = renderMapper.get(entities.get(i));
			renderComponent.draw(batch);
		}
		batch.end();
	}
	

	public void dispose() {
		batch.dispose();
	}
	
}
