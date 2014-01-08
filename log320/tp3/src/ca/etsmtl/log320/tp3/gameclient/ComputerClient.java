package ca.etsmtl.log320.tp3.gameclient;

import java.io.IOException;
import java.net.UnknownHostException;

import ca.etsmtl.log320.tp3.ai.AdversarialSearch;
import ca.etsmtl.log320.tp3.ai.AlphaBetaSearch;
import ca.etsmtl.log320.tp3.ai.IterativeAlphaBetaSearch;
import ca.etsmtl.log320.tp3.ai.evaluator.BoardEvaluator;
import ca.etsmtl.log320.tp3.ai.evaluator.CenterEvaluator;
import ca.etsmtl.log320.tp3.ai.evaluator.ConcentrationEvaluator;
import ca.etsmtl.log320.tp3.ai.evaluator.OpposingEvaluator;
import ca.etsmtl.log320.tp3.ai.evaluator.WeightedEvaluators;
import ca.etsmtl.log320.tp3.loa.Move;

public class ComputerClient extends TCPClient {
	
	BoardEvaluator m_evaluator;
	
	public ComputerClient(String host, int port) throws UnknownHostException,
			IOException {
		super(host, port);
		
		WeightedEvaluators evaluator = new WeightedEvaluators();
		evaluator.add(new OpposingEvaluator(new ConcentrationEvaluator(), 1.f), 2.f);
		evaluator.add(new OpposingEvaluator(new CenterEvaluator(), 1.f), 1.5f);
		
		m_evaluator = evaluator;
	}

	@Override
	protected Move doPlay() {
		// Sticitte que ca se passe!
		long startTime = System.currentTimeMillis();
		AdversarialSearch m_alphaBeta = new IterativeAlphaBetaSearch(m_board, m_clientTeam, m_evaluator, 5, 4500);
		//AdversarialSearch m_alphaBeta = new AlphaBetaSearch(m_board, m_clientTeam, m_evaluator, 1);
		
		Move bestMove = m_alphaBeta.getBestMove();
		long endTime = System.currentTimeMillis();
		System.out.println("Total execution time: " + (endTime-startTime) + "ms");
		m_alphaBeta.printStats();
		return bestMove;
	}

}
