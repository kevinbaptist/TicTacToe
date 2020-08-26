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
	public void bad_logic_image_eexists() {
		assertTrue(".", Gdx.files.internal("badlogic.jpg").exists());
	}
}