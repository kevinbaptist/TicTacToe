package pt.ladon.games.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Every instance drawn on the screen will have a renderComponent attached
 */
public class RenderComponent extends Sprite implements Component  {
	public RenderComponent(Texture texture) {
		super(new Sprite(texture));
	}
}
