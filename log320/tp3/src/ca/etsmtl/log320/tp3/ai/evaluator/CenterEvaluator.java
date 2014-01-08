package ca.etsmtl.log320.tp3.ai.evaluator;

import java.util.List;

import ca.etsmtl.log320.tp3.loa.Board;
import ca.etsmtl.log320.tp3.loa.Position;
import ca.etsmtl.log320.tp3.loa.Team;

public class CenterEvaluator implements BoardEvaluator {

	@Override
	public float evaluate(Board board, Team team) {
		List<Position> pieces = board.getPieces(team);		
		
		float comx = 0, comy = 0;
		for(Position piece : pieces) {
			comx += piece.x;
			comy += piece.y;
		}
		comx /= pieces.size();
		comy /= pieces.size();
		
		float score = (Math.abs(comx - 3.5f) + Math.abs(comy - 3.5f)) / 2;		
		return 3.5f - score;
	}

}
