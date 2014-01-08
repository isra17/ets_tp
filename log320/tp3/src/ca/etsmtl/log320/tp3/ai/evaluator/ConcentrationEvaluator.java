package ca.etsmtl.log320.tp3.ai.evaluator;

import java.util.List;

import ca.etsmtl.log320.tp3.loa.Board;
import ca.etsmtl.log320.tp3.loa.Position;
import ca.etsmtl.log320.tp3.loa.Team;

public class ConcentrationEvaluator implements BoardEvaluator {

	private static final float[] MIN_SUM_LOOKUP_TABLE = new float[] { 0, 1, 1, 2, 3, 5, 6, 7, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30 };

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
		
		float sum = 0, minSum = MIN_SUM_LOOKUP_TABLE[pieces.size()];
		for(Position piece : pieces) {
			float diffRow = 0, diffCol = 0;
			diffRow = Math.abs(comx - piece.x);
			diffCol = Math.abs(comy - piece.y);
			sum += Math.max(diffRow, diffCol);
		}
		
		return -(sum - minSum)/pieces.size() + 3;
	}

}
