package ca.etsmtl.log320.tp3.ai;

import ca.etsmtl.log320.tp3.ai.evaluator.BoardEvaluator;
import ca.etsmtl.log320.tp3.loa.Board;
import ca.etsmtl.log320.tp3.loa.Move;
import ca.etsmtl.log320.tp3.loa.Team;

public class IterativeAlphaBetaSearch extends AlphaBetaSearch {
	Board m_board;
	Team m_team;
	int m_initDepth;
	int m_curDepthLimit;	
	long m_maxTime;
	BoardEvaluator m_evaluator;
	
	public IterativeAlphaBetaSearch(Board board, Team team, BoardEvaluator evaluator, int initDepth, long maxTimeMs) {
		super(board, team, evaluator, initDepth);
		m_board = board;
		m_team = team;
		m_evaluator = evaluator;
		m_maxTime = maxTimeMs;
		m_initDepth = initDepth;
	}

	@Override
	public Move getBestMove() {
		Move bestMove = null;
		float max = Float.NEGATIVE_INFINITY;
		m_curDepthLimit = m_initDepth;
		
		long startTime = System.currentTimeMillis();
		
		boolean exit = false;
		do {
			setMaxDepth(m_curDepthLimit);
			
			Move depthMove = null;
			float depthMax = Float.NEGATIVE_INFINITY;		
            
            for(Move move : m_board.findMoves(m_team)) {
    			m_board.applyMove(move);
    			
            	if (bestMove != null && System.currentTimeMillis() > startTime + m_maxTime) {
        			m_board.unapplyLastMove();
        			m_curDepthLimit--;
            		exit = true;
                	break;
            	}
            	
            	float value = searchMin(0, Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY);
    			if(value >= depthMax) {
    				depthMax = value;
    				depthMove = move;
    			}
    			
    			m_board.unapplyLastMove();
            }
            
            if(!exit && depthMax > max) {
            	max = depthMax;
            	bestMove = depthMove;
            }
            
			m_curDepthLimit++;
		} while(!exit);
		
		return bestMove;
	}

	@Override
	public void printStats() {
		System.out.println("Visited " + m_visitedNodes + " nodes (" + m_visitedLeaf + " leafs) at depth " + m_curDepthLimit);
	}
}
