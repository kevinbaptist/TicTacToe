package pt.ladon.games;

import com.badlogic.ashley.core.PooledEngine;
import pt.ladon.games.factories.EntityFactory;
import pt.ladon.games.systems.BoardSystem;
import pt.ladon.games.systems.RenderSystem;

public class GameWorld {
	private final PooledEngine engine;
	private final RenderSystem renderSystem;
	
	private static final short BOARD_ROWS = 3, COLUMNS_ROWS = 3;

	public GameWorld() {
		this.engine = new PooledEngine();
		this.renderSystem = new RenderSystem();
		addSystems();
		addEntities();
	}

	private void addSystems() {
		this.engine.addSystem(renderSystem);
		this.engine.addSystem(new BoardSystem(BOARD_ROWS, COLUMNS_ROWS));
	}

	public void render(float deltaTime) {
		this.engine.update(deltaTime);
	}

	private void addEntities() {
		this.engine.addEntity(EntityFactory.createMove());
	}

	

	public void dispose() {
		this.renderSystem.dispose();
	}
}
