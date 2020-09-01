package pt.ladon.games.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import pt.ladon.games.components.ActionComponent;
import pt.ladon.games.exceptions.InvalidPositionException;
import pt.ladon.games.factories.EntityFactory;
import pt.ladon.games.models.Board;
import pt.ladon.games.utils.Participant;
import pt.ladon.games.utils.PieceState;

import java.util.HashMap;
import java.util.Map;

/**
 * Listen to all ActionComponent... which could be from a player or IA
 * First player start with cross
 */
public class BoardSystem extends IteratingSystem {
	private final Board board;
	private PieceState nextPieceToPlay;
	private final Map<PieceState, Participant> playerMappingWithPiece;

	private final Map<PieceState, PieceState> nextMoveMapping;

	private final ComponentMapper<ActionComponent> playMapper = ComponentMapper.getFor(ActionComponent.class);


	public BoardSystem(short rows, short columns, Participant participant) {
		super(Family.all(ActionComponent.class).get());
		this.playerMappingWithPiece = new HashMap<>();
		this.board = new Board(rows, columns);
		this.nextPieceToPlay = PieceState.CROSS;
		this.nextMoveMapping = new HashMap<>();

		this.initMapping(participant);
	}

	private void initMapping(Participant participant) {
		nextMoveMapping.put(PieceState.EMPTY, PieceState.CROSS);
		nextMoveMapping.put(PieceState.CROSS, PieceState.CIRCLE);
		nextMoveMapping.put(PieceState.CIRCLE, PieceState.CROSS);
		playerMappingWithPiece.put(PieceState.CROSS, participant);
		playerMappingWithPiece.put(PieceState.CIRCLE, participant == Participant.PLAYER_1 ? Participant.PLAYER_2 : Participant.PLAYER_1);
	}

	

	@Override
	public void addedToEngine(Engine engine) {
		super.addedToEngine(engine);
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		ActionComponent actionComponent = playMapper.get(entity);
		try {
			this.play(actionComponent.row, actionComponent.column, actionComponent.player);
		} catch (InvalidPositionException ignored) {
			logIgnoredMessage(actionComponent);
		} finally {
			actionComponent.pieceState = board.getPiece(actionComponent.row, actionComponent.column);
			getEngine().removeEntity(entity);
			if (hasGameFinished()) {
				Gdx.app.log("Game as finished", "game has finished");
			} 
			
			
		}
	}

	private void logIgnoredMessage(ActionComponent actionComponent) {
		Gdx.app.log("BoardSystem", "Invalid play by " + actionComponent.player
				+ " at " + actionComponent.row + ", " + actionComponent.column);
	}

	public void play(int row, int column, Participant player) {
		if (!board.isValidPlay(row, column) || !isValidPlayer(player)) {
			throw new InvalidPositionException();
		}
		if (hasGameFinished()) {
			return;
		}
		board.movePiece(row, column, this.nextPieceToPlay);
		switchPlayer();
	}

	private boolean isValidPlayer(Participant player) {
		return getCurrentPlayer() == player;
	}

	public Participant getCurrentPlayer() {
		return playerMappingWithPiece.get(nextPieceToPlay);
	}

	private void switchPlayer() {
		nextPieceToPlay = nextMoveMapping.get(nextPieceToPlay);
		if (getCurrentPlayer() == Participant.PLAYER_2) {//now it's turn of IA
			getEngine().addEntity(EntityFactory.createIA(board, PieceState.CIRCLE));
		}
	}

	public int getRows() {
		return board.getRows();
	}

	public int getColumns() {
		return board.getColumns();
	}

	public PieceState getPiece(int row, int col) {
		return board.getPiece(row, col);
	}

	public boolean hasGameFinished() {
		return board.hasGameFinished();
	}

	public boolean hasPlayerWon(PieceState piece) {
		return board.hasWon(piece);
	}

	public String printState() {
		return this.board.toString();
	}
}
