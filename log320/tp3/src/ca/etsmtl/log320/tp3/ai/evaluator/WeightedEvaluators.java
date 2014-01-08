package ca.etsmtl.log320.tp3.ai.evaluator;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import ca.etsmtl.log320.tp3.loa.Board;
import ca.etsmtl.log320.tp3.loa.Team;

public class WeightedEvaluators implements BoardEvaluator {

	Map<BoardEvaluator, Float> m_evaluators = new HashMap<BoardEvaluator, Float>();
	
	public void add(BoardEvaluator evaluator, float weight) {
		m_evaluators.put(evaluator, weight);
	}
	
	@Override
	public float evaluate(Board board, Team team) {
		float score = 0.f;
		for(Entry<BoardEvaluator, Float> entry : m_evaluators.entrySet()) {
			score += entry.getKey().evaluate(board, team) * entry.getValue();
		}
		
		return score;
	}

}
