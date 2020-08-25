package pt.ladon.games.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import pt.ladon.games.components.RenderComponent;

public class RenderSystem extends EntitySystem implements Disposable {
	private final SpriteBatch batch;

	private final ComponentMapper<RenderComponent> renderMapper = ComponentMapper.getFor(RenderComponent.class);
	private ImmutableArray<Entity> entities;

	public RenderSystem() {
		this.batch = new SpriteBatch();

	}

	public void addedToEngine(Engine engine) {
		entities = engine.getEntitiesFor(Family.all(RenderComponent.class).get());
	}

	@Override
	public void update(float deltaTime) {
		RenderComponent renderComponent;
		batch.begin();
		for (int i = 0; i < entities.size(); i++) {
			renderComponent = renderMapper.get(entities.get(i));
			renderComponent.draw(batch);
		}
		batch.end();

	}
	
	
	public void dispose() {
		this.batch.dispose();
	}

	
}
