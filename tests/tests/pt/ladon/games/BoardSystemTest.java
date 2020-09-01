package pt.ladon.games;

import org.junit.Before;
import org.junit.Test;
import pt.ladon.games.exceptions.InvalidPositionException;
import pt.ladon.games.systems.BoardSystem;
import pt.ladon.games.utils.Participant;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static pt.ladon.games.utils.PieceState.*;

public class BoardSystemTest {
	private BoardSystem boardSystem;

	private static final short ROWS_EX1 = 5;
	private static final short COLUMNS_EX1 = 6;

	@Before
	public void setUp() throws Exception {
		boardSystem = new BoardSystem(ROWS_EX1, COLUMNS_EX1, Participant.PLAYER_1);
	}

	@Test
	public void when_starts_board_is_initialized() {
		assertEquals(ROWS_EX1, boardSystem.getRows());
		assertEquals(COLUMNS_EX1, boardSystem.getColumns());
	}

	@Test
	public void when_game_starts_board_is_empty() {
		for (int row = 0; row < ROWS_EX1; row++) {
			for (int col = 0; col < COLUMNS_EX1; col++) {
				assertEquals("All piece must start EMPTY", EMPTY, boardSystem.getPiece(row, col));
			}
		}
	}

	@Test
	public void when_selecting_invalid_row_exception_isThrown() {
		assertThrows("Valid row must be selected", InvalidPositionException.class, () -> boardSystem.getPiece(ROWS_EX1 + 10, 0));		
		assertThrows("Valid row must be selected", InvalidPositionException.class, () -> boardSystem.getPiece(ROWS_EX1, 0));		
	}

	@Test
	public void when_selecting_invalid_column_exception_isThrown() {
		assertThrows("Valid column must be selected", InvalidPositionException.class, () -> boardSystem.getPiece(0, COLUMNS_EX1 + 10));
		assertThrows("Valid column must be selected", InvalidPositionException.class, () -> boardSystem.getPiece(0, COLUMNS_EX1));
	}

	@Test
	public void when_selecting_valid_position_correct_value_is_returned() {
		assertEquals(boardSystem.getPiece(0, 0), EMPTY);
		
		boardSystem.play(0, 0, Participant.PLAYER_1);
		assertEquals(boardSystem.getPiece(0, 0), CROSS);

		assertEquals(boardSystem.getPiece(0, 1), EMPTY);

		boardSystem.play(0, 1, Participant.PLAYER_2);
		assertEquals(boardSystem.getPiece(0, 1), CIRCLE);
	}

	@Test
	public void when_selecting_position_taken_exception_is_thrown() {
		boardSystem.play(0, 0, Participant.PLAYER_1);
		assertThrows(InvalidPositionException.class, () -> boardSystem.play(0, 0, Participant.PLAYER_2));
	}

	@Test
	public void player2_cannot_play_out_of_time() {
		assertThrows(InvalidPositionException.class, () -> boardSystem.play(0, 0, Participant.PLAYER_2));
	}
}
