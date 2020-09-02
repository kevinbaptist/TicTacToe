package pt.ladon.games.components;

import com.badlogic.ashley.core.Component;
import pt.ladon.games.models.Board;
import pt.ladon.games.utils.PieceState;

public class IAComponent implements Component {
	public Board board;
	public PieceState myPiece;
}
