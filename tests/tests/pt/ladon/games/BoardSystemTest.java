package pt.ladon.games;

import com.badlogic.ashley.core.Engine;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import pt.ladon.games.exceptions.InvalidPositionException;
import pt.ladon.games.systems.BoardSystem;
import pt.ladon.games.utils.Participant;

import static org.junit.Assert.*;
import static pt.ladon.games.utils.PieceState.*;

@RunWith(GdxTestRunner.class)
public class BoardSystemTest {
	private BoardSystem boardSystem;
	private Engine engine;

	private static final short ROWS_EX1 = 3;
	private static final short COLUMNS_EX1 = 3;

	@Before
	public void setUp() throws Exception {
		engine = new Engine();
		boardSystem = new BoardSystem(ROWS_EX1, COLUMNS_EX1, Participant.PLAYER_1);
		engine.addSystem(boardSystem);
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

	@Test
	public void when_there_not_empty_cells_then_game_ends() {
		assertFalse(boardSystem.hasGameFinished());
		boardSystem.play(0, 0, Participant.PLAYER_1);
		boardSystem.play(0, 1, Participant.PLAYER_2);
		boardSystem.play(0, 2, Participant.PLAYER_1);
		boardSystem.play(1, 0, Participant.PLAYER_2);
		boardSystem.play(1, 2, Participant.PLAYER_1);
		boardSystem.play(1, 1, Participant.PLAYER_2);
		boardSystem.play(2, 0, Participant.PLAYER_1);
		boardSystem.play(2, 2, Participant.PLAYER_2);
		boardSystem.play(2, 1, Participant.PLAYER_1);
		assertTrue(boardSystem.hasGameFinished());
	}

	@Test
	public void three_pieces_in_a_row_then_game_ends_and_player_wins() {
		assertFalse(boardSystem.hasGameFinished());
		assertFalse(boardSystem.hasPlayerWon(CROSS));
		assertFalse(boardSystem.hasPlayerWon(CIRCLE));
		boardSystem.play(0, 0, Participant.PLAYER_1);
		boardSystem.play(2, 2, Participant.PLAYER_2);
		boardSystem.play(1, 0, Participant.PLAYER_1);
		boardSystem.play(2, 1, Participant.PLAYER_2);
		boardSystem.play(2, 0, Participant.PLAYER_1);
		assertTrue(boardSystem.hasPlayerWon(CROSS));
		assertTrue(boardSystem.hasGameFinished());
	}

	@Test
	public void three_pieces_in_a_column_then_game_ends_and_player_wins() {
		assertFalse(boardSystem.hasGameFinished());
		assertFalse(boardSystem.hasPlayerWon(CROSS));
		assertFalse(boardSystem.hasPlayerWon(CIRCLE));
		boardSystem.play(0, 0, Participant.PLAYER_1);
		boardSystem.play(2, 2, Participant.PLAYER_2);
		boardSystem.play(0, 1, Participant.PLAYER_1);
		boardSystem.play(2, 1, Participant.PLAYER_2);
		boardSystem.play(0, 2, Participant.PLAYER_1);
		assertTrue(boardSystem.hasPlayerWon(CROSS));
		assertTrue(boardSystem.hasGameFinished());
	}

	@Test
	public void three_pieces_in_a_diagonal_then_game_ends_and_player_wins() {
		assertFalse(boardSystem.hasGameFinished());
		assertFalse(boardSystem.hasPlayerWon(CROSS));
		assertFalse(boardSystem.hasPlayerWon(CIRCLE));
		boardSystem.play(0, 0, Participant.PLAYER_1);
		boardSystem.play(2, 0, Participant.PLAYER_2);
		boardSystem.play(1, 1, Participant.PLAYER_1);
		boardSystem.play(2, 1, Participant.PLAYER_2);
		boardSystem.play(2, 2, Participant.PLAYER_1);
		assertTrue(boardSystem.hasPlayerWon(CROSS));
		assertTrue(boardSystem.hasGameFinished());
	}

	@Test
	public void three_pieces_in_a_anti_diagonal_then_game_ends_and_player_wins() {
		assertFalse(boardSystem.hasGameFinished());
		assertFalse(boardSystem.hasPlayerWon(CROSS));
		assertFalse(boardSystem.hasPlayerWon(CIRCLE));
		boardSystem.play(2, 0, Participant.PLAYER_1);
		boardSystem.play(0, 0, Participant.PLAYER_2);
		boardSystem.play(1, 1, Participant.PLAYER_1);
		boardSystem.play(2, 1, Participant.PLAYER_2);
		boardSystem.play(0, 2, Participant.PLAYER_1);
		assertTrue(boardSystem.hasPlayerWon(CROSS));
		assertTrue(boardSystem.hasGameFinished());
	}
}
