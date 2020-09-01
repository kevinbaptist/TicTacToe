package pt.ladon.games.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import pt.ladon.games.components.ActionComponent;
import pt.ladon.games.exceptions.InvalidPositionException;
import pt.ladon.games.utils.Participant;
import pt.ladon.games.utils.PieceState;

import java.util.HashMap;
import java.util.Map;

/**
 * Listen to all ActionComponent... which could be from a player or IA
 * First player start with cross
 */
public class BoardSystem extends IteratingSystem {
	private final PieceState[][] board;
	private PieceState nextPieceToPlay;
	private final Map<PieceState, Participant> playerMappingWithPiece;

	private final Map<PieceState, PieceState> nextMoveMapping;

	private final ComponentMapper<ActionComponent> playMapper = ComponentMapper.getFor(ActionComponent.class);


	public BoardSystem(short rows, short columns, Participant participant) {
		super(Family.all(ActionComponent.class).get());
		this.playerMappingWithPiece = new HashMap<>();
		this.board = new PieceState[rows][columns];
		this.nextPieceToPlay = PieceState.CROSS;
		this.nextMoveMapping = new HashMap<>();

		this.initMapping(participant);
		this.initBoard();
	}

	private void initMapping(Participant participant) {
		nextMoveMapping.put(PieceState.EMPTY, PieceState.CROSS);
		nextMoveMapping.put(PieceState.CROSS, PieceState.CIRCLE);
		nextMoveMapping.put(PieceState.CIRCLE, PieceState.CROSS);
		playerMappingWithPiece.put(PieceState.CROSS, participant);
		playerMappingWithPiece.put(PieceState.CIRCLE, participant == Participant.PLAYER_1 ? Participant.PLAYER_2 : Participant.PLAYER_1);
	}

	private void initBoard() {
		for (short row = 0; row < getRows(); row++) {
			for (short column = 0; column < getColumns(); column++) {
				setPiece(row, column, PieceState.EMPTY);
			}
		}
	}

	@Override
	public void addedToEngine(Engine engine) {
		super.addedToEngine(engine);
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		ActionComponent playComponent = playMapper.get(entity);
		try {
			this.play(playComponent.row, playComponent.column, playComponent.player);
		} catch (InvalidPositionException ignored) {
			Gdx.app.log("BoardSystem", "Invalid play by " + playComponent.player 
					+ " at " + playComponent.row + ", " + playComponent.column);
		} finally {
			playComponent.pieceState = getPiece(playComponent.row, playComponent.column);
			getEngine().removeEntity(entity);
		}
	}

	public void play(int row, int column, Participant player) {
		if (!isValidPlay(row, column) || !isValidPlayer(player)) {
			throw new InvalidPositionException();
		}
		Gdx.app.log("BoardSystem", "I played " + row + ", " + column + " - " + player);
		setPiece(row, column, this.nextPieceToPlay);
		switchPlayer();
	}

	private boolean isValidPlayer(Participant player) {
		return playerMappingWithPiece.get(nextPieceToPlay) == player;
	}

	private void switchPlayer() {
		nextPieceToPlay = nextMoveMapping.get(nextPieceToPlay);
	}

	public PieceState getPiece(int row, int column) {
		if (!isPieceValid(row, column)) {
			throw new InvalidPositionException();
		}
		return this.board[row][column];
	}

	private void setPiece(int row, int column, PieceState state) {
		this.board[row][column] = state;
	}

	private boolean isPieceValid(int row, int column) {
		return row < getRows() && column < getColumns();
	}

	private boolean isValidPlay(int row, int column) {
		return getPiece(row, column) == PieceState.EMPTY;
	}

	public int getRows() {
		return this.board.length;
	}

	public int getColumns() {
		return this.board[0].length;
	}
}
