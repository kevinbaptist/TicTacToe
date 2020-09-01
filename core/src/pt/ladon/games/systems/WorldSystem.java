package pt.ladon.games.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.Texture;
import pt.ladon.games.components.ActionComponent;
import pt.ladon.games.components.RenderComponent;
import pt.ladon.games.factories.EntityFactory;
import pt.ladon.games.utils.PieceState;

import java.util.HashMap;
import java.util.Map;

/**
 * Bridge between BoardSystem and RenderSystem. Listens to any change on the board and change texture accordingly
 */
public class WorldSystem extends EntitySystem implements EntityListener {
	private final ComponentMapper<RenderComponent> renderMapper = ComponentMapper.getFor(RenderComponent.class);
	private final ComponentMapper<ActionComponent> actionMapper = ComponentMapper.getFor(ActionComponent.class);

	private final Map<PieceState, Texture> textureMap;
	private ImmutableArray<Entity> renderableEntities;

	public WorldSystem() {
		this.textureMap = new HashMap<>();
		this.textureMap.put(PieceState.CROSS, EntityFactory.CROSS);
		this.textureMap.put(PieceState.CIRCLE, EntityFactory.CIRCLE);
		this.textureMap.put(PieceState.EMPTY, EntityFactory.EMPTY_CELL);
	}

	@Override
	public void addedToEngine(Engine engine) {
		renderableEntities = engine.getEntitiesFor(Family.all(RenderComponent.class).get());
		engine.addEntityListener(Family.all(ActionComponent.class).get(), this);
	}

	@Override
	public void entityAdded(Entity entity) {
	}

	private void changeTexture(ActionComponent actionComponent) {
		Entity entity = renderableEntities.get((actionComponent.row * 3) + (actionComponent.column));

		RenderComponent renderComponent = renderMapper.get(entity);
		renderComponent.setTexture(textureMap.get(actionComponent.pieceState));
	}


	@Override
	public void entityRemoved(Entity entity) {
		ActionComponent actionComponent = actionMapper.get(entity);
		changeTexture(actionComponent);
	}
}
