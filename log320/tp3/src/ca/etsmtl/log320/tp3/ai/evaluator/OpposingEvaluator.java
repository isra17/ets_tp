package ca.etsmtl.log320.tp3.ai.evaluator;

import ca.etsmtl.log320.tp3.loa.Board;
import ca.etsmtl.log320.tp3.loa.Team;

public class OpposingEvaluator implements BoardEvaluator {

	BoardEvaluator m_evaluator;
	float m_teamWeight;
	
	public OpposingEvaluator(BoardEvaluator evaluator, float teamWeight) {
		m_evaluator = evaluator;
	}
	
	@Override
	public float evaluate(Board board, Team team) {
		return m_teamWeight * m_evaluator.evaluate(board, team) - m_evaluator.evaluate(board, team.getOpposingTeam());
	}
}
