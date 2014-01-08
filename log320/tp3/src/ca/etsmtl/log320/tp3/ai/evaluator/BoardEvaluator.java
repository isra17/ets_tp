package ca.etsmtl.log320.tp3.ai.evaluator;

import ca.etsmtl.log320.tp3.loa.Board;
import ca.etsmtl.log320.tp3.loa.Team;

public interface BoardEvaluator {
	public float evaluate(Board board, Team team);
}
