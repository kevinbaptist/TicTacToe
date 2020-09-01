package pt.ladon.games.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import pt.ladon.games.components.RenderComponent;
import pt.ladon.games.factories.EntityFactory;
import pt.ladon.games.utils.Participant;

import static com.badlogic.gdx.math.MathUtils.floor;
import static pt.ladon.games.configurations.Configurations.PIECE_WIDTH_HEIGHT;


/**
 * Responsible to handle all input from user
 */
public class InputSystem extends EntitySystem {
	private final OrthographicCamera camera;
	private ImmutableArray<Entity> entities;
	private final ComponentMapper<RenderComponent> renderMapper = ComponentMapper.getFor(RenderComponent.class);


	public InputSystem(OrthographicCamera camera) {
		this.camera = camera;
	}

	@Override
	public void addedToEngine(Engine engine) {
		entities = engine.getEntitiesFor(Family.all(RenderComponent.class).get());
	}

	@Override
	public void update(float deltaTime) {
		if (Gdx.input.isTouched(0)) {
			RenderComponent renderComponent;
			Rectangle boundingRectangle;
			Vector3 locationInWorld = getLocationTouchedInWorld();
			
			for (int i = 0; i < entities.size(); i++) {
				renderComponent = renderMapper.get(entities.get(i));
				boundingRectangle = renderComponent.getBoundingRectangle();
				if (boundingRectangle.contains(locationInWorld.x, locationInWorld.y)) {
					addActionEntity(locationInWorld.x, locationInWorld.y);
					break;
				}
			}
		}
	}

	private Vector3 getLocationTouchedInWorld() {
		Vector3 touchPosition = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
		return camera.unproject(touchPosition);
	}

	private void addActionEntity(float x, float y) {
		Entity action = EntityFactory.createAction(
				floor(x / PIECE_WIDTH_HEIGHT), 
				floor(y / PIECE_WIDTH_HEIGHT),
				Participant.PLAYER_1);
		getEngine().addEntity(action);
	}
}
