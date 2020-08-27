package pt.ladon.games.systems;

import pt.ladon.games.exceptions.InvalidPositionException;
import pt.ladon.games.utils.PieceState;

import java.util.HashMap;
import java.util.Map;

public class BoardSystem {
	private PieceState[][] board;
	private PieceState nextPieceToPlay;
	
	private Map<PieceState, PieceState> nextMoveMapping;

	public BoardSystem(int rows, int columns) {
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
		for (int row = 0; row < getRows(); row++) {
			for (int column = 0; column < getColumns(); column++) {
				setPiece(row, column, PieceState.EMPTY);
			}
		}
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
