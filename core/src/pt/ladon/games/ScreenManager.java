package pt.ladon.games;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import pt.ladon.games.screens.GameScreen;

public class ScreenManager extends Game {

	@Override
	public void create () {
		setScreen(new GameScreen());
	}
	

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		super.render();
	}

	@Override
	public void dispose() {
		super.dispose();
		this.screen.dispose();
	}
}
