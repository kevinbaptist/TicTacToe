package pt.ladon.games.models;

import pt.ladon.games.systems.BoardSystem;
import pt.ladon.games.utils.Pair;
import pt.ladon.games.utils.Participant;

public class Minimax {
	
	public static Pair<Position, Integer> getBestMove(BoardSystem boardSystem, Participant player) {
		if (boardSystem.hasGameFinished()) {
			return new Pair<>(null, 0);
		}
		
		int bestScore = 3000;
		
		if (boardSystem.getCurrentPlayer() == player) {
			bestScore = -bestScore;
		}
		
		
		
		return new Pair<>(new Position(0, 0), 2000);
	}
	
	
}
