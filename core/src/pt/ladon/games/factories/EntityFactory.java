package pt.ladon.games.factories;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import pt.ladon.games.components.RenderComponent;

/**
 * Class responsible to create every entity in the game
 */
public class EntityFactory {

	public EntityFactory() {
	}

	public static Entity createDummyObject() {
		Entity entity = new Entity();
		entity.add(new RenderComponent(new Texture("badlogic.jpg")));
		return entity;
	}
}
