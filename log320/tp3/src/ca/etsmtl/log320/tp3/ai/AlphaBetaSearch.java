package ca.etsmtl.log320.tp3.ai;

import ca.etsmtl.log320.tp3.ai.evaluator.BoardEvaluator;
import ca.etsmtl.log320.tp3.loa.Board;
import ca.etsmtl.log320.tp3.loa.Move;
import ca.etsmtl.log320.tp3.loa.Team;

public class AlphaBetaSearch implements AdversarialSearch {
	Board m_board;
	Team m_team;
	int m_maxDepth;
	BoardEvaluator m_evaluator;

	protected int m_visitedNodes = 0;
	protected int m_visitedLeaf = 0;
	
	public AlphaBetaSearch(Board board, Team team, BoardEvaluator evaluator, int maxDepth) {
		m_board = board;
		m_team = team;
		m_maxDepth = maxDepth;
		m_evaluator = evaluator;
	}

	@Override
	public Move getBestMove() {
		m_visitedNodes = 0;
		Move bestMove = null;
		float max = Float.NEGATIVE_INFINITY;
		for(Move move : m_board.findMoves(m_team)) {
			m_board.applyMove(move);
			
			float value = searchMin(0, Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY);
			if(value >= max) {
				max = value;
				bestMove = move;
			}
			
			m_board.unapplyLastMove();
		}
		return bestMove;
	}
	
	protected float searchMax(int depth, float alpha, float beta) {
		m_visitedNodes++;
		
		if(m_board.isTerminal(m_team)) {
			return 1000000.f / depth;
		} else if(m_board.isTerminal(m_team.getOpposingTeam())) {
			return -1000000.f / depth;
		} else if(depth == m_maxDepth) {
			m_visitedLeaf++;
			return m_evaluator.evaluate(m_board, m_team);
		}
		
		float maxScore = Float.NEGATIVE_INFINITY;
		for(Move move : m_board.findMoves(m_team)) {
			m_board.applyMove(move);
			
			float score = searchMin(depth + 1, alpha, beta);
			maxScore = Math.max(maxScore, score);
			
			if(maxScore >= beta) {
				m_board.unapplyLastMove();
				return maxScore;
			}
			
            alpha = Math.max(alpha, maxScore);
			m_board.unapplyLastMove();
		}		
		return maxScore;
	}
	
	protected float searchMin(int depth, float alpha, float beta) {
		m_visitedNodes++;
		
		if(m_board.isTerminal(m_team)) {
			return 1000000.f / depth;
		} else if(m_board.isTerminal(m_team.getOpposingTeam())) {
			return -1000000.f / depth;
		} else if(depth == m_maxDepth) {
			m_visitedLeaf++;
			return m_evaluator.evaluate(m_board, m_team);
		}
		
		float minScore = Float.POSITIVE_INFINITY;
		for(Move move : m_board.findMoves(m_team.getOpposingTeam())) {
			m_board.applyMove(move);
			
			float score = searchMax(depth + 1, alpha, beta);
			minScore = Math.min(minScore, score);
			
			if(minScore <= alpha) {
				m_board.unapplyLastMove();
				return minScore;
			}
			
            alpha = Math.min(beta, minScore);
			m_board.unapplyLastMove();
		}		
		return minScore;
	}
	
	public void setMaxDepth(int maxDepth) {
		m_maxDepth = maxDepth;
	}
	
	public int getVisistedNodes() {
		return m_visitedNodes;
	}

	@Override
	public void printStats() {
		System.out.println("Visited " + m_visitedNodes + " nodes (" + m_visitedLeaf + " leafs)");
	}
}
