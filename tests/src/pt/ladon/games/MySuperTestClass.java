package pt.ladon.games;

import com.badlogic.gdx.Gdx;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;

@RunWith(GdxTestRunner.class)
public class MySuperTestClass {

	@Test
	public void bestTestInHistory() {
		assertTrue(".", Gdx.files
				.internal("../android/assets/badlogic.jpg").exists());
	}
}