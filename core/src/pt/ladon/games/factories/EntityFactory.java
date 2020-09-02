package pt.ladon.games.factories;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import pt.ladon.games.components.ActionComponent;
import pt.ladon.games.components.IAComponent;
import pt.ladon.games.components.RenderComponent;
import pt.ladon.games.models.Board;
import pt.ladon.games.utils.PieceState;

import static pt.ladon.games.configurations.Configurations.PIECE_WIDTH_HEIGHT;

/**
 * Class responsible to create every entity in the game
 */
public class EntityFactory {
	public static final Texture CROSS = new Texture("cross.png");
	public static final Texture CIRCLE = new Texture("circle.png");
	public static final Texture EMPTY_CELL = new Texture("badlogic.jpg");

	private static Entity createAction(int row, int column, PieceState state) {
		Entity entity = new Entity();
		ActionComponent component = new ActionComponent();
		component.row = row;
		component.column = column;
		component.pieceState = state;
		entity.add(component);
		return entity;
	}

	public static Entity createIAAction(int row, int column) {
		return createAction(row, column, PieceState.CIRCLE);
	}
	public static Entity createPlayerAction(int row, int column) {
		return createAction(row, column, PieceState.CROSS);
	}
	public static Entity createIA(Board board) {
		Entity entity = new Entity();
		IAComponent iaComponent = new IAComponent();
		iaComponent.board = board;
		iaComponent.myPiece = PieceState.CIRCLE;
		entity.add(iaComponent);
		return entity;
	}

	public static Entity createPiece(int row, int column) {
		Entity entity = new Entity();
		entity.add(createRenderComponent(row, column));
		return entity;
	}

	private static RenderComponent createRenderComponent(int row, int column) {
		RenderComponent renderComponent = new RenderComponent(EntityFactory.EMPTY_CELL);
		int x = row * PIECE_WIDTH_HEIGHT;
		int y = column * PIECE_WIDTH_HEIGHT;
		renderComponent.setPosition(x, y);
		renderComponent.setSize(PIECE_WIDTH_HEIGHT, PIECE_WIDTH_HEIGHT);
		return renderComponent;
	}
	

	public static void dispose() {
		CROSS.dispose();
		CIRCLE.dispose();
		EMPTY_CELL.dispose();
	}
}
