package pt.ladon.games;

import com.badlogic.ashley.core.PooledEngine;
import pt.ladon.games.factories.EntityFactory;
import pt.ladon.games.systems.RenderSystem;

public class GameWorld {
	private PooledEngine engine;

	private RenderSystem renderSystem;

	public GameWorld() {
		this.engine = new PooledEngine();
		this.renderSystem = new RenderSystem();
		addSystems();
		addEntities();
	}

	private void addSystems() {
		this.engine.addSystem(renderSystem);
	}

	public void render(float deltaTime) {
		this.engine.update(deltaTime);
	}

	private void addEntities() {
		this.engine.addEntity(EntityFactory.createDummyObject());
	}

	

	public void dispose() {
		this.renderSystem.dispose();
	}
}
