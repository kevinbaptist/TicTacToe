package pt.ladon.games.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import pt.ladon.games.components.MoveComponent;
import pt.ladon.games.exceptions.InvalidPositionException;
import pt.ladon.games.utils.PieceState;

import java.util.HashMap;
import java.util.Map;

public class BoardSystem extends IteratingSystem {
	private final PieceState[][] board;
	private PieceState nextPieceToPlay;
	
	private final Map<PieceState, PieceState> nextMoveMapping;

	private final ComponentMapper<MoveComponent> playMapper = ComponentMapper.getFor(MoveComponent.class);


	public BoardSystem(short rows, short columns) {
		super(Family.all(MoveComponent.class).get());
		this.board = new PieceState[rows][columns];
		this.nextPieceToPlay = PieceState.CROSS;
		this.nextMoveMapping = new HashMap<>();
		
		this.initMapping();
		this.initBoard();
	}

	private void initMapping() {
		nextMoveMapping.put(PieceState.EMPTY, PieceState.CROSS);
		nextMoveMapping.put(PieceState.CROSS, PieceState.CIRCLE);
		nextMoveMapping.put(PieceState.CIRCLE, PieceState.CROSS);
	}

	private void initBoard() {
		for (short row = 0; row < getRows(); row++) {
			for (short column = 0; column < getColumns(); column++) {
				setPiece(row, column, PieceState.EMPTY);
			}
		}
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		MoveComponent playComponent = playMapper.get(entity);
		this.play(playComponent.x, playComponent.y);
	}

	public void play(int row, int column) {
		if (!isValidPlay(row, column)) {
			throw new InvalidPositionException();
		}
		setPiece(row, column, this.nextPieceToPlay);
		switchPlayer();
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
