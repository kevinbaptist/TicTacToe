package pt.ladon.games.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import pt.ladon.games.components.IAComponent;
import pt.ladon.games.factories.EntityFactory;
import pt.ladon.games.models.Board;
import pt.ladon.games.models.Minimax;
import pt.ladon.games.models.Position;
import pt.ladon.games.utils.PieceState;

/**
 * TODO: For now IA is always circle. Put it more dinamic
 */
public class IASystem extends IteratingSystem {
	private final ComponentMapper<IAComponent> iaMapper = ComponentMapper.getFor(IAComponent.class);
	private final Minimax minimax;
	public IASystem() {
		super(Family.all(IAComponent.class).get());
		minimax = new Minimax();
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		Board board = iaMapper.get(entity).board;
		Position bestMove = getBestMove(board);
		Entity action = EntityFactory.createIAAction(bestMove.getRow(), bestMove.getColumn());
		updateEngine(entity, action);
	}

	private void updateEngine(Entity entity, Entity action) {
		getEngine().addEntity(action);
		getEngine().removeEntity(entity);
	}

	private Position getBestMove(Board board) {
		minimax.calculate(board, PieceState.CIRCLE);
		Position position = minimax.returnBestMove();
		minimax.reset();
		return position;
	}
	
}
