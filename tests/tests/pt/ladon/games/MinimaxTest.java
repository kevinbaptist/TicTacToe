package pt.ladon.games;

import org.junit.Before;
import org.junit.Test;
import pt.ladon.games.models.Board;

public class MinimaxTest {
	private Board board;
	@Before
	public void setUp() throws Exception {
		board = new Board(3, 3);
	}

	@Test
	public void when_max_depth_is_0_anything_is_evaluated() {
//		Pair<Position, Integer> bestMove = Minimax.getBestMove(board, 0);
//		assertNull(bestMove.first);
//		assertEquals(bestMove.second, new Integer(0));
	}
	
	
}
