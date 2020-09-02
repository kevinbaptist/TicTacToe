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
		assertTrue("Failure to load empty.jpg", Gdx.files.internal("empty.jpg").exists());
		assertTrue("Failure to load cross.jpg", Gdx.files.internal("cross.jpg").exists());
		assertTrue("Failure to load circle.jpg", Gdx.files.internal("circle.png").exists());
	}
}