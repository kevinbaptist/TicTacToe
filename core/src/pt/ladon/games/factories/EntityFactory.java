package pt.ladon.games.factories;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import pt.ladon.games.components.ActionComponent;
import pt.ladon.games.components.RenderComponent;
import pt.ladon.games.utils.Participant;

import static pt.ladon.games.configurations.Configurations.PIECE_WIDTH_HEIGHT;

/**
 * Class responsible to create every entity in the game
 */
public class EntityFactory {
	public static final Texture CROSS = new Texture("cross.jpg");
	public static final Texture CIRCLE = new Texture("circle.png");
	public static final Texture EMPTY_CELL = new Texture("badlogic.jpg");

	public static Entity createAction(int row, int column, Participant participant) {
		Entity entity = new Entity();
		ActionComponent component = new ActionComponent();
		component.row = row;
		component.column = column;
		component.player = participant;
		entity.add(component);
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
