package pt.ladon.games.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import pt.ladon.games.components.RenderComponent;

/**
 * Listening for all render components to draw them in the batch
 */
public class RenderSystem extends EntitySystem implements Disposable {
	private final SpriteBatch batch;
	private final OrthographicCamera camera;

	private final ComponentMapper<RenderComponent> renderMapper = ComponentMapper.getFor(RenderComponent.class);
	private ImmutableArray<Entity> entities;
	private final Viewport viewport;

	public RenderSystem(OrthographicCamera camera) {
		this.batch = new SpriteBatch();
		this.camera = camera;
		this.viewport = new ExtendViewport(120, 120, camera);
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
			batch.draw(renderComponent.getTexture(), 
					renderComponent.getX(),
					renderComponent.getY(),
					renderComponent.getWidth(),
					renderComponent.getHeight());
		}
		batch.end();
	}

	public void resize(int width, int height) {
		viewport.update(width, height, true);
		batch.setProjectionMatrix(camera.combined);
	}

	public void dispose() {
		batch.dispose();
	}

}
