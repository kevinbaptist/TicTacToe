package pt.ladon.games;

import com.badlogic.gdx.Gdx;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;

@RunWith(GdxTestRunner.class)
public class RenderSystemClass {
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void images_exists() {
		assertTrue("Failure to load badlogic.png", Gdx.files.internal("badlogic.jpg").exists());
		assertTrue("Failure to load cross.png", Gdx.files.internal("cross.png").exists());
		assertTrue("Failure to load circle.png", Gdx.files.internal("circle.png").exists());
	}
}