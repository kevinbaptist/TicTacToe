package pt.ladon.games.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import pt.ladon.games.components.IAComponent;
import pt.ladon.games.factories.EntityFactory;
import pt.ladon.games.models.Board;
import pt.ladon.games.utils.PieceState;

/**
 * TODO: For now IA is always circle. Put it more dinamic
 */
public class IASystem extends IteratingSystem {
	private final ComponentMapper<IAComponent> iaMapper = ComponentMapper.getFor(IAComponent.class);

	public IASystem() {
		super(Family.all(IAComponent.class).get());
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		Board board = iaMapper.get(entity).board;
		playUsingPredefinedRules(board);
		getEngine().removeEntity(entity);
	}
	
	private void playUsingPredefinedRules(Board board) {
		for (int row = 0; row < board.getRows(); row++) {
			for (int column = 0; column < board.getColumns(); column++) {
				if (board.getPiece(row, column) == PieceState.EMPTY) {
					Entity action = EntityFactory.createAction(row, column, PieceState.CROSS);
					getEngine().addEntity(action);
				}
			}
		}
	}
}
