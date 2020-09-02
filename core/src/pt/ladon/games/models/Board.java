package pt.ladon.games.models;

import pt.ladon.games.exceptions.InvalidPositionException;
import pt.ladon.games.utils.PieceState;

import java.util.HashMap;
import java.util.Map;


public class Board {
	private final PieceState[][] board;
	Map<PieceState, int[]> rowsState;
	Map<PieceState, int[]> columnsState;
	Map<PieceState, Number> diagonalState;
	Map<PieceState, Number> antiDiagonalState;

	public Board(int rows, int columns) {
		this.board = new PieceState[rows][columns];
		rowsState = new HashMap<>();
		columnsState = new HashMap<>();
		diagonalState = new HashMap<>();
		antiDiagonalState = new HashMap<>();
		this.initBoard();
		this.initStates();
	}

	private void initStates() {
		rowsState.put(PieceState.CROSS, new int[]{0, 0, 0});
		rowsState.put(PieceState.CIRCLE, new int[]{0, 0, 0});
		columnsState.put(PieceState.CROSS, new int[]{0, 0, 0});
		columnsState.put(PieceState.CIRCLE, new int[]{0, 0, 0});
		diagonalState.put(PieceState.CROSS, new Number());
		diagonalState.put(PieceState.CIRCLE, new Number());
		antiDiagonalState.put(PieceState.CROSS, new Number());
		antiDiagonalState.put(PieceState.CIRCLE, new Number());
	}


	private void initBoard() {
		for (short row = 0; row < getRows(); row++) {
			for (short column = 0; column < getColumns(); column++) {
				this.board[row][column] = PieceState.EMPTY;
			}
		}
	}

	private boolean isPieceValid(int row, int column) {
		return row < getRows() && column < getColumns();
	}

	public int getRows() {
		return this.board.length;
	}

	public int getColumns() {
		return this.board[0].length;
	}

	public PieceState getPiece(int row, int column) {
		if (!isPieceValid(row, column)) {
			throw new InvalidPositionException();
		}
		return this.board[row][column];
	}

	public void movePiece(int row, int column, PieceState state) {
		this.board[row][column] = state;
		this.rowsState.get(state)[row]++;
		this.columnsState.get(state)[column]++;

		if (row == column) {
			diagonalState.get(state).value++;
		}
		if (row + column == 2) {
			antiDiagonalState.get(state).value++;
		}
	}

	public boolean isValidPlay(int row, int column) {
		return isEmptyCell(row, column);
	}

	public boolean hasEmptyCells() {
		for (int row = 0; row < getRows(); row++) {
			for (int col = 0; col < getColumns(); col++) {
				if (isEmptyCell(row, col)) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean hasWon(PieceState piece) {
		return hasCompletedRow(piece) || hasCompletedColumn(piece) || hasCompletedDiagonal(piece);
	}

	public boolean hasCompletedDiagonal(PieceState piece) {
		return diagonalState.get(piece).value == 3 || antiDiagonalState.get(piece).value == 3;
	}

	public boolean hasCompletedColumn(PieceState piece) {
		for (int i = 0; i < 3; i++) {
			if (columnsState.get(piece)[i] == 3) {
				return true;
			}
		}
		return false;	
	}

	public boolean hasCompletedRow(PieceState piece) {
		for (int i = 0; i < 3; i++) {
			if (rowsState.get(piece)[i] == 3) {
				return true;
			}
		}
		return false;
	}

	public boolean hasGameFinished() {
		return !hasEmptyCells() || hasWon(PieceState.CIRCLE) || hasWon(PieceState.CROSS);
	}

	public boolean isEmptyCell(int row, int col) {
		return getPiece(row, col) == PieceState.EMPTY;
	}
	
	

	@Override
	public String toString() {
		StringBuilder br = new StringBuilder();
		for (int row = 0; row < getRows(); row++) {
			for (int col = 0; col < getColumns(); col++) {
				br.append(getPiece(row, col)).append("   ");
			}
			br.append("\n");
		}
		return br.toString();
	}


	private static class Number {
		protected int value;
	}
}
