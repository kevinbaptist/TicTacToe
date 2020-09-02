package pt.ladon.games.components;

import com.badlogic.ashley.core.Component;
import pt.ladon.games.utils.PieceState;

/**
 * Component hold all info related to a play.
 */
public class ActionComponent implements Component {
	public int row;
	public int column;
	public PieceState pieceState;
}