package pt.ladon.games.factories;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import pt.ladon.games.components.MoveComponent;
import pt.ladon.games.components.RenderComponent;

/**
 * Class responsible to create every entity in the game
 */
public class EntityFactory {
	private static final Texture CROSS = new Texture("badlogic.jpg");

	public static Entity createMove() {
		Entity entity = new Entity();
		entity.add(new RenderComponent(CROSS));
		entity.add(new MoveComponent());
		return entity;
	}
}
