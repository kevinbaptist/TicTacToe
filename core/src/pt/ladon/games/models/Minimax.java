package pt.ladon.games.models;

import pt.ladon.games.utils.PieceState;

import java.util.ArrayList;
import java.util.List;

public class Minimax {
	private final List<PositionAndScore> rootPositionScore;

	public Minimax() {
		rootPositionScore = new ArrayList<>();
	}

	public int calculate(Board board, PieceState state) {
		return calculate(board, state, 0);
	}

	private int calculate(Board board, PieceState state, int depth) {
		if (board.hasGameFinished()) {
			return getScore(board);
		}
		List<Integer> scores = new ArrayList<>();
		List<Position> possibleMoves = board.getPossibleMoves();
		for (Position position : possibleMoves) {
			if (state == PieceState.CROSS) {//It's player, so 
				board.movePiece(position.getRow(), position.getColumn(), PieceState.CROSS);
				int currentScore = calculate(board, PieceState.CIRCLE, depth + 1);
				scores.add(currentScore);
			} else if (state == PieceState.CIRCLE) {
				board.movePiece(position.getRow(), position.getColumn(), PieceState.CIRCLE);
				int currentScore = calculate(board, PieceState.CROSS, depth + 1);
				scores.add(currentScore);
				if (depth == 0) {
					rootPositionScore.add(new PositionAndScore(position, currentScore));
				}
			}
			board.resetPiece(position);
		}


		return state == PieceState.CROSS ? returnMin(scores) : returnMax(scores);
	}

	public Position returnBestMove() {
		int MAX = -100000;
		int best = -1;

		for (int i = 0; i < rootPositionScore.size(); ++i) {
			if (MAX < rootPositionScore.get(i).score) {
				MAX = rootPositionScore.get(i).score;
				best = i;
			}
		}
		return rootPositionScore.get(best).position;
	}

	private static int returnMin(List<Integer> list) {
		int min = Integer.MAX_VALUE;
		int index = -1;
		for (int i = 0; i < list.size(); ++i) {
			if (list.get(i) < min) {
				min = list.get(i);
				index = i;
			}
		}
		return list.get(index);
	}

	private static int returnMax(List<Integer> list) {
		int max = Integer.MIN_VALUE;
		int index = -1;
		for (int i = 0; i < list.size(); ++i) {
			if (list.get(i) > max) {
				max = list.get(i);
				index = i;
			}
		}
		return list.get(index);
	}

	private static int getScore(Board board) {
		if (board.hasWon(PieceState.CIRCLE)) {
			return 10;
		} else if (board.hasWon(PieceState.CROSS)) {
			return -10;
		} else {
			return 0;
		}
	}

	public void reset() {
		rootPositionScore.clear();
	}

	private static class PositionAndScore {
		protected final Position position;
		protected final int score;

		public PositionAndScore(Position position, int score) {
			this.position = position;
			this.score = score;
		}
	}


}
