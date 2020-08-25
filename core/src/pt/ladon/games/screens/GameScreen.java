package pt.ladon.games.screens;

import com.badlogic.gdx.ScreenAdapter;
import pt.ladon.games.GameWorld;

public class GameScreen extends ScreenAdapter {
	private final GameWorld gameWorld;

	public GameScreen() {
		this.gameWorld = new GameWorld();
	}

	@Override
	public void render(float delta) {
		this.gameWorld.render(delta);
	}


	@Override
	public void dispose() {
		this.gameWorld.dispose();
	}
}
