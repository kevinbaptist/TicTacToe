package pt.ladon.games;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.OrthographicCamera;
import pt.ladon.games.factories.EntityFactory;
import pt.ladon.games.systems.BoardSystem;
import pt.ladon.games.systems.InputSystem;
import pt.ladon.games.systems.RenderSystem;
import pt.ladon.games.systems.WorldSystem;
import pt.ladon.games.utils.Participant;

public class GameWorld {
	private final PooledEngine engine;
	private final RenderSystem renderSystem;
	private final BoardSystem boardSystem;
	private final OrthographicCamera camera;
	
	private static final short BOARD_ROWS = 3, COLUMNS_ROWS = 3;

	public GameWorld() {
		this.engine = new PooledEngine();
		this.camera = new OrthographicCamera();
		this.renderSystem = new RenderSystem(camera);
		this.boardSystem = new BoardSystem(BOARD_ROWS, COLUMNS_ROWS, Participant.PLAYER_1);
		
		addSystems();
		addEntities();
	}

	private void addSystems() {
		this.engine.addSystem(renderSystem);
		this.engine.addSystem(boardSystem);
		this.engine.addSystem(new InputSystem(camera));
		this.engine.addSystem(new WorldSystem());
	}

	public void render(float deltaTime) {
		this.engine.update(deltaTime);
	}

	private void addEntities() {
		addPiecesEntities();
	}

	private void addPiecesEntities() {
		for (int row = 0; row < boardSystem.getRows(); row++) {
			for (int column = 0; column < boardSystem.getColumns(); column++) {
				engine.addEntity(EntityFactory.createPiece(row, column));
			}
		}
	}


	public void dispose() {
		this.renderSystem.dispose();
		EntityFactory.dispose();
	}

	public void resize(int width, int height) {
		renderSystem.resize(width, height);
	}
}
